package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.springframework.stereotype.Component;

@Component
public class ComparisonSymbolDaoHibernateImpl
        extends GenericDaoHibernateImpl<ComparisonSymbolEntity> implements ComparisonSymbolDao {
}
