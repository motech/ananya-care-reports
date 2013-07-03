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

import java.util.Set;

public class ReportServiceImpl implements ReportService {

    @Autowired
    private DashboardDao dashboardDao;

    @Autowired
    private ChartTypeDao chartTypeDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Override
    public Set<ReportEntity> findAllReports() {
        return reportDao.findAll();
    }

    @Override
    public ReportEntity findReportById(Integer id) {
        return reportDao.findById(id);
    }

    @Override
    public void createNewReport(ReportEntity reportEntity) {
        reportDao.save(reportEntity);
    }

    @Override
    public void updateReport(ReportEntity reportEntity) {
        reportDao.update(reportEntity);
    }

    @Override
    public void deleteReport(ReportEntity reportEntity) {
        reportDao.remove(reportEntity);
    }

    @Override
    public Set<ReportTypeEntity> findAllReportTypes() {
        return reportTypeDao.findAll();
    }

    @Override
    public ReportTypeEntity findReportTypeById(Integer id) {
        return reportTypeDao.findById(id);
    }

    @Override
    public void createNewReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.save(reportTypeEntity);
    }

    @Override
    public void updateReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.update(reportTypeEntity);
    }

    @Override
    public void deleteReportType(ReportTypeEntity reportTypeEntity) {
        reportTypeDao.remove(reportTypeEntity);
    }

    @Override
    public Set<DashboardEntity> findAllDashboards() {
        return dashboardDao.findAll();
    }

    @Override
    public DashboardEntity findDashboardById(Integer id) {
        return dashboardDao.findById(id);
    }

    @Override
    public void createNewDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.save(dashboardEntity);
    }

    @Override
    public void updateDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.update(dashboardEntity);
    }

    @Override
    public void deleteDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.remove(dashboardEntity);
    }

    @Override
    public Set<ChartTypeEntity> findAllChartTypes() {
        return chartTypeDao.findAll();
    }

    @Override
    public ChartTypeEntity findChartTypeById(Integer id) {
        return chartTypeDao.findById(id);
    }

    @Override
    public void createNewChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.save(chartTypeEntity);
    }

    @Override
    public void updateChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.update(chartTypeEntity);
    }

    @Override
    public void deleteChartType(ChartTypeEntity chartTypeEntity) {
        chartTypeDao.remove(chartTypeEntity);
    }

}