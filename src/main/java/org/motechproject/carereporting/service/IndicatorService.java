package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.dto.IndicatorCreationFormDto;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.domain.dto.QueryCreationFormDto;
import org.motechproject.carereporting.domain.dto.TrendIndicatorCategoryDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IndicatorService {

    String HAS_ROLE_CAN_CREATE_INDICATORS = "hasRole('CAN_CREATE_INDICATORS')";
    String HAS_ROLE_CAN_REMOVE_INDICATORS = "hasRole('CAN_REMOVE_INDICATORS')";
    String HAS_ROLE_CAN_EDIT_INDICATORS = "hasRole('CAN_EDIT_INDICATORS')";
    String HAS_ROLE_CAN_CREATE_CATEGORIES = "hasRole('CAN_CREATE_CATEGORIES')";
    String HAS_ROLE_CAN_EDIT_CATEGORIES = "hasRole('CAN_EDIT_CATEGORIES')";
    String HAS_ROLE_CAN_REMOVE_CATEGORIES = "hasRole('CAN_REMOVE_CATEGORIES')";

    Set<IndicatorEntity> getAllIndicators();

    Set<IndicatorEntity> getIndicatorsByCategoryId(Integer categoryId);

    IndicatorEntity getIndicatorById(Integer id);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_INDICATORS)
    void createNewIndicator(IndicatorEntity indicatorEntity);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_INDICATORS)
    void createNewIndicatorFromDto(IndicatorDto indicatorDto);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_INDICATORS)
    void updateIndicator(IndicatorEntity indicatorEntity);

    void setComputedForIndicator(IndicatorEntity indicatorEntity, Boolean value);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_INDICATORS)
    void updateIndicatorFromDto(IndicatorDto indicatorDto);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_INDICATORS)
    void deleteIndicator(IndicatorEntity indicatorEntity);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_INDICATORS)
    void deleteAllIndicators();

    Set<IndicatorTypeEntity> getAllIndicatorTypes();

    IndicatorTypeEntity getIndicatorTypeById(Integer id);

    Set<IndicatorCategoryEntity> getAllIndicatorCategories();

    IndicatorCategoryEntity getIndicatorCategoryById(Integer id);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_CATEGORIES)
    void createNewIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_CATEGORIES)
    void updateIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    @PreAuthorize(HAS_ROLE_CAN_REMOVE_CATEGORIES)
    void deleteIndicatorCategory(IndicatorCategoryEntity indicatorCategoryEntity);

    Set<IndicatorValueEntity> getAllIndicatorValues();

    IndicatorValueEntity getIndicatorValueById(Integer id);

    void createNewIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    void updateIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    void deleteIndicatorValue(IndicatorValueEntity indicatorValueEntity);

    List<IndicatorValueEntity> getIndicatorValuesForArea(Integer indicatorId, Integer areaId, Integer frequencyId,
                                                         Date startDate, Date endDate, String category);

    Set<TrendIndicatorCategoryDto> getIndicatorsWithTrendsUnderUser(UserEntity user, Date startDate, Date endDate, Integer areaId, Integer frequencyId);

    Map<AreaEntity, Integer> getIndicatorTrendForChildAreas(Integer indicatorId, Integer parentAreaId, Integer frequencyId, Date startDate, Date endDate);

    byte[] getCaseListReportAsCsv(IndicatorEntity indicatorEntity, Integer areaId, Date fromDate, Date toDate);

    void calculateIndicator(IndicatorEntity indicatorEntity);

    void calculateAllIndicators(Integer categoryId);

    IndicatorCreationFormDto getIndicatorCreationFormDto();

    QueryCreationFormDto getIndicatorQueryCreationFormDto();

    Set<IndicatorEntity> getAllIndicatorsByUserAccess(UserEntity userEntity);

    List<IndicatorValueEntity> getIndicatorValuesForCsv(Integer indicatorId, Integer areaId, Integer frequencyId, Date startDate, Date endDate);
}
