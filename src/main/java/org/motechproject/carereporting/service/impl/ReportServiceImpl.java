package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ReportDao;
import org.motechproject.carereporting.dao.ReportTypeDao;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.domain.dto.CategorizedValueDto;
import org.motechproject.carereporting.domain.dto.IndicatorValueDto;
import org.motechproject.carereporting.domain.dto.ReportDto;
import org.motechproject.carereporting.domain.types.ReportType;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.web.chart.Chart;
import org.motechproject.carereporting.web.chart.ChartFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Autowired
    private ChartFactory chartFactory;

    @Override
    @Transactional
    public Set<ReportEntity> getAllReports() {
        return reportDao.getAll();
    }

    @Override
    @Transactional
    public ReportEntity getReportById(Integer id) {
        return reportDao.getById(id);
    }

    @Override
    public ReportEntity getReportByTypeAndIndicatorId(ReportType reportType, Integer indicatorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("indicator.id", indicatorId);
        params.put("reportType.id", reportType.getValue());

        return reportDao.getByFields(params);
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
    public void updateReport(ReportDto reportDto) {
        ReportEntity reportEntity = fetchAndUpdateReport(
                reportDto.getId(),
                reportDto.getIndicatorId(),
                reportDto.getReportTypeId(),
                reportDto.getLabelX(),
                reportDto.getLabelY());

        try {
            reportDao.update(reportEntity);
        } catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException e) {
            throw new EntityException(e);
        }
    }

    private ReportEntity fetchAndUpdateReport(Integer reportId, Integer indicatorId, Integer reportTypeId,
            String labelX, String labelY) {
        ReportEntity reportEntity = reportDao.getById(reportId);
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setId(indicatorId);
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
    public Set<ReportTypeEntity> getAllReportTypes() {
        return reportTypeDao.getAll();
    }

    @Override
    @Transactional
    public ReportTypeEntity getReportTypeById(Integer id) {
        return reportTypeDao.getById(id);
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
    public Chart prepareChart(IndicatorEntity indicator, String chartType, List<IndicatorValueDto> values) {
        if (chartType.startsWith("pie")) {
            return chartFactory.createPieChart(indicator, values);
        } else if (chartType.startsWith("bar")) {
            return chartFactory.createLineOrBarChart(indicator, values, ReportType.BarChart);
        } else if (chartType.startsWith("line")) {
            return chartFactory.createLineOrBarChart(indicator, values, ReportType.LineChart);
        } else {
            throw new IllegalArgumentException("Chart type " + chartType + " not supported");
        }
    }

    @Override
    public Chart prepareCategorizedChart(IndicatorEntity indicator, String chartType, List<CategorizedValueDto> values) {
        switch (chartType) {
            case "clustered bar chart":
                return chartFactory.createClusteredBarChart(indicator, values);
            default: throw new IllegalArgumentException("Chart type " + chartType +
                    " not supported");
        }
    }

}
