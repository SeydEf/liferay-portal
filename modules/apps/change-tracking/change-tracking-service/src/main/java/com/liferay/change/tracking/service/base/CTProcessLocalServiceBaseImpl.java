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

package com.liferay.change.tracking.service.base;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.persistence.CTCollectionFinder;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.service.persistence.CTProcessFinder;
import com.liferay.change.tracking.service.persistence.CTProcessPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the ct process local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.change.tracking.service.impl.CTProcessLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.service.impl.CTProcessLocalServiceImpl
 * @generated
 */
public abstract class CTProcessLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CTProcessLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CTProcessLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.change.tracking.service.CTProcessLocalServiceUtil</code>.
	 */

	/**
	 * Adds the ct process to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTProcess addCTProcess(CTProcess ctProcess) {
		ctProcess.setNew(true);

		return ctProcessPersistence.update(ctProcess);
	}

	/**
	 * Creates a new ct process with the primary key. Does not add the ct process to the database.
	 *
	 * @param ctProcessId the primary key for the new ct process
	 * @return the new ct process
	 */
	@Override
	@Transactional(enabled = false)
	public CTProcess createCTProcess(long ctProcessId) {
		return ctProcessPersistence.create(ctProcessId);
	}

	/**
	 * Deletes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws PortalException if a ct process with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CTProcess deleteCTProcess(long ctProcessId) throws PortalException {
		return ctProcessPersistence.remove(ctProcessId);
	}

	/**
	 * Deletes the ct process from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CTProcess deleteCTProcess(CTProcess ctProcess) {
		return ctProcessPersistence.remove(ctProcess);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CTProcess.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return ctProcessPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return ctProcessPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return ctProcessPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return ctProcessPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return ctProcessPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CTProcess fetchCTProcess(long ctProcessId) {
		return ctProcessPersistence.fetchByPrimaryKey(ctProcessId);
	}

	/**
	 * Returns the ct process with the primary key.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process
	 * @throws PortalException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess getCTProcess(long ctProcessId) throws PortalException {
		return ctProcessPersistence.findByPrimaryKey(ctProcessId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ctProcessLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CTProcess.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("ctProcessId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			ctProcessLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CTProcess.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"ctProcessId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(ctProcessLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CTProcess.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("ctProcessId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ctProcessPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ctProcessLocalService.deleteCTProcess((CTProcess)persistedModel);
	}

	public BasePersistence<CTProcess> getBasePersistence() {
		return ctProcessPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ctProcessPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of ct processes
	 */
	@Override
	public List<CTProcess> getCTProcesses(int start, int end) {
		return ctProcessPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ct processes.
	 *
	 * @return the number of ct processes
	 */
	@Override
	public int getCTProcessesCount() {
		return ctProcessPersistence.countAll();
	}

	/**
	 * Updates the ct process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTProcess updateCTProcess(CTProcess ctProcess) {
		return ctProcessPersistence.update(ctProcess);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CTProcessLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		ctProcessLocalService = (CTProcessLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CTProcessLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CTProcess.class;
	}

	protected String getModelClassName() {
		return CTProcess.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ctProcessPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected CTProcessLocalService ctProcessLocalService;

	@Reference
	protected CTProcessPersistence ctProcessPersistence;

	@Reference
	protected CTProcessFinder ctProcessFinder;

	@Reference
	protected CTCollectionPersistence ctCollectionPersistence;

	@Reference
	protected CTCollectionFinder ctCollectionFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.CompanyLocalService
		companyLocalService;

}