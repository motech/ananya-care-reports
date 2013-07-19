package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.dto.ComplexConditionFormObject;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface ComplexConditionService {

    String HAS_ROLE_CAN_CREATE_COMPLEX_CONDITIONS = "hasRole('CAN_CREATE_COMPLEX_CONDITIONS')";

    Set<ComplexConditionEntity> getAllComplexConditions();

    ComplexConditionEntity getComplexConditionById(Integer complexConditionId);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPLEX_CONDITIONS)
    void createNewComplexCondition(ComplexConditionEntity complexCondition);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPLEX_CONDITIONS)
    void createNewComplexCondition(ComplexConditionFormObject complexConditionFormObject);

    void updateComplexCondition(ComplexConditionEntity complexConditionEntity);

    void updateComplexCondition(ComplexConditionFormObject complexConditionFormObject);

    void deleteComplexCondition(ComplexConditionEntity complexConditionEntity);

    Set<OperatorTypeEntity> getAllOperatorTypes();

    OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId);

    Set<ComparisonSymbolEntity> getAllComparisonSymbols();

    ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId);

}
