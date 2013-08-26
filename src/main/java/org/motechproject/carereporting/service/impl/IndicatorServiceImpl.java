package org.motechproject.carereporting.service.impl;

import org.apache.commons.lang.StringUtils;
import org.dwQueryBuilder.builders.QueryBuilder;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.jooq.SQLDialect;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.IndicatorTypeDao;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexDwQueryEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.SimpleDwQueryEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.domain.dto.IndicatorWithTrendDto;
import org.motechproject.carereporting.domain.dto.TrendIndicatorCategoryDto;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.indicator.DwQueryHelper;
import org.motechproject.carereporting.initializers.IndicatorValuesInitializer;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.ExportService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.utils.configuration.ConfigurationLocator;
import org.motechproject.carereporting.utils.date.DateResolver;
import org.motechproject.carereporting.xml.XmlCaseListReportParser;
import org.motechproject.carereporting.xml.mapping.reports.CaseListReport;
import org.motechproject.carereporting.xml.mapping.reports.ReportField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class IndicatorServiceImpl implements IndicatorService {

    private static final int TREND_NEUTRAL = 0;
    private static final int TREND_NEGATIVE = -1;
    private static final int TREND_POSITIVE = 1;
    private static final SQLDialect SQL_DIALECT = SQLDialect.POSTGRES;
    private static final String REPLACE_SELECT_WITH_SELECT_COLUMNS =
            "select (.*?) from \\\"(.*?)\\\"\\.\\\"(\\w*?_case)\\\"(.*)?";
    private static final String CASE_LIST_REPORT_XML_DIRECTORY = ConfigurationLocator.getCareXmlDirectory()
            + File.separatorChar + "caseListReport";

    @Value("${care.jdbc.schema}")
    private String schemaName;

    @Resource(name = "careDataSource")
    private DataSource careDataSource;

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private IndicatorTypeDao indicatorTypeDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Autowired
    private IndicatorValueDao indicatorValueDao;

    @Autowired
    private CronService cronService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExportService csvExportService;

    @Autowired
    private XmlCaseListReportParser xmlCaseListReportParser;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Set<IndicatorEntity> getAllIndicators() {
        return indicatorDao.getAllWithFields("reports");
    }

    @Transactional
    @Override
    public Set<IndicatorEntity> getIndicatorsByCategoryId(Integer categoryId) {
        return indicatorDao.getIndicatorsByCategoryId(categoryId);
    }

    private Set<AreaEntity> getAllChildEntities(AreaEntity areaEntity) {
        Set<AreaEntity> areas = new LinkedHashSet<>();

        areas.add(areaEntity);
        for (AreaEntity area : areaEntity.getChildAreas()) {
            areas.addAll(getAllChildEntities(area));
        }

        return areas;
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public Set<IndicatorEntity> getAllIndicatorsUnderUserArea(Integer areaId) {
        AreaEntity areaEntity = areaService.getAreaById(areaId);
        Set<AreaEntity> areaEntities = getAllChildEntities(areaEntity);

        List<Integer> areaIds = new ArrayList<>();
        for (AreaEntity area : areaEntities) {
            areaIds.add(area.getId());
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery("from IndicatorEntity where area.id in (:areaEntities)");
        query.setParameterList("areaEntities", areaIds);

        return new LinkedHashSet<IndicatorEntity>(query.list());
    }

    @Transactional
    @Override
    public IndicatorEntity getIndicatorById(Integer id) {
        return indicatorDao.getByIdWithFields(id, "reports");
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.save(indicatorEntity);
        calculateIndicator(indicatorEntity);
    }

    @Override
    public void createNewIndicatorFromDto(IndicatorDto indicatorDto) {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setCategories(findIndicatorCategoryEntitiesFromDto(indicatorDto));
        indicatorEntity.setArea(findAreaEntityFromDto(indicatorDto));
        //indicatorEntity.setOwners(findUserEntitiesFromDto(indicatorDto)); TODO: set owner and roles
        indicatorEntity.setReports(indicatorDto.getReports());
        indicatorEntity.setDefaultFrequency(findFrequencyEntityFromDto(indicatorDto));
        indicatorEntity.setName(indicatorDto.getName());
        indicatorEntity.setTrend(indicatorDto.getTrend());
        createNewIndicator(indicatorEntity);
    }

    private FrequencyEntity findFrequencyEntityFromDto(IndicatorDto indicatorDto) {
        return cronService.getFrequencyById(indicatorDto.getFrequency());
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.update(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void setComputingForIndicator(IndicatorEntity indicatorEntity, Boolean value) {
        indicatorEntity.setComputed(value);
        indicatorDao.update(indicatorEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorFromDto(IndicatorDto indicatorDto) {
        IndicatorEntity indicatorEntity = this.getIndicatorById(indicatorDto.getId());

        indicatorEntity.setCategories(findIndicatorCategoryEntitiesFromDto(indicatorDto));
        indicatorEntity.setArea(findAreaEntityFromDto(indicatorDto));
        //indicatorEntity.setOwners(findUserEntitiesFromDto(indicatorDto)); TODO: set owner and roles
        indicatorEntity.setTrend(indicatorDto.getTrend());
        indicatorEntity.setReports(setUpdatedReports(indicatorDto.getReports(), indicatorEntity));
        indicatorEntity.setDefaultFrequency(findFrequencyEntityFromDto(indicatorDto));
        indicatorEntity.setName(indicatorDto.getName());

        indicatorDao.update(indicatorEntity);
    }


    private Set<ReportEntity> setUpdatedReports(Set<ReportEntity> reportsFromDto, IndicatorEntity indicatorEntity) {
        Set<ReportEntity> reportsUpdated = new HashSet<>();
        Set<ReportEntity> reportsToUpdate = indicatorEntity.getReports();
        for (ReportEntity reportForm : reportsFromDto) {
            if (reportForm.getId() == null) {
                reportForm.setIndicator(indicatorEntity);
                reportsUpdated.add(reportForm);
                continue;
            }
            for (ReportEntity reportToUpdate : reportsToUpdate) {
                if (reportForm.getId() != null && reportForm.getId().equals(reportToUpdate.getId())) {
                    reportToUpdate.setReportType(reportForm.getReportType());
                    reportsUpdated.add(reportToUpdate);
                    break;
                }
            }
        }
        reportsToUpdate.removeAll(reportsUpdated);
        reportService.deleteReportSet(reportsToUpdate);
        return reportsUpdated;
    }

    private Set<IndicatorCategoryEntity> findIndicatorCategoryEntitiesFromDto(
            IndicatorDto indicatorDto) {
        Set<IndicatorCategoryEntity> indicatorCategoryEntities = new LinkedHashSet<>();

        for (Integer indicatorCategoryId : indicatorDto.getCategories()) {
            IndicatorCategoryEntity indicatorCategoryEntity = this.getIndicatorCategoryById(indicatorCategoryId);

            indicatorCategoryEntities.add(indicatorCategoryEntity);
        }

        return indicatorCategoryEntities;
    }

    private AreaEntity findAreaEntityFromDto(IndicatorDto indicatorDto) {
        return areaService.getAreaById(indicatorDto.getArea());
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicator(IndicatorEntity indicatorEntity) {
        indicatorDao.remove(indicatorEntity);
    }

    // IndicatorTypeEntity

    @Transactional
    @Override
    public Set<IndicatorTypeEntity> getAllIndicatorTypes() {
        return indicatorTypeDao.getAll();
    }

    @Transactional
    @Override
    public IndicatorTypeEntity getIndicatorTypeById(Integer id) {
        return indicatorTypeDao.getById(id);
    }

    // IndicatorCategoryEntity

    @Transactional
    @Override
    public Set<IndicatorCategoryEntity> getAllIndicatorCategories() {
        return indicatorCategoryDao.getAll();
    }

    @Transactional
    @Override
    public IndicatorCategoryEntity getIndicatorCategoryById(Integer id) {
        return indicatorCategoryDao.getById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        DashboardEntity dashboardForCategory = createDashboardForNewIndicatorCategory(indicatorCategoryEntity.getName());
        indicatorCategoryEntity.setDashboard(dashboardForCategory);
        indicatorCategoryDao.save(indicatorCategoryEntity);
    }

    private DashboardEntity createDashboardForNewIndicatorCategory(String name) {
        Short newDashboardTabPosition = dashboardService.getTabPositionForNewDashboard();
        return new DashboardEntity(name, newDashboardTabPosition);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        indicatorCategoryEntity.getDashboard().setName(indicatorCategoryEntity.getName());
        indicatorCategoryDao.update(indicatorCategoryEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity) {
        for (IndicatorEntity indicatorEntity : indicatorCategoryEntity.getIndicators()) {
            indicatorEntity.getCategories().remove(indicatorCategoryEntity);
        }

        indicatorCategoryDao.remove(indicatorCategoryEntity);
    }

    @Transactional
    @Override
    public Set<IndicatorValueEntity> getAllIndicatorValues() {
        return indicatorValueDao.getAll();
    }

    // IndicatorValueEntity

    @Transactional
    @Override
    public IndicatorValueEntity getIndicatorValueById(Integer id) {
        return indicatorValueDao.getById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.save(indicatorValueEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.update(indicatorValueEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity) {
        indicatorValueDao.remove(indicatorValueEntity);
    }

    @Override
    @Transactional
    public List<IndicatorValueEntity> getIndicatorValuesForArea(Integer indicatorId, Integer areaId, Integer frequencyId,
                                                                Date startDate, Date endDate) {
        return indicatorValueDao.getIndicatorValuesForArea(indicatorId, areaId, frequencyId, startDate, endDate);
    }

    @Override
    @Transactional
    public Set<TrendIndicatorCategoryDto> getIndicatorsWithTrendsUnderUser(UserEntity user, Date startDate, Date endDate, Integer areaId, Integer frequencyId) {
        Set<TrendIndicatorCategoryDto> categories = new LinkedHashSet<>();
        Set<IndicatorCategoryEntity> indicatorCategories = getAllIndicatorCategories();
        AreaEntity area = areaService.getAreaById(areaId);

        for (IndicatorCategoryEntity indicatorCategory: indicatorCategories) {
            TrendIndicatorCategoryDto trendCategory = new TrendIndicatorCategoryDto(indicatorCategory.getName());
            categories.add(trendCategory);
            for (IndicatorEntity indicator: indicatorCategory.getIndicators()) {

                if (isIndicatorAccessibleForUser(indicator, user) && indicator.getTrend() != null) {
                    IndicatorWithTrendDto trendIndicator = new IndicatorWithTrendDto(indicator,
                            getTrendForIndicator(area, indicator, frequencyId, startDate, endDate));
                    trendCategory.getIndicators().add(trendIndicator);
                }
            }
        }
        return categories;
    }

    private boolean isIndicatorAccessibleForUser(IndicatorEntity indicatorEntity, UserEntity userEntity) {
        return userEntity.equals(indicatorEntity.getOwner()) ||
                hasIndicatorCommonRoleWithUser(indicatorEntity, userEntity);
    }

    private boolean hasIndicatorCommonRoleWithUser(IndicatorEntity indicatorEntity, UserEntity userEntity) {
        return !Collections.disjoint(indicatorEntity.getRoles(), userEntity.getRoles());
    }

    @Override
    public Map<AreaEntity, Integer> getIndicatorTrendForChildAreas(
            Integer indicatorId, Integer parentAreaId, Integer frequencyId, Date startDate, Date endDate) {
        IndicatorEntity indicator = getIndicatorById(indicatorId);
        Map<AreaEntity, Integer> areasTrends = new LinkedHashMap<>();
        if (indicator.getTrend() == null) {
            return areasTrends;
        }
        Set<AreaEntity> areas;
        if (parentAreaId != null) {
            areas = areaService.getAllAreasByParentAreaId(parentAreaId);
        } else {
            areas = areaService.getAllTopLevelAreas();
        }
        for (AreaEntity area: areas) {
            int trend = getTrendForIndicator(area, indicator, frequencyId, startDate, endDate);
            areasTrends.put(area, trend);
        }
        return areasTrends;
    }

    @Override
    public byte[] getCaseListReportAsCsv(IndicatorEntity indicatorEntity, Integer areaId, Date fromDate, Date toDate) {
        try {
            DwQueryEntity numerator = indicatorEntity.getNumerator();
            DwQueryHelper dwQueryHelper = new DwQueryHelper();
            String tableName = getDwQueryEntityTableName(numerator);
            CaseListReport caseListReport = getCaseListReportFromXml(tableName);
            AreaEntity areaEntity = areaService.getAreaById(areaId);

            String sqlString = QueryBuilder.getDwQueryAsSQLString(SQL_DIALECT,
                    schemaName, dwQueryHelper.buildDwQuery(numerator, areaEntity), false);
            if (fromDate != null && toDate != null) {
                if (fromDate.compareTo(toDate) > 0) {
                    throw new CareRuntimeException("Value of field 'fromDate' must be less or equal to 'toDate'.");
                }

                sqlString = dwQueryHelper.formatFromDateAndToDate(sqlString, fromDate, toDate);
            }

            List<String> fields = new ArrayList<>();
            List<String> headers = new ArrayList<>();
            if (caseListReport.getFields() == null) {
                return null;
            }

            for (ReportField reportField : caseListReport.getFields()) {
                fields.add(constructCaseListReportFieldName(tableName, reportField));
                headers.add(reportField.getDisplayName());
            }

            sqlString = sqlString.replaceFirst(REPLACE_SELECT_WITH_SELECT_COLUMNS,
                    String.format("select %s from ", StringUtils.join(fields, ", "))
                            + "\"$2\".\"$3\"$4");

            JdbcTemplate jdbcTemplate = new JdbcTemplate(careDataSource);
            return csvExportService.convertRowMapToBytes(headers, jdbcTemplate.queryForList(sqlString));
        } catch (Exception e) {
            throw new CareRuntimeException(e);
        }
    }

    private String constructCaseListReportFieldName(String tableName, ReportField reportField) {
        return "\"" + tableName + "\".\"" + reportField.getDbName() + "\"";
    }

    private CaseListReport getCaseListReportFromXml(String tableName) throws JAXBException, IOException {
        String xmlFilePath = CASE_LIST_REPORT_XML_DIRECTORY + File.separator + tableName + ".xml";
        File caseListXmlFile = new File(xmlFilePath);

        if (caseListXmlFile.exists()) {
            return xmlCaseListReportParser.parse(caseListXmlFile);
        } else {
            ClassPathResource caseListReportXmlFile = new ClassPathResource("xml/" + tableName + ".xml");
            return xmlCaseListReportParser.parse(caseListReportXmlFile.getFile());
        }
    }

    private String getDwQueryEntityTableName(DwQueryEntity dwQueryEntity) {
        if (dwQueryEntity instanceof SimpleDwQueryEntity) {
            return ((SimpleDwQueryEntity) dwQueryEntity).getTableName();
        } else if (dwQueryEntity instanceof ComplexDwQueryEntity) {
            return ((ComplexDwQueryEntity) dwQueryEntity).getDimension();
        }

        throw new CareRuntimeException("Cannot recognize query type.");
    }

    @Override
    public Date getDateDepth() {
        String sqlString = "SELECT date_depth FROM dashboard_app.date_depth";
        return (Date) sessionFactory.getCurrentSession().createSQLQuery(sqlString).uniqueResult();
    }

    @Override
    @Transactional
    public void updateDateDepth(Date newDateDepth) {
        String sqlString = "UPDATE dashboard_app.date_depth set date_depth = :dateDepth";
        sessionFactory.getCurrentSession().createSQLQuery(sqlString).setParameter("dateDepth", newDateDepth).executeUpdate();
    }

    @Override
    public void calculateIndicator(IndicatorEntity indicatorEntity) {
        Thread thread = new Thread(new IndicatorValuesInitializer(indicatorEntity));
        thread.start();
    }

    @Override
    @Transactional(readOnly = false)
    public void calculateAllIndicators() {
        for (IndicatorEntity indicator : indicatorDao.getAll()) {
            indicatorValueDao.removeByIndicator(indicator);
            setComputingForIndicator(indicator, false);
            calculateIndicator(indicator);
        }
    }

    private int getTrendForIndicator(AreaEntity area, IndicatorEntity indicator, Integer frequencyId, Date startDate, Date endDate) {

        if (indicator.getTrend() == null) {
            throw new IllegalArgumentException("Cannot calculate trend value for indicator with null trend.");
        }
        FrequencyEntity frequency = cronService.getFrequencyById(frequencyId);
        Date[] dates = DateResolver.resolveDates(frequency, startDate, endDate);

        List<IndicatorValueEntity> values = indicatorValueDao.getIndicatorValuesForArea(indicator.getId(), area.getId(), frequencyId, dates[0], dates[1]);

        if (values.size() < 2) {
            return TREND_NEUTRAL;
        }

        BigDecimal diff = values.get(values.size() - 1).getValue().subtract(values.get(0).getValue());

        if (diff.compareTo(indicator.getTrend().negate()) < 0) {
            return TREND_NEGATIVE;
        } else if (diff.compareTo(indicator.getTrend()) > 0) {
            return TREND_POSITIVE;
        }
        return TREND_NEUTRAL;
    }

}
