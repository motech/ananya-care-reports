package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.dao.ReportDao;
import org.motechproject.carereporting.dao.ReportTypeDao;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.domain.forms.ReportFormObject;
import org.motechproject.carereporting.enums.ReportType;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private DashboardDao dashboardDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Set<ReportEntity> findAllReports() {
        return reportDao.findAll();
    }

    @Override
    @Transactional
    public ReportEntity findReportById(Integer id) {
        return reportDao.findById(id);
    }

    @Override
    public ReportEntity findReportByTypeAndIndicatorId(ReportType reportType, Integer indicatorId) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from ReportEntity where reportType.id = :reportTypeId"
                        + " and indicator.id = :indicatorId");
        query.setParameter("reportTypeId", reportType.getValue());
        query.setParameter("indicatorId", indicatorId);

        return (ReportEntity)query.list().get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewReport(ReportEntity reportEntity) {
        reportDao.save(reportEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public ReportEntity createNewReport(Integer indicatorId, Integer reportTypeId) {
        ReportEntity reportEntity = new ReportEntity(indicatorId, reportTypeId);

        try {
            reportDao.save(reportEntity);
            return reportEntity;
        } catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException e) {
            throw new EntityException(e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateReport(ReportFormObject reportFormObject) {
        ReportEntity reportEntity = fetchAndUpdateReport(
                reportFormObject.getId(),
                reportFormObject.getIndicatorId(),
                reportFormObject.getReportTypeId(),
                reportFormObject.getLabelX(),
                reportFormObject.getLabelY());

        try {
            reportDao.update(reportEntity);
        } catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException e) {
            throw new EntityException(e);
        }
    }

    private ReportEntity fetchAndUpdateReport(Integer reportId, Integer indicatorId, Integer reportTypeId,
            String labelX, String labelY) {
        ReportEntity reportEntity = reportDao.findById(reportId);
        IndicatorEntity indicatorEntity = new IndicatorEntity(indicatorId);
        reportEntity.setIndicator(indicatorEntity);
        ReportTypeEntity reportTypeEntity = new ReportTypeEntity(reportTypeId);
        reportEntity.setReportType(reportTypeEntity);
        reportEntity.setLabelX(labelX);
        reportEntity.setLabelY(labelY);
        return reportEntity;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateReport(ReportEntity reportEntity) {
        reportDao.update(reportEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReport(ReportEntity reportEntity) {
        reportDao.remove(reportEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReportSet(Set<ReportEntity> reportsEntity) {
        for (ReportEntity reportEntity : reportsEntity) {
            reportDao.remove(reportEntity);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReportById(Integer reportId) {
        ReportEntity reportEntity = new ReportEntity(reportId);
        reportDao.remove(reportEntity);
    }

    @Override
    @Transactional
    public Set<ReportTypeEntity> findAllReportTypes() {
        return reportTypeDao.findAll();
    }

    @Override
    @Transactional
    public ReportTypeEntity findReportTypeById(Integer id) {
        return reportTypeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.save(reportTypeEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.update(reportTypeEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.remove(reportTypeEntity);
    }

    @Override
    @Transactional
    public Set<DashboardEntity> findAllDashboards() {
        return dashboardDao.findAll();
    }

    @Override
    @Transactional
    public DashboardEntity findDashboardById(Integer id) {
        return dashboardDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.save(dashboardEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.update(dashboardEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.remove(dashboardEntity);
    }
}
