package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.FormEntity;

import java.util.List;

public interface FormsService {

   void addForm(FormEntity form);
   void updateForm(FormEntity form);
   void deleteFormById(Integer id);
   FormEntity findFormById(Integer formId);
   List<FormEntity> getAllForms();
   List<String> getTables();
   List<String> getTableColumns(String tableName);

}

