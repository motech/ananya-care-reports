package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;

import java.util.Set;

public interface ComplexConditionService {

    Set<ComplexConditionEntity> getAllComplexConditions();

    ComplexConditionEntity getComplexConditionById(Integer complexConditionId);

    Set<ComplexConditionEntity> getComplexConditionsByIndicatorId(Integer indicatorId);

    void createComplexCondition(ComplexConditionEntity complexCondition);

    Set<OperatorTypeEntity> getAllOperatorTypes();

    OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId);

    void createOperatorType(OperatorTypeEntity operatorType);

    Set<ComparisonSymbolEntity> getAllComparisonSymbols();

    ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId);

    void createComparisonSymbol(ComparisonSymbolEntity comparisonSymbol);

}
