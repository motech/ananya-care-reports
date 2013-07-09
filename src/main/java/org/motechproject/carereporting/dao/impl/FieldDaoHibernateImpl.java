package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.FieldDao;
import org.motechproject.carereporting.domain.FieldEntity;
import org.springframework.stereotype.Component;

@Component
public class FieldDaoHibernateImpl extends GenericDaoHibernateImpl<FieldEntity> implements FieldDao {
}
