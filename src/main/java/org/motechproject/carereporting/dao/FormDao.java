package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.FormEntity;

public interface FormDao extends GenericDao<FormEntity> {
    FormEntity getByName(String name);
}
