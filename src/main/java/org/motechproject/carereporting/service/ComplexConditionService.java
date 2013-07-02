package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;

import java.util.List;

public interface ComplexConditionService {

    List<ComplexConditionEntity> getAllComplexConditions();

    ComplexConditionEntity getComplexConditionById(Integer complexConditionId);

    List<ComplexConditionEntity> getComplexConditionsByIndicatorId(Integer indicatorId);

    void createComplexCondition(ComplexConditionEntity complexCondition);

    List<OperatorTypeEntity> getAllOperatorTypes();

    OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId);

    void createOperatorType(OperatorTypeEntity operatorType);

    List<ComparisonSymbolEntity> getAllComparisonSymbols();

    ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId);

    void createComparisonSymbol(ComparisonSymbolEntity comparisonSymbol);

}
