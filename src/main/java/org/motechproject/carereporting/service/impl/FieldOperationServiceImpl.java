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
public class FieldOperationServiceImpl implements FieldOperationService {

    @Autowired
    private FieldOperationDao fieldOperationDao;

    @Override
    public Set<FieldOperationEntity> getAllFieldOperations() {
        return fieldOperationDao.getAll();
    }

    @Override
    public FieldOperationEntity getFieldOperationEntityById(Integer fieldOperationEntityId) {
        return fieldOperationDao.getById(fieldOperationEntityId);
    }
}
