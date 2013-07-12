package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface ReportService {

    String HAS_ROLE_MANAGE_REPORTS = "hasRole('CAN_MANAGE_REPORTS')";

    Set<ReportEntity> findAllReports();

    ReportEntity findReportById(Integer id);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    void createNewReport(ReportEntity reportEntity);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    ReportEntity createNewReport(Integer indicatorId, Integer reportTypeId);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    void updateReport(Integer reportId, Integer indicatorId, Integer reportTypeId);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    void updateReport(ReportEntity reportEntity);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    void deleteReport(ReportEntity reportEntity);

    @PreAuthorize(HAS_ROLE_MANAGE_REPORTS)
    void deleteReportById(Integer reportId);

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
}
