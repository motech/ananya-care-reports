package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.dto.ComputedFieldDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface ComputedFieldService {

    String HAS_ROLE_CAN_CREATE_COMPUTED_FIELDS = "hasRole('CAN_CREATE_COMPUTED_FIELDS')";

    Set<ComputedFieldEntity> getAllComputedFields();

    Set<ComputedFieldEntity> getComputedFieldsByFormId(Integer formId);

    Set<ComputedFieldEntity> getAllComputedFieldsByFormId(Integer formId);

    ComputedFieldEntity getComputedFieldById(Integer computedFieldId);

    Set<ComputedFieldEntity> getAllComputedFields(boolean origin);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPUTED_FIELDS)
    void createNewComputedField(ComputedFieldEntity computedFieldEntity);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPUTED_FIELDS)
    void createNewComputedFieldFromDto(ComputedFieldDto computedFieldDto);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPUTED_FIELDS)
    void updateComputedFieldFromDto(Integer id, ComputedFieldDto computedFieldDto);

    @PreAuthorize(HAS_ROLE_CAN_CREATE_COMPUTED_FIELDS)
    void deleteComputedField(Integer id);

    Set<OperatorTypeEntity> getAllOperatorTypes();

    OperatorTypeEntity getOperatorTypeById(Integer operatorTypeId);

    Set<ComparisonSymbolEntity> getAllComparisonSymbols();

    ComparisonSymbolEntity getComparisonSymbolById(Integer comparisonSymbolId);

    ComparisonSymbolEntity getComparisonSymbolByName(String name);

}
