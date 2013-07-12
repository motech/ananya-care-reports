package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FormEntity;

import java.util.Set;

public interface FormsService {

    void addForm(FormEntity form);

    void updateForm(FormEntity form);

    void deleteFormById(Integer id);

    FormEntity findFormById(Integer formId);

    Set<FormEntity> findAllForms();

    Set<String> getTables();

    Set<String> getTableColumns(String tableName);

    Set<FieldEntity> getFieldsByFormEntity(FormEntity formEntity);

    String getForeignKeyForTable(String tableName);


}

