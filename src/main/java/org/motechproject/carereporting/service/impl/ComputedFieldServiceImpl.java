package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.forms.ComputedFieldFormObject;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ComputedFieldServiceImpl extends AbstractService implements ComputedFieldService {

    @Autowired
    private ComputedFieldDao computedFieldDao;

    @Autowired
    private FormsService formsService;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public Set<ComputedFieldEntity> findAllComputedFields() {
        return computedFieldDao.findAll();
    }

    @Transactional
    @Override
    public Set<ComputedFieldEntity> findComputedFieldsByFormId(Integer formId) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from ComputedFieldEntity where form.id = :formId");
        query.setParameter("formId", formId);

        return new LinkedHashSet<ComputedFieldEntity>(query.list());
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

    @Transactional(readOnly = false)
    @Override
    public void createNewComputedFieldFromFormObject(ComputedFieldFormObject computedFieldFormObject) {
        computedFieldDao.save(new ComputedFieldEntity(
                computedFieldFormObject.getName(),
                computedFieldFormObject.getType(),
                findFormEntityFromFormObject(computedFieldFormObject),
                new LinkedHashSet<>(computedFieldFormObject.getFieldOperations())
        ));
    }

    private FormEntity findFormEntityFromFormObject(ComputedFieldFormObject computedFieldFormObject) {
        return formsService.findFormById(computedFieldFormObject.getForm());
    }
}
