package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FormEntity;

import java.util.Set;

public interface FormsService {

    void addForm(FormEntity form);

    void updateForm(FormEntity form);

    void deleteFormById(Integer id);

    FormEntity findFormById(Integer formId);

    FormEntity findFormByIdWithFields(Integer formId, String... fieldNames);

    Set<FormEntity> findAllForms();

    Set<FormEntity> findAllFormsWithFields(String... fieldNames);

    Set<String> getTables();

    Set<String> getTableColumns(String tableName);

    Set<FieldEntity> getFieldsByFormEntity(FormEntity formEntity);

    String getForeignKeyForTable(String tableName);

    Set<ComputedFieldEntity> findAllComputedFieldsByFormId(Integer formId);
}

