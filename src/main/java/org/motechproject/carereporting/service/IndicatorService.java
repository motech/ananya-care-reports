package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;

import java.util.List;

public interface IndicatorService {

    List<IndicatorEntity> findAllIndicators();
    IndicatorEntity findIndicatorById(Integer id);

    void createNewIndicator(IndicatorEntity indicatorEntity);
    void updateIndicator(IndicatorEntity indicatorEntity);
    void deleteIndicator(IndicatorEntity indicatorEntity);

    List<IndicatorTypeEntity> findAllIndicatorTypes();
    IndicatorTypeEntity findIndicatorTypeById(Integer id);

    void createNewIndicatorType(IndicatorTypeEntity indicatorTypeEntity);
    void updateIndicatorType(IndicatorTypeEntity indicatorTypeEntity);
    void deleteIndicatorType(IndicatorTypeEntity indicatorTypeEntity);

    List<IndicatorCategoryEntity> findAllIndicatorCategories();
    IndicatorCategoryEntity findIndicatorCategoryById(Integer id);

    void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);
    void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);
    void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);
}
