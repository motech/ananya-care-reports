package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ChartTypeEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;

import java.util.Set;

public interface ReportService {

    Set<ReportEntity> findAllReports();

    ReportEntity findReportById(Integer id);

    void createNewReport(ReportEntity reportEntity);

    void updateReport(ReportEntity reportEntity);

    void deleteReport(ReportEntity reportEntity);

    Set<ReportTypeEntity> findAllReportTypes();

    ReportTypeEntity findReportTypeById(Integer id);

    void createNewReportType(ReportTypeEntity reportTypeEntity);

    void updateReportType(ReportTypeEntity reportTypeEntity);

    void deleteReportType(ReportTypeEntity reportTypeEntity);

    Set<DashboardEntity> findAllDashboards();

    DashboardEntity findDashboardById(Integer id);

    void createNewDashboard(DashboardEntity dashboardEntity);

    void updateDashboard(DashboardEntity dashboardEntity);

    void deleteDashboard(DashboardEntity dashboardEntity);

    Set<ChartTypeEntity> findAllChartTypes();

    ChartTypeEntity findChartTypeById(Integer id);

    void createNewChartType(ChartTypeEntity chartTypeEntity);

    void updateChartType(ChartTypeEntity chartTypeEntity);

    void deleteChartType(ChartTypeEntity chartTypeEntity);
}
