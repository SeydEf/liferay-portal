/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.web.experience.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;

import java.util.Locale;
import java.util.Objects;

import javax.ws.rs.BadRequestException;

/**
 * @author Víctor Galán
 */
public class DDMValueUtil {

	public static Value toDDMValue(
		ContentField contentFieldValue, DDMFormField ddmFormField,
		DLAppService dlAppService, long groupId,
		JournalArticleService journalArticleService,
		LayoutLocalService layoutLocalService, Locale locale) {

		com.liferay.headless.web.experience.dto.v1_0.Value value =
			contentFieldValue.getValue();

		if (value == null) {
			throw new BadRequestException(
				"Value for field " + ddmFormField.getName() +
					" can not be null");
		}

		if (ddmFormField.isLocalizable()) {
			LocalizedValue localizedValue = new LocalizedValue(locale);

			if (Objects.equals(
					DDMFormFieldType.DOCUMENT_LIBRARY,
					ddmFormField.getType())) {

				localizedValue.addString(
					locale,
					_getMediaValue(dlAppService, value.getDocumentId(), ""));
			}
			else if (Objects.equals(
						DDMFormFieldType.IMAGE, ddmFormField.getType())) {

				localizedValue.addString(
					locale,
					_getMediaValue(
						dlAppService, value.getImageId(),
						value.getImageDescription()));
			}
			else if (Objects.equals(
						DDMFormFieldType.JOURNAL_ARTICLE,
						ddmFormField.getType())) {

				JournalArticle journalArticle = null;

				try {
					journalArticle = journalArticleService.getLatestArticle(
						value.getStructuredContentId());
				}
				catch (Exception e) {
					throw new BadRequestException(
						"No structured content exists with id " +
							value.getDocumentId(),
						e);
				}

				localizedValue.addString(
					locale,
					JSONUtil.put(
						"className", JournalArticle.class.getName()
					).put(
						"classPK", journalArticle.getResourcePrimKey()
					).put(
						"title", journalArticle.getTitle()
					).toString());
			}
			else if (Objects.equals(
						DDMFormFieldType.LINK_TO_PAGE,
						ddmFormField.getType())) {

				String link = value.getLink();

				Layout layout = _getLayout(link, layoutLocalService, groupId);

				localizedValue.addString(
					locale,
					JSONUtil.put(
						"groupId", layout.getGroupId()
					).put(
						"label", layout.getFriendlyURL()
					).put(
						"privateLayout", layout.isPrivateLayout()
					).put(
						"layoutId", layout.getLayoutId()
					).toString());
			}
			else {
				localizedValue.addString(locale, value.getData());
			}

			return localizedValue;
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			Geo geo = value.getGeo();

			if (Objects.isNull(geo) || Objects.isNull(geo.getLatitude()) ||
				Objects.isNull(geo.getLongitude())) {

				throw new BadRequestException("Incorrect geo object");
			}

			return new UnlocalizedValue(
				JSONUtil.put(
					"latitude", geo.getLatitude()
				).put(
					"longitude", geo.getLongitude()
				).toString());
		}

		return new UnlocalizedValue(value.getData());
	}

	private static Layout _getLayout(
		String link, LayoutLocalService layoutLocalService, long groupId) {

		Layout layout = layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, link);

		if (layout == null) {
			layout = layoutLocalService.fetchLayoutByFriendlyURL(
				groupId, true, link);
		}

		if (layout == null) {
			throw new BadRequestException(
				"No page found with friendly url " + link);
		}

		try {
			LayoutPermissionUtil.check(
				PermissionThreadLocal.getPermissionChecker(), layout,
				ActionKeys.VIEW);
		}
		catch (PortalException pe) {
			throw new BadRequestException(
				"No page found with friendly url " + link, pe);
		}

		return layout;
	}

	private static String _getMediaValue(
		DLAppService dlAppService, long fileEntryId, String description) {

		FileEntry fileEntry = null;

		try {
			fileEntry = dlAppService.getFileEntry(fileEntryId);
		}
		catch (Exception e) {
			throw new BadRequestException(
				"No document exists with ID " + fileEntryId, e);
		}

		return JSONUtil.put(
			"alt", description
		).put(
			"classPK", fileEntry.getFileEntryId()
		).put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"name", fileEntry.getFileName()
		).put(
			"resourcePrimKey", fileEntry.getPrimaryKey()
		).put(
			"title", fileEntry.getFileName()
		).put(
			"type", "document"
		).put(
			"uuid", fileEntry.getUuid()
		).toString();
	}

}