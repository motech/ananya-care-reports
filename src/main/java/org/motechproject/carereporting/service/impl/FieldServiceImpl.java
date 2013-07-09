package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.FieldDao;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.enums.FieldType;
import org.motechproject.carereporting.service.FieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FieldServiceImpl extends AbstractService implements FieldService {

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private FormsService formsService;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public Set<FieldEntity> findAllFieldsByFormId(Integer formId) {
        FormEntity formEntity = formsService.findFormById(formId);

        return formEntity.getFields();
    }

    @Transactional
    @Override
    public Set<FieldEntity> findAllFieldsByType(FieldType fieldType) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from FieldEntity where type = :fieldType");
        query.setParameter("fieldType", fieldType);

        return new LinkedHashSet<FieldEntity>(query.list());
    }

    @Transactional
    @Override
    public FieldEntity findFieldById(Integer fieldId) {
        return fieldDao.findById(fieldId);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewField(FieldEntity fieldEntity) {
        fieldDao.save(fieldEntity);
    }
}
