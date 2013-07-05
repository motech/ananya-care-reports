package org.motechproject.carereporting.service;

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

   String getForeignKeyForTable(String tableName);
}

