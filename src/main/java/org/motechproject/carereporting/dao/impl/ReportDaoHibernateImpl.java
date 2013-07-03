package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.ReportDao;
import org.motechproject.carereporting.domain.ReportEntity;
import org.springframework.stereotype.Component;

@Component
public class ReportDaoHibernateImpl extends GenericDaoHibernateImpl<ReportEntity> implements ReportDao {

}
