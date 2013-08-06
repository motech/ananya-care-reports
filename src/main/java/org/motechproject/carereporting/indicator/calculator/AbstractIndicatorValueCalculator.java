package org.motechproject.carereporting.indicator.calculator;

import org.apache.commons.lang.time.DateUtils;
import org.jooq.SelectQuery;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.IndicatorService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public void calculateAndPersistIndicatorValues(FrequencyEntity frequency) {
        frequencyEntity = frequency;

        for (AreaEntity area: getAllAreasForIndicator(indicator)) {
            Set<IndicatorValueEntity> values = getValuesFromDatabase(area);
            BigDecimal nominator = getNominator(area, values);
            BigDecimal denominator = getDenominator(area, values);

            BigDecimal value = denominator.compareTo(BigDecimal.ZERO) != 0 ? nominator.divide(denominator, 1, RoundingMode.HALF_UP) : null;

            if (value != null) {
                if (this instanceof  PercentageIndicatorValueCalculator) {
                    value = value.multiply(new BigDecimal(100));
                }
                IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(indicator, area, nominator, denominator, value, frequencyEntity);
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

    private Set<IndicatorValueEntity> getValuesFromDatabase(AreaEntity area) {
        Set<IndicatorValueEntity> values = null;

        if (!"defined".equals(frequencyEntity.getFrequencyName()) && !"daily".equals(frequencyEntity.getFrequencyName())) {
            Date date = resolveDate();
            FrequencyEntity child = resolveChild();
            values = indicatorService.getIndicatorValues(indicator, area, child, date);
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

    private Date resolveDate() {
        Date date = new Date();

        switch(frequencyEntity.getFrequencyName()) {
            case "weekly":
                date = DateUtils.addWeeks(date, -1);
                date = DateUtils.addSeconds(date, -10);
                break;
            case "monthly":
                date = DateUtils.addMonths(date, -1);
                date = DateUtils.addSeconds(date, -15);
                break;
            case "quarterly":
                date = DateUtils.addMonths(date, -3);
                date = DateUtils.addSeconds(date, -20);
                break;
            case "yearly":
                date = DateUtils.addYears(date, -1);
                date = DateUtils.addSeconds(date, -25);
                break;
            default:
                break;
        }

        return date;
    }

    protected BigDecimal getNominator(AreaEntity areaEntity, Set<IndicatorValueEntity> values) {
        BigDecimal result = BigDecimal.ZERO;

        if ("defined".equals(frequencyEntity.getFrequencyName()) || "daily".equals(frequencyEntity.getFrequencyName())) {
            SelectQuery query = calculatorQueryBuilder
                    .withIndicator(indicator)
                    .withComplexCondition(indicator.getComplexCondition())
                    .withArea(areaEntity)
                    .withFrequency(indicator.getFrequency())
                    .withOperation(nominatorOperationType)
                    .build();

            Object resultObj = query.fetch().getValue(0, 0);
            String resultObjStr = (resultObj != null) ? resultObj.toString() : "0.0";
            result = new BigDecimal(resultObjStr);
        } else {
            for (IndicatorValueEntity indicatorValueEntity: values) {
                result = result.add(indicatorValueEntity.getNominator());
            }
        }

        return result;
    }

    protected BigDecimal getDenominator(AreaEntity areaEntity, Set<IndicatorValueEntity> values) {
        BigDecimal result = BigDecimal.ZERO;

        if ("defined".equals(frequencyEntity.getFrequencyName()) || "daily".equals(frequencyEntity.getFrequencyName())) {
            SelectQuery query = calculatorQueryBuilder
                    .withIndicator(indicator)
                    .withArea(areaEntity)
                    .withFrequency(indicator.getFrequency())
                    .withOperation(nominatorOperationType)
                    .build();

            Object resultObj = query.fetch().getValue(0, 0);
            String resultObjStr = (resultObj != null) ? resultObj.toString() : "0.0";
            result = new BigDecimal(resultObjStr);
        } else {
            for (IndicatorValueEntity indicatorValueEntity: values) {
                result = result.add(indicatorValueEntity.getDenominator());
            }
        }

        return result;
    }

}
