package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.domain.FormEntity;
import org.springframework.stereotype.Component;

@Component
public class FormDaoHibernateImpl extends GenericDaoHibernateImpl<FormEntity> implements FormDao {

}
