package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.domain.dto.CategorizedValueDto;
import org.motechproject.carereporting.domain.dto.IndicatorValueDto;
import org.motechproject.carereporting.domain.dto.ReportDto;
import org.motechproject.carereporting.domain.types.ReportType;
import org.motechproject.carereporting.web.chart.Chart;

import java.util.List;
import java.util.Set;

public interface ReportService {
    Set<ReportEntity> getAllReports();

    ReportEntity getReportById(Integer id);

    ReportEntity getReportByTypeAndIndicatorId(ReportType reportType, Integer indicatorId);

    void createNewReport(ReportEntity reportEntity);

    ReportEntity createNewReport(Integer indicatorId, Integer reportTypeId);

    void updateReport(ReportDto reportDto);

    void updateReport(ReportEntity reportEntity);

    void deleteReport(ReportEntity reportEntity);

    void deleteReportSet(Set<ReportEntity> reportsEntity);

    void deleteReportById(Integer reportId);

    Set<ReportTypeEntity> getAllReportTypes();

    ReportTypeEntity getReportTypeById(Integer id);

    void createNewReportType(ReportTypeEntity reportTypeEntity);

    void updateReportType(ReportTypeEntity reportTypeEntity);

    void deleteReportType(ReportTypeEntity reportTypeEntity);

    Chart prepareChart(IndicatorEntity indicator, String chartType, List<IndicatorValueDto> values);

    Chart prepareCategorizedChart(IndicatorEntity indicator, String chartType, List<CategorizedValueDto> values);
}
