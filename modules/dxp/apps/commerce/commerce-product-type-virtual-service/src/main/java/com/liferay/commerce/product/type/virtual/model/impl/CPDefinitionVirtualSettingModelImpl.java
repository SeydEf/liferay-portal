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

package com.liferay.commerce.product.type.virtual.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingModel;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSettingSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The base model implementation for the CPDefinitionVirtualSetting service. Represents a row in the &quot;CPDefinitionVirtualSetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CPDefinitionVirtualSettingModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPDefinitionVirtualSettingImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingImpl
 * @see CPDefinitionVirtualSetting
 * @see CPDefinitionVirtualSettingModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CPDefinitionVirtualSettingModelImpl extends BaseModelImpl<CPDefinitionVirtualSetting>
	implements CPDefinitionVirtualSettingModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp definition virtual setting model instance should use the {@link CPDefinitionVirtualSetting} interface instead.
	 */
	public static final String TABLE_NAME = "CPDefinitionVirtualSetting";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "CPDefinitionVirtualSettingId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "CPDefinitionId", Types.BIGINT },
			{ "fileEntryId", Types.BIGINT },
			{ "url", Types.VARCHAR },
			{ "activationStatus", Types.VARCHAR },
			{ "duration", Types.BIGINT },
			{ "maxUsages", Types.INTEGER },
			{ "sampleFileEntryId", Types.BIGINT },
			{ "sampleUrl", Types.VARCHAR },
			{ "termsOfUseRequired", Types.BOOLEAN },
			{ "termsOfUseContent", Types.VARCHAR },
			{ "termsOfUseJournalArticleId", Types.BIGINT },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("CPDefinitionVirtualSettingId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("activationStatus", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("duration", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("maxUsages", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("sampleFileEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("sampleUrl", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("termsOfUseRequired", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("termsOfUseContent", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("termsOfUseJournalArticleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table CPDefinitionVirtualSetting (uuid_ VARCHAR(75) null,CPDefinitionVirtualSettingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,fileEntryId LONG,url VARCHAR(75) null,activationStatus VARCHAR(75) null,duration LONG,maxUsages INTEGER,sampleFileEntryId LONG,sampleUrl VARCHAR(75) null,termsOfUseRequired BOOLEAN,termsOfUseContent STRING null,termsOfUseJournalArticleId LONG,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table CPDefinitionVirtualSetting";
	public static final String ORDER_BY_JPQL = " ORDER BY cpDefinitionVirtualSetting.CPDefinitionVirtualSettingId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CPDefinitionVirtualSetting.CPDefinitionVirtualSettingId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.type.virtual.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.type.virtual.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.type.virtual.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting"),
			true);
	public static final long CPDEFINITIONID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long UUID_COLUMN_BITMASK = 8L;
	public static final long CPDEFINITIONVIRTUALSETTINGID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CPDefinitionVirtualSetting toModel(
		CPDefinitionVirtualSettingSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CPDefinitionVirtualSetting model = new CPDefinitionVirtualSettingImpl();

		model.setUuid(soapModel.getUuid());
		model.setCPDefinitionVirtualSettingId(soapModel.getCPDefinitionVirtualSettingId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCPDefinitionId(soapModel.getCPDefinitionId());
		model.setFileEntryId(soapModel.getFileEntryId());
		model.setUrl(soapModel.getUrl());
		model.setActivationStatus(soapModel.getActivationStatus());
		model.setDuration(soapModel.getDuration());
		model.setMaxUsages(soapModel.getMaxUsages());
		model.setSampleFileEntryId(soapModel.getSampleFileEntryId());
		model.setSampleUrl(soapModel.getSampleUrl());
		model.setTermsOfUseRequired(soapModel.getTermsOfUseRequired());
		model.setTermsOfUseContent(soapModel.getTermsOfUseContent());
		model.setTermsOfUseJournalArticleId(soapModel.getTermsOfUseJournalArticleId());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CPDefinitionVirtualSetting> toModels(
		CPDefinitionVirtualSettingSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CPDefinitionVirtualSetting> models = new ArrayList<CPDefinitionVirtualSetting>(soapModels.length);

		for (CPDefinitionVirtualSettingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.product.type.virtual.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting"));

	public CPDefinitionVirtualSettingModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _CPDefinitionVirtualSettingId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCPDefinitionVirtualSettingId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _CPDefinitionVirtualSettingId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionVirtualSetting.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionVirtualSetting.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionVirtualSettingId",
			getCPDefinitionVirtualSettingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("activationStatus", getActivationStatus());
		attributes.put("duration", getDuration());
		attributes.put("maxUsages", getMaxUsages());
		attributes.put("sampleFileEntryId", getSampleFileEntryId());
		attributes.put("sampleUrl", getSampleUrl());
		attributes.put("termsOfUseRequired", getTermsOfUseRequired());
		attributes.put("termsOfUseContent", getTermsOfUseContent());
		attributes.put("termsOfUseJournalArticleId",
			getTermsOfUseJournalArticleId());
		attributes.put("lastPublishDate", getLastPublishDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionVirtualSettingId = (Long)attributes.get(
				"CPDefinitionVirtualSettingId");

		if (CPDefinitionVirtualSettingId != null) {
			setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String activationStatus = (String)attributes.get("activationStatus");

		if (activationStatus != null) {
			setActivationStatus(activationStatus);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Integer maxUsages = (Integer)attributes.get("maxUsages");

		if (maxUsages != null) {
			setMaxUsages(maxUsages);
		}

		Long sampleFileEntryId = (Long)attributes.get("sampleFileEntryId");

		if (sampleFileEntryId != null) {
			setSampleFileEntryId(sampleFileEntryId);
		}

		String sampleUrl = (String)attributes.get("sampleUrl");

		if (sampleUrl != null) {
			setSampleUrl(sampleUrl);
		}

		Boolean termsOfUseRequired = (Boolean)attributes.get(
				"termsOfUseRequired");

		if (termsOfUseRequired != null) {
			setTermsOfUseRequired(termsOfUseRequired);
		}

		String termsOfUseContent = (String)attributes.get("termsOfUseContent");

		if (termsOfUseContent != null) {
			setTermsOfUseContent(termsOfUseContent);
		}

		Long termsOfUseJournalArticleId = (Long)attributes.get(
				"termsOfUseJournalArticleId");

		if (termsOfUseJournalArticleId != null) {
			setTermsOfUseJournalArticleId(termsOfUseJournalArticleId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getCPDefinitionVirtualSettingId() {
		return _CPDefinitionVirtualSettingId;
	}

	@Override
	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {
		_CPDefinitionVirtualSettingId = CPDefinitionVirtualSettingId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_columnBitmask |= CPDEFINITIONID_COLUMN_BITMASK;

		if (!_setOriginalCPDefinitionId) {
			_setOriginalCPDefinitionId = true;

			_originalCPDefinitionId = _CPDefinitionId;
		}

		_CPDefinitionId = CPDefinitionId;
	}

	public long getOriginalCPDefinitionId() {
		return _originalCPDefinitionId;
	}

	@JSON
	@Override
	public long getFileEntryId() {
		return _fileEntryId;
	}

	@Override
	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	@JSON
	@Override
	public String getUrl() {
		if (_url == null) {
			return StringPool.BLANK;
		}
		else {
			return _url;
		}
	}

	@Override
	public void setUrl(String url) {
		_url = url;
	}

	@JSON
	@Override
	public String getActivationStatus() {
		if (_activationStatus == null) {
			return StringPool.BLANK;
		}
		else {
			return _activationStatus;
		}
	}

	@Override
	public void setActivationStatus(String activationStatus) {
		_activationStatus = activationStatus;
	}

	@JSON
	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public void setDuration(long duration) {
		_duration = duration;
	}

	@JSON
	@Override
	public int getMaxUsages() {
		return _maxUsages;
	}

	@Override
	public void setMaxUsages(int maxUsages) {
		_maxUsages = maxUsages;
	}

	@JSON
	@Override
	public long getSampleFileEntryId() {
		return _sampleFileEntryId;
	}

	@Override
	public void setSampleFileEntryId(long sampleFileEntryId) {
		_sampleFileEntryId = sampleFileEntryId;
	}

	@JSON
	@Override
	public String getSampleUrl() {
		if (_sampleUrl == null) {
			return StringPool.BLANK;
		}
		else {
			return _sampleUrl;
		}
	}

	@Override
	public void setSampleUrl(String sampleUrl) {
		_sampleUrl = sampleUrl;
	}

	@JSON
	@Override
	public boolean getTermsOfUseRequired() {
		return _termsOfUseRequired;
	}

	@JSON
	@Override
	public boolean isTermsOfUseRequired() {
		return _termsOfUseRequired;
	}

	@Override
	public void setTermsOfUseRequired(boolean termsOfUseRequired) {
		_termsOfUseRequired = termsOfUseRequired;
	}

	@JSON
	@Override
	public String getTermsOfUseContent() {
		if (_termsOfUseContent == null) {
			return StringPool.BLANK;
		}
		else {
			return _termsOfUseContent;
		}
	}

	@Override
	public String getTermsOfUseContent(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTermsOfUseContent(languageId);
	}

	@Override
	public String getTermsOfUseContent(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTermsOfUseContent(languageId, useDefault);
	}

	@Override
	public String getTermsOfUseContent(String languageId) {
		return LocalizationUtil.getLocalization(getTermsOfUseContent(),
			languageId);
	}

	@Override
	public String getTermsOfUseContent(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getTermsOfUseContent(),
			languageId, useDefault);
	}

	@Override
	public String getTermsOfUseContentCurrentLanguageId() {
		return _termsOfUseContentCurrentLanguageId;
	}

	@JSON
	@Override
	public String getTermsOfUseContentCurrentValue() {
		Locale locale = getLocale(_termsOfUseContentCurrentLanguageId);

		return getTermsOfUseContent(locale);
	}

	@Override
	public Map<Locale, String> getTermsOfUseContentMap() {
		return LocalizationUtil.getLocalizationMap(getTermsOfUseContent());
	}

	@Override
	public void setTermsOfUseContent(String termsOfUseContent) {
		_termsOfUseContent = termsOfUseContent;
	}

	@Override
	public void setTermsOfUseContent(String termsOfUseContent, Locale locale) {
		setTermsOfUseContent(termsOfUseContent, locale,
			LocaleUtil.getSiteDefault());
	}

	@Override
	public void setTermsOfUseContent(String termsOfUseContent, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(termsOfUseContent)) {
			setTermsOfUseContent(LocalizationUtil.updateLocalization(
					getTermsOfUseContent(), "TermsOfUseContent",
					termsOfUseContent, languageId, defaultLanguageId));
		}
		else {
			setTermsOfUseContent(LocalizationUtil.removeLocalization(
					getTermsOfUseContent(), "TermsOfUseContent", languageId));
		}
	}

	@Override
	public void setTermsOfUseContentCurrentLanguageId(String languageId) {
		_termsOfUseContentCurrentLanguageId = languageId;
	}

	@Override
	public void setTermsOfUseContentMap(
		Map<Locale, String> termsOfUseContentMap) {
		setTermsOfUseContentMap(termsOfUseContentMap,
			LocaleUtil.getSiteDefault());
	}

	@Override
	public void setTermsOfUseContentMap(
		Map<Locale, String> termsOfUseContentMap, Locale defaultLocale) {
		if (termsOfUseContentMap == null) {
			return;
		}

		setTermsOfUseContent(LocalizationUtil.updateLocalization(
				termsOfUseContentMap, getTermsOfUseContent(),
				"TermsOfUseContent", LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public long getTermsOfUseJournalArticleId() {
		return _termsOfUseJournalArticleId;
	}

	@Override
	public void setTermsOfUseJournalArticleId(long termsOfUseJournalArticleId) {
		_termsOfUseJournalArticleId = termsOfUseJournalArticleId;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				CPDefinitionVirtualSetting.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CPDefinitionVirtualSetting.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> termsOfUseContentMap = getTermsOfUseContentMap();

		for (Map.Entry<Locale, String> entry : termsOfUseContentMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getTermsOfUseContent();

		if (xml == null) {
			return StringPool.BLANK;
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(CPDefinitionVirtualSetting.class.getName(),
				getPrimaryKey(), defaultLocale, availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String termsOfUseContent = getTermsOfUseContent(defaultLocale);

		if (Validator.isNull(termsOfUseContent)) {
			setTermsOfUseContent(getTermsOfUseContent(modelDefaultLanguageId),
				defaultLocale);
		}
		else {
			setTermsOfUseContent(getTermsOfUseContent(defaultLocale),
				defaultLocale, defaultLocale);
		}
	}

	@Override
	public CPDefinitionVirtualSetting toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CPDefinitionVirtualSetting)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CPDefinitionVirtualSettingImpl cpDefinitionVirtualSettingImpl = new CPDefinitionVirtualSettingImpl();

		cpDefinitionVirtualSettingImpl.setUuid(getUuid());
		cpDefinitionVirtualSettingImpl.setCPDefinitionVirtualSettingId(getCPDefinitionVirtualSettingId());
		cpDefinitionVirtualSettingImpl.setGroupId(getGroupId());
		cpDefinitionVirtualSettingImpl.setCompanyId(getCompanyId());
		cpDefinitionVirtualSettingImpl.setUserId(getUserId());
		cpDefinitionVirtualSettingImpl.setUserName(getUserName());
		cpDefinitionVirtualSettingImpl.setCreateDate(getCreateDate());
		cpDefinitionVirtualSettingImpl.setModifiedDate(getModifiedDate());
		cpDefinitionVirtualSettingImpl.setCPDefinitionId(getCPDefinitionId());
		cpDefinitionVirtualSettingImpl.setFileEntryId(getFileEntryId());
		cpDefinitionVirtualSettingImpl.setUrl(getUrl());
		cpDefinitionVirtualSettingImpl.setActivationStatus(getActivationStatus());
		cpDefinitionVirtualSettingImpl.setDuration(getDuration());
		cpDefinitionVirtualSettingImpl.setMaxUsages(getMaxUsages());
		cpDefinitionVirtualSettingImpl.setSampleFileEntryId(getSampleFileEntryId());
		cpDefinitionVirtualSettingImpl.setSampleUrl(getSampleUrl());
		cpDefinitionVirtualSettingImpl.setTermsOfUseRequired(getTermsOfUseRequired());
		cpDefinitionVirtualSettingImpl.setTermsOfUseContent(getTermsOfUseContent());
		cpDefinitionVirtualSettingImpl.setTermsOfUseJournalArticleId(getTermsOfUseJournalArticleId());
		cpDefinitionVirtualSettingImpl.setLastPublishDate(getLastPublishDate());

		cpDefinitionVirtualSettingImpl.resetOriginalValues();

		return cpDefinitionVirtualSettingImpl;
	}

	@Override
	public int compareTo(CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		long primaryKey = cpDefinitionVirtualSetting.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionVirtualSetting)) {
			return false;
		}

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = (CPDefinitionVirtualSetting)obj;

		long primaryKey = cpDefinitionVirtualSetting.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CPDefinitionVirtualSettingModelImpl cpDefinitionVirtualSettingModelImpl = this;

		cpDefinitionVirtualSettingModelImpl._originalUuid = cpDefinitionVirtualSettingModelImpl._uuid;

		cpDefinitionVirtualSettingModelImpl._originalGroupId = cpDefinitionVirtualSettingModelImpl._groupId;

		cpDefinitionVirtualSettingModelImpl._setOriginalGroupId = false;

		cpDefinitionVirtualSettingModelImpl._originalCompanyId = cpDefinitionVirtualSettingModelImpl._companyId;

		cpDefinitionVirtualSettingModelImpl._setOriginalCompanyId = false;

		cpDefinitionVirtualSettingModelImpl._setModifiedDate = false;

		cpDefinitionVirtualSettingModelImpl._originalCPDefinitionId = cpDefinitionVirtualSettingModelImpl._CPDefinitionId;

		cpDefinitionVirtualSettingModelImpl._setOriginalCPDefinitionId = false;

		cpDefinitionVirtualSettingModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CPDefinitionVirtualSetting> toCacheModel() {
		CPDefinitionVirtualSettingCacheModel cpDefinitionVirtualSettingCacheModel =
			new CPDefinitionVirtualSettingCacheModel();

		cpDefinitionVirtualSettingCacheModel.uuid = getUuid();

		String uuid = cpDefinitionVirtualSettingCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.uuid = null;
		}

		cpDefinitionVirtualSettingCacheModel.CPDefinitionVirtualSettingId = getCPDefinitionVirtualSettingId();

		cpDefinitionVirtualSettingCacheModel.groupId = getGroupId();

		cpDefinitionVirtualSettingCacheModel.companyId = getCompanyId();

		cpDefinitionVirtualSettingCacheModel.userId = getUserId();

		cpDefinitionVirtualSettingCacheModel.userName = getUserName();

		String userName = cpDefinitionVirtualSettingCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			cpDefinitionVirtualSettingCacheModel.createDate = createDate.getTime();
		}
		else {
			cpDefinitionVirtualSettingCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			cpDefinitionVirtualSettingCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			cpDefinitionVirtualSettingCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		cpDefinitionVirtualSettingCacheModel.CPDefinitionId = getCPDefinitionId();

		cpDefinitionVirtualSettingCacheModel.fileEntryId = getFileEntryId();

		cpDefinitionVirtualSettingCacheModel.url = getUrl();

		String url = cpDefinitionVirtualSettingCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.url = null;
		}

		cpDefinitionVirtualSettingCacheModel.activationStatus = getActivationStatus();

		String activationStatus = cpDefinitionVirtualSettingCacheModel.activationStatus;

		if ((activationStatus != null) && (activationStatus.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.activationStatus = null;
		}

		cpDefinitionVirtualSettingCacheModel.duration = getDuration();

		cpDefinitionVirtualSettingCacheModel.maxUsages = getMaxUsages();

		cpDefinitionVirtualSettingCacheModel.sampleFileEntryId = getSampleFileEntryId();

		cpDefinitionVirtualSettingCacheModel.sampleUrl = getSampleUrl();

		String sampleUrl = cpDefinitionVirtualSettingCacheModel.sampleUrl;

		if ((sampleUrl != null) && (sampleUrl.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.sampleUrl = null;
		}

		cpDefinitionVirtualSettingCacheModel.termsOfUseRequired = getTermsOfUseRequired();

		cpDefinitionVirtualSettingCacheModel.termsOfUseContent = getTermsOfUseContent();

		String termsOfUseContent = cpDefinitionVirtualSettingCacheModel.termsOfUseContent;

		if ((termsOfUseContent != null) && (termsOfUseContent.length() == 0)) {
			cpDefinitionVirtualSettingCacheModel.termsOfUseContent = null;
		}

		cpDefinitionVirtualSettingCacheModel.termsOfUseJournalArticleId = getTermsOfUseJournalArticleId();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			cpDefinitionVirtualSettingCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			cpDefinitionVirtualSettingCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return cpDefinitionVirtualSettingCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", CPDefinitionVirtualSettingId=");
		sb.append(getCPDefinitionVirtualSettingId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", CPDefinitionId=");
		sb.append(getCPDefinitionId());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", activationStatus=");
		sb.append(getActivationStatus());
		sb.append(", duration=");
		sb.append(getDuration());
		sb.append(", maxUsages=");
		sb.append(getMaxUsages());
		sb.append(", sampleFileEntryId=");
		sb.append(getSampleFileEntryId());
		sb.append(", sampleUrl=");
		sb.append(getSampleUrl());
		sb.append(", termsOfUseRequired=");
		sb.append(getTermsOfUseRequired());
		sb.append(", termsOfUseContent=");
		sb.append(getTermsOfUseContent());
		sb.append(", termsOfUseJournalArticleId=");
		sb.append(getTermsOfUseJournalArticleId());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(64);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionVirtualSettingId</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionVirtualSettingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionId</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>activationStatus</column-name><column-value><![CDATA[");
		sb.append(getActivationStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>duration</column-name><column-value><![CDATA[");
		sb.append(getDuration());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxUsages</column-name><column-value><![CDATA[");
		sb.append(getMaxUsages());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sampleFileEntryId</column-name><column-value><![CDATA[");
		sb.append(getSampleFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sampleUrl</column-name><column-value><![CDATA[");
		sb.append(getSampleUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>termsOfUseRequired</column-name><column-value><![CDATA[");
		sb.append(getTermsOfUseRequired());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>termsOfUseContent</column-name><column-value><![CDATA[");
		sb.append(getTermsOfUseContent());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>termsOfUseJournalArticleId</column-name><column-value><![CDATA[");
		sb.append(getTermsOfUseJournalArticleId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CPDefinitionVirtualSetting.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CPDefinitionVirtualSetting.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _CPDefinitionVirtualSettingId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _CPDefinitionId;
	private long _originalCPDefinitionId;
	private boolean _setOriginalCPDefinitionId;
	private long _fileEntryId;
	private String _url;
	private String _activationStatus;
	private long _duration;
	private int _maxUsages;
	private long _sampleFileEntryId;
	private String _sampleUrl;
	private boolean _termsOfUseRequired;
	private String _termsOfUseContent;
	private String _termsOfUseContentCurrentLanguageId;
	private long _termsOfUseJournalArticleId;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private CPDefinitionVirtualSetting _escapedModel;
}