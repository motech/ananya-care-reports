package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.util.Set;

public interface IndicatorService {

    Set<IndicatorEntity> findAllIndicators();
    IndicatorEntity findIndicatorById(Integer id);

    void createNewIndicator(IndicatorEntity indicatorEntity);
    void updateIndicator(IndicatorEntity indicatorEntity);
    void deleteIndicator(IndicatorEntity indicatorEntity);

    Set<IndicatorTypeEntity> findAllIndicatorTypes();
    IndicatorTypeEntity findIndicatorTypeById(Integer id);

    void createNewIndicatorType(IndicatorTypeEntity indicatorTypeEntity);
    void updateIndicatorType(IndicatorTypeEntity indicatorTypeEntity);
    void deleteIndicatorType(IndicatorTypeEntity indicatorTypeEntity);

    Set<IndicatorCategoryEntity> findAllIndicatorCategories();
    IndicatorCategoryEntity findIndicatorCategoryById(Integer id);

    void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);
    void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);
    void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    Set<IndicatorValueEntity> findAllIndicatorValues();
    IndicatorValueEntity findIndicatorValueById(Integer id);

    void createNewIndicatorValue(IndicatorValueEntity indicatorValueEntity);
    void updateIndicatorValue(IndicatorValueEntity indicatorValueEntity);
    void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity);
}
