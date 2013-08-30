package org.motechproject.carereporting.service.impl;

import org.hibernate.SessionFactory;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
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
        Set<ComputedFieldEntity> computedFieldEntities = computedFieldDao.getAll();

        Iterator<ComputedFieldEntity> iterator = computedFieldEntities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getOrigin()) {
                iterator.remove();
            }
        }

        return computedFieldEntities;
    }

    @Transactional
    @Override
    public Set<ComputedFieldEntity> getComputedFieldsByFormId(Integer formId) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("form.id", formId);
        fields.put("type", FieldType.Number);

        Set<ComputedFieldEntity> computedFieldEntities = computedFieldDao.getAllByFields(fields);

        Iterator<ComputedFieldEntity> iterator = computedFieldEntities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().indexOf("id") != -1) {
                iterator.remove();
            }
        }
        return computedFieldEntities;
    }

    @Transactional
    @Override
    public ComputedFieldEntity getComputedFieldById(Integer computedFieldId) {
        return computedFieldDao.getByIdWithFields(computedFieldId, "fieldOperations");
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
                new LinkedHashSet<>(computedFieldDto.getFieldOperations()));
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

}
