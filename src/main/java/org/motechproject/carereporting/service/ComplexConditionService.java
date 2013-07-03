package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.forms.ComplexConditionFormObject;

import java.util.Set;

public interface ComplexConditionService {

    Set<ComplexConditionEntity> findAllComplexConditions();

    ComplexConditionEntity findComplexConditionById(Integer complexConditionId);

    Set<ComplexConditionEntity> findComplexConditionsByIndicatorId(Integer indicatorId);

    void createNewComplexCondition(ComplexConditionEntity complexCondition);

    void createNewComplexCondition(ComplexConditionFormObject complexConditionFormObject);

    void updateComplexCondition(ComplexConditionEntity complexConditionEntity);

    void updateComplexCondition(ComplexConditionFormObject complexConditionFormObject);

    void deleteComplexCondition(ComplexConditionEntity complexConditionEntity);

    Set<OperatorTypeEntity> findAllOperatorTypes();

    OperatorTypeEntity findOperatorTypeById(Integer operatorTypeId);

    void createNewOperatorType(OperatorTypeEntity operatorType);

    void updateOperatorType(OperatorTypeEntity operatorTypeEntity);

    void deleteOperatorType(OperatorTypeEntity operatorTypeEntity);

    Set<ComparisonSymbolEntity> findAllComparisonSymbols();

    ComparisonSymbolEntity findComparisonSymbolById(Integer comparisonSymbolId);

    void createNewComparisonSymbol(ComparisonSymbolEntity comparisonSymbol);

    void updateComparisonSymbol(ComparisonSymbolEntity comparisonSymbolEntity);

    void deleteComparisonSymbol(ComparisonSymbolEntity comparisonSymbolEntity);

}
