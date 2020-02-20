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

package com.liferay.change.tracking.web.internal.servlet.taglib;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = DynamicInclude.class)
public class ChangeTrackingIndicatorDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		if (ctPreferences == null) {
			return;
		}

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<div class=\"change-tracking-indicator\">");

			String componentId =
				_portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS) +
					"IndicatorComponent";
			String module =
				_npmResolver.resolveModuleName("change-tracking-web") +
					"/dynamic_include/ChangeTrackingIndicator";

			_reactRenderer.renderReact(
				new ComponentDescriptor(module, componentId),
				_getReactData(httpServletRequest, themeDisplay),
				httpServletRequest, printWriter);

			printWriter.write("</div>");
		}
		catch (TemplateException templateException) {
			throw new IOException(templateException);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.product.navigation.taglib#/page.jsp#pre");
	}

	private Map<String, Object> _getReactData(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		PortletURL checkoutURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.ACTION_PHASE);

		checkoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/change_lists/checkout_ct_collection");
		checkoutURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));

		PortletURL selectURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		selectURL.setParameter(
			"mvcPath", "/change_lists/select_change_list.jsp");

		try {
			selectURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			return ReflectionUtil.throwException(windowStateException);
		}

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"checkoutURL", checkoutURL.toString()
		).put(
			"namespace", _portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS)
		).put(
			"selectURL", selectURL.toString()
		).build();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(),
			ChangeTrackingIndicatorDynamicInclude.class);

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());

		if (ctPreferences != null) {
			ctCollectionId = ctPreferences.getCtCollectionId();
		}

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection != null) {
			data.put("iconClass", "change-tracking-indicator-icon-change-list");
			data.put("iconName", "radio-button");
			data.put("title", ctCollection.getName());
		}
		else {
			data.put("iconClass", "change-tracking-indicator-icon-production");
			data.put("iconName", "simple-circle");

			data.put("title", _language.get(resourceBundle, "production"));
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ctPreferences != null) {
			long previousCtCollectionId =
				ctPreferences.getPreviousCtCollectionId();

			if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				CTCollection previousCtCollection =
					_ctCollectionLocalService.fetchCTCollection(
						previousCtCollectionId);

				if (previousCtCollection != null) {
					checkoutURL.setParameter(
						"ctCollectionId",
						String.valueOf(previousCtCollectionId));

					jsonArray.put(
						JSONUtil.put(
							"href", checkoutURL.toString()
						).put(
							"label",
							_language.format(
								resourceBundle, "work-on-x",
								previousCtCollection.getName(), false)
						).put(
							"symbolLeft", "radio-button"
						));
				}
			}
			else {
				checkoutURL.setParameter("ctCollectionId", String.valueOf(0L));

				jsonArray.put(
					JSONUtil.put(
						"href", checkoutURL.toString()
					).put(
						"label",
						_language.get(resourceBundle, "work-on-production")
					).put(
						"symbolLeft", "simple-circle"
					));
			}
		}

		jsonArray.put(
			JSONUtil.put(
				"href",
				StringBundler.concat(
					"javascript:Liferay.fire('",
					_portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS),
					"openDialog', {});")
			).put(
				"label", _language.get(resourceBundle, "select-a-publication")
			).put(
				"symbolLeft", "cards2"
			));

		PortletURL addURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		PortletURL overviewURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		addURL.setParameter("backURL", overviewURL.toString());

		addURL.setParameter(
			"mvcRenderCommandName", "/change_lists/add_ct_collection");

		jsonArray.put(
			JSONUtil.put(
				"href", addURL.toString()
			).put(
				"label", _language.get(resourceBundle, "create-new-publication")
			).put(
				"symbolLeft", "plus"
			));

		if (ctCollection != null) {
			jsonArray.put(
				JSONUtil.put("type", "divider")
			).put(
				JSONUtil.put(
					"href", overviewURL.toString()
				).put(
					"label", _language.get(resourceBundle, "review-changes")
				).put(
					"symbolLeft", "list-ul"
				)
			);

			int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
				ctCollection.getCtCollectionId());

			if (count > 0) {
				jsonArray.put(JSONUtil.put("type", "divider"));

				PortletURL conflictsURL = _portal.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
					CTPortletKeys.CHANGE_LISTS, 0, 0,
					PortletRequest.RENDER_PHASE);

				conflictsURL.setParameter(
					"mvcRenderCommandName", "/change_lists/view_conflicts");

				conflictsURL.setParameter(
					"ctCollectionId",
					String.valueOf(ctCollection.getCtCollectionId()));

				jsonArray.put(
					JSONUtil.put(
						"href", conflictsURL.toString()
					).put(
						"label",
						_language.get(resourceBundle, "prepare-to-publish")
					).put(
						"symbolLeft", "upload"
					));
			}
		}

		data.put("items", jsonArray);

		return data;
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private ReactRenderer _reactRenderer;

}