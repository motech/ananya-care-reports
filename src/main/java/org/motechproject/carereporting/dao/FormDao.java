package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.FormEntity;

import java.util.Set;

public interface FormDao extends GenericDao<FormEntity> {

    Set<FormEntity> getByTableName(String name);

    Set<FormEntity> getOtherTables();

}
