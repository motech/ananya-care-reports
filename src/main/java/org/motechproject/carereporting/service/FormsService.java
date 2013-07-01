package org.motechproject.carereporting.service;

import java.util.List;

public interface FormsService {

   List<String> getTables();
   List<String> getTableColumns(String tableName);

}

