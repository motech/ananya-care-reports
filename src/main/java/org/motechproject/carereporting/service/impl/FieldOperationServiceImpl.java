package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.FieldOperationDao;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.motechproject.carereporting.service.FieldOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FieldOperationServiceImpl extends AbstractService implements FieldOperationService {

    @Autowired
    private FieldOperationDao fieldOperationDao;

    @Transactional
    @Override
    public Set<FieldOperationEntity> findAllFieldOperationServices() {
        return fieldOperationDao.findAll();
    }

    @Transactional
    @Override
    public FieldOperationEntity findFieldOperationEntityById(Integer fieldOperationEntityId) {
        return fieldOperationDao.findById(fieldOperationEntityId);
    }
}
