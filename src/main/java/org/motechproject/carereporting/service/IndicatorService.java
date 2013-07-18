package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.forms.IndicatorFormObject;
import org.motechproject.carereporting.domain.forms.TrendIndicatorCategory;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IndicatorService {

    String HAS_ROLE_CAN_REMOVE_INDICATORS = "hasRole('CAN_REMOVE_INDICATORS')";
    String HAS_ROLE_CAN_EDIT_INDICATORS = "hasRole('CAN_EDIT_INDICATORS')";
    String HAS_ROLE_CAN_CREATE_CATEGORIES = "hasRole('CAN_CREATE_CATEGORIES')";
    String HAS_ROLE_CAN_EDIT_CATEGORIES = "hasRole('CAN_EDIT_CATEGORIES')";
    String HAS_ROLE_CAN_REMOVE_CATEGORIES = "hasRole('CAN_REMOVE_CATEGORIES')";

    Set<IndicatorEntity> findAllIndicators();

    Set<IndicatorEntity> getIndicatorsByCategoryId(Integer categoryId);

    Set<IndicatorEntity> findAllIndicatorsUnderUserArea(Integer userId);

    IndicatorEntity findIndicatorById(Integer id);

    void createNewIndicator(IndicatorEntity indicatorEntity);

    void createNewIndicatorFromFormObject(IndicatorFormObject indicatorFormObject);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_INDICATORS)
    void updateIndicator(IndicatorEntity indicatorEntity);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_INDICATORS)
    void updateIndicatorFromFormObject(IndicatorFormObject indicatorFormObject);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_INDICATORS)
    void deleteIndicator(IndicatorEntity indicatorEntity);

    Set<IndicatorTypeEntity> findAllIndicatorTypes();

    IndicatorTypeEntity findIndicatorTypeById(Integer id);

    void createNewIndicatorType(IndicatorTypeEntity indicatorTypeEntity);

    void updateIndicatorType(IndicatorTypeEntity indicatorTypeEntity);

    void deleteIndicatorType(IndicatorTypeEntity indicatorTypeEntity);

    Set<IndicatorCategoryEntity> findAllIndicatorCategories();

    IndicatorCategoryEntity findIndicatorCategoryById(Integer id);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_CATEGORIES)
    void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_CATEGORIES)
    void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_CATEGORIES)
    void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    Set<IndicatorValueEntity> findAllIndicatorValues();

    IndicatorValueEntity findIndicatorValueById(Integer id);

    void createNewIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    void updateIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    List<IndicatorValueEntity> findIndicatorValuesForArea(Integer indicatorId, Integer areaId, Date earliestDate);

    Set<TrendIndicatorCategory> getIndicatorsWithTrendsUnderUser(UserEntity user, Date startDate, Date endDate);
}
