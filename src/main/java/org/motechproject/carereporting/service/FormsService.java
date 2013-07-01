package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;

import java.util.List;
import java.util.Set;

public interface FormsService {

   List<String> getTables();
   List<String> getTableColumns(String tableName);

}

