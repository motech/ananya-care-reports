package org.motechproject.carereporting.indicator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class IndicatorValueCalculatorImpl implements IndicatorValueCalculator {
/*
    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private AreaService areaService;

    @Resource(name = "careDataSource")
    private DataSource dataSource;

    private static final Logger LOG = Logger.getLogger(IndicatorValueCalculatorImpl.class);

    @Override
    @Transactional(readOnly = false)
    public int calculateAllIndicatorsValues() {
        int calculatedIndicatorsCount = 0;
        Set<IndicatorEntity> indicators = indicatorService.findAllIndicators();
        for (IndicatorEntity indicator: indicators) {
            LOG.info("Calculating values for indicator: " + indicator.getName());
            calculateAndPersistIndicatorValues(indicator);
            ++calculatedIndicatorsCount;
            LOG.info("Calculating values for indicator: " + indicator.getName() + " finished.");
        }
        return calculatedIndicatorsCount;
    }

    // TODO: Adapt this methods to new entities

    private void calculateAndPersistIndicatorValues(IndicatorEntity indicator) {
        for (ComplexConditionEntity complexCondition: indicator.getComplexConditions()) {
            for (AreaEntity area: areaService.findAllAreas()) {
                //String indicatorType = indicator.getIndicatorType().getName();
                BigDecimal value = calculateComplexConditionForArea(complexCondition, area);
                IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(new Date(),
                        indicator, area, complexCondition, value);
                indicatorService.createNewIndicatorValue(indicatorValueEntity);
            }
        }
    }

    private BigDecimal calculateComplexConditionForArea(//String indicatorType,
            ComplexConditionEntity complexCondition, AreaEntity area) {
        String tableName = complexCondition.getForm().getTableName();
        String field = complexCondition.getField();
        String comparisonSymbol = complexCondition.getComparisonSymbol().getName();
        String areaName = area.getName();
        //String operatorName = complexCondition.getOperatorType().getName();

        BigDecimal comparisonValue = complexCondition.getComparisonValue();

        StringBuilder query = new StringBuilder();

        query.append("SELECT count(*) FROM care.")
                .append(tableName)
                .append(" as t INNER JOIN care.flw as flw ON t.user_id = flw.id")
                .append(" WHERE t.")
                .append(field)
                .append(" ")
                .append(comparisonSymbol)
                .append(" :comparisonValue AND (flw.block = :areaName OR flw.district = :areaName OR flw.village = :areaName);");

        MapSqlParameterSource parameters =
                new MapSqlParameterSource("comparisonValue", comparisonValue)
                        .addValue("areaName", areaName);

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query.toString(), parameters, BigDecimal.class);
    }
*/
}
