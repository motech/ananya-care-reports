package org.motechproject.carereporting.indicator.calculator;

import org.jooq.SelectQuery;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.utils.date.DateResolver;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractIndicatorValueCalculator {

    private IndicatorService indicatorService;

    protected FrequencyEntity frequencyEntity;

    protected final IndicatorEntity indicator;

    protected CalculatorQueryBuilder calculatorQueryBuilder;

    protected CalculatorQueryBuilder.OperationType nominatorOperationType;

    public AbstractIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                            IndicatorService indicatorService, FormsService formsService) {
        this.indicator = indicator;
        this.indicatorService = indicatorService;
        this.calculatorQueryBuilder = new CalculatorQueryBuilder(dataSource, formsService);
    }

    public void calculateAndPersistIndicatorValues(FrequencyEntity frequency, Date startDate) {
        frequencyEntity = frequency;

        for (AreaEntity area: getAllAreasForIndicator(indicator)) {
            List<IndicatorValueEntity> values = getValuesFromDatabase(area, startDate);
            BigDecimal nominator = getNominator(area, values);
            BigDecimal denominator = getDenominator(area, values);

            BigDecimal value = denominator.compareTo(BigDecimal.ZERO) != 0 ? nominator.divide(denominator, 1, RoundingMode.HALF_UP) : null;

            if (value != null) {
                if (this instanceof  PercentageIndicatorValueCalculator) {
                    value = value.multiply(new BigDecimal(100));
                }
                IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(indicator, area, nominator, denominator, value, frequencyEntity, startDate);
                indicatorService.createNewIndicatorValue(indicatorValueEntity);
            }
        }
    }

    private List<AreaEntity> getAllAreasForIndicator(IndicatorEntity indicator) {
        List<AreaEntity> areas = new ArrayList<>();
        areas.add(indicator.getArea());
        areas.addAll(getAllChildAreas(indicator.getArea()));
        return areas;
    }

    private List<AreaEntity> getAllChildAreas(AreaEntity area) {
        List<AreaEntity> childAreas = new ArrayList<>();
        for (AreaEntity child: area.getChildAreas()) {
            childAreas.add(child);
            childAreas.addAll(getAllChildAreas(child));
        }
        return childAreas;
    }

    private List<IndicatorValueEntity> getValuesFromDatabase(AreaEntity area, Date startDate) {
        List<IndicatorValueEntity> values = null;

        if (!"defined".equals(frequencyEntity.getFrequencyName()) && !"daily".equals(frequencyEntity.getFrequencyName())) {
            Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate);
            FrequencyEntity child = resolveChild();
            values = indicatorService.getIndicatorValuesForArea(indicator.getId(), area.getId(), child.getId(), dates[0], dates[1]);
        }

        return values;
    }

    private FrequencyEntity resolveChild() {
        FrequencyEntity child = frequencyEntity;

        switch(frequencyEntity.getFrequencyName()) {
            case "monthly":
                child = child.getChildFrequency();
            case "weekly":
            case "quarterly":
            case "yearly":
                child = child.getChildFrequency();
                break;
            default:
                break;
        }

        return child;
    }

    protected BigDecimal getNominator(AreaEntity areaEntity, List<IndicatorValueEntity> values) {
        BigDecimal result = BigDecimal.ZERO;

        if ("defined".equals(frequencyEntity.getFrequencyName()) || "daily".equals(frequencyEntity.getFrequencyName())) {
            result = queryForResult(areaEntity);
        } else {
            for (IndicatorValueEntity indicatorValueEntity: values) {
                result = result.add(indicatorValueEntity.getNominator());
            }
        }

        return result;
    }

    protected BigDecimal getDenominator(AreaEntity areaEntity, List<IndicatorValueEntity> values) {
        BigDecimal result = BigDecimal.ZERO;

        if ("defined".equals(frequencyEntity.getFrequencyName()) || "daily".equals(frequencyEntity.getFrequencyName())) {
            result = queryForResult(areaEntity);
        } else {
            for (IndicatorValueEntity indicatorValueEntity: values) {
                result = result.add(indicatorValueEntity.getDenominator());
            }
        }

        return result;
    }

    private BigDecimal queryForResult(AreaEntity area) {
        SelectQuery query = createSelectQuery(area);
        return calculateResult(query.fetch().getValue(0, 0));
    }

    private SelectQuery createSelectQuery(AreaEntity area) {
        return calculatorQueryBuilder
                .withIndicator(indicator)
                .withArea(area)
                .withFrequency(indicator.getDefaultFrequency().getId())
                .withOperation(nominatorOperationType)
                .build();
    }

    private BigDecimal calculateResult(Object result) {
        return result != null ? new BigDecimal(result.toString()) : BigDecimal.ZERO;
    }

}
