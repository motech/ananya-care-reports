package org.motechproject.carereporting.service.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.dto.ComputedFieldDto;
import org.motechproject.carereporting.domain.types.FieldType;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ComputedFieldServiceImpl implements ComputedFieldService {

    @Autowired
    private ComputedFieldDao computedFieldDao;

    @Autowired
    private FormsService formsService;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public Set<ComputedFieldEntity> getAllComputedFields() {
        return computedFieldDao.getAll();
    }

    @Transactional
    @Override
    public Set<ComputedFieldEntity> getComputedFieldsByFormId(Integer formId) {
        List<Criterion> criterions = new LinkedList<>();
        criterions.add(Restrictions.eq("form.id", formId));
        criterions.add(Restrictions.eq("type", FieldType.Number));
        criterions.add(Restrictions.not(Restrictions.like("name", "id", MatchMode.ANYWHERE)));

        return computedFieldDao.getAllByFields(criterions);
    }

    @Transactional
    @Override
    public ComputedFieldEntity getComputedFieldById(Integer computedFieldId) {
        return computedFieldDao.getByIdWithFields(computedFieldId, "fieldOperations");
    }

    @Override
    public Set<ComputedFieldEntity> getAllComputedFields(boolean origin) {
        return computedFieldDao.getAllByField("origin", origin);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewComputedField(ComputedFieldEntity computedFieldEntity) {
        computedFieldDao.save(computedFieldEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewComputedFieldFromDto(ComputedFieldDto computedFieldDto) {
        ComputedFieldEntity computedField = new ComputedFieldEntity(
                computedFieldDto.getName(),
                computedFieldDto.getType(),
                findFormEntityFromDto(computedFieldDto),
                new LinkedHashSet<>(computedFieldDto.getFieldOperations()),
                false);
        computedFieldDao.save(computedField);
    }

    private FormEntity findFormEntityFromDto(ComputedFieldDto computedFieldDto) {
        return formsService.getFormById(computedFieldDto.getForm());
    }

    @Transactional(readOnly = false)
    @Override
    public void updateComputedFieldFromDto(Integer id, ComputedFieldDto computedFieldDto) {
        ComputedFieldEntity computedFieldEntity = getComputedFieldById(id);
        computedFieldEntity.setName(computedFieldDto.getName());
        computedFieldEntity.setType(computedFieldDto.getType());
        computedFieldEntity.setForm(findFormEntityFromDto(computedFieldDto));
        computedFieldEntity.getFieldOperations().clear();
        computedFieldDao.update(computedFieldEntity);
        sessionFactory.getCurrentSession().flush();
        computedFieldEntity.getFieldOperations().addAll(computedFieldDto.getFieldOperations());
        computedFieldDao.update(computedFieldEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteComputedField(Integer id) {
        ComputedFieldEntity computedFieldEntity = computedFieldDao.getById(id);
        computedFieldDao.remove(computedFieldEntity);
    }

}
