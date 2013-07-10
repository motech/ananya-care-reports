package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.springframework.stereotype.Component;

@Component
public class ComputedFieldDaoHibernateImpl extends GenericDaoHibernateImpl<ComputedFieldEntity> implements ComputedFieldDao {
}
