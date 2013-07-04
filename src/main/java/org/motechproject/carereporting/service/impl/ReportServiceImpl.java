package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ChartTypeDao;
import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.dao.ReportDao;
import org.motechproject.carereporting.dao.ReportTypeDao;
import org.motechproject.carereporting.domain.ChartTypeEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl extends AbstractService implements ReportService {

    @Autowired
    private DashboardDao dashboardDao;

    @Autowired
    private ChartTypeDao chartTypeDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

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
    @Transactional(readOnly = false)
    public void createNewReport(ReportEntity reportEntity) {
        reportDao.save(reportEntity);
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

    @Override
    @Transactional
    public Set<ChartTypeEntity> findAllChartTypes() {
        return chartTypeDao.findAll();
    }

    @Override
    @Transactional
    public ChartTypeEntity findChartTypeById(Integer id) {
        return chartTypeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.save(chartTypeEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.update(chartTypeEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.remove(chartTypeEntity);
    }

}
