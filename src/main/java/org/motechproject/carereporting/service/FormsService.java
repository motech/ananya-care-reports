package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.dto.FieldDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface FormsService {

    String HAS_ROLE_CAN_EDIT_FORM_NAMES = "hasRole('CAN_EDIT_FORM_NAMES')";
    String HAS_ROLE_CAN_DELETE_FORMS = "hasRole('CAN_DELETE_FORMS')";

    void addForm(FormEntity form);

    @PreAuthorize(HAS_ROLE_CAN_EDIT_FORM_NAMES)
    void updateForm(FormEntity form);

    @PreAuthorize(HAS_ROLE_CAN_DELETE_FORMS)
    void deleteFormById(Integer id);

    FormEntity getFormById(Integer formId);

    FormEntity getFormByIdWithFields(Integer formId, String... fieldNames);

    Set<FormEntity> getAllForms();

    Set<FormEntity> getAllFormsWithFields(String... fieldNames);

    Set<String> getTables();

    Set<String> getTableColumns(String tableName);

    Set<FieldDto> getFieldsByFormEntity(FormEntity formEntity);

    String getForeignKeyForTable(String tableName);

    Set<ComputedFieldEntity> getAllComputedFieldsByFormId(Integer formId);
}

