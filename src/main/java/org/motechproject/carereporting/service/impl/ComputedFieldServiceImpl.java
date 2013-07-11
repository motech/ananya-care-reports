package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ComputedFieldServiceImpl extends AbstractService implements ComputedFieldService {

    @Autowired
    private ComputedFieldDao computedFieldDao;

    @Transactional
    @Override
    public Set<ComputedFieldEntity> findAllComputedFields() {
        return computedFieldDao.findAll();
    }

    @Transactional
    @Override
    public ComputedFieldEntity findComputedFieldById(Integer computedFieldId) {
        return computedFieldDao.findById(computedFieldId);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewComputedField(ComputedFieldEntity computedFieldEntity) {
        computedFieldDao.save(computedFieldEntity);
    }
}
