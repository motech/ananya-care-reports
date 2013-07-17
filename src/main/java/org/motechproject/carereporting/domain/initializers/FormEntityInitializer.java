package org.motechproject.carereporting.domain.initializers;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FormEntityInitializer {

    @Autowired
    private FormsService formsService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ComputedFieldService computedFieldService;

    @PostConstruct
    @Transactional(readOnly = false)
    public void loadFormsFromDB() {
        Set<String> formTables = formsService.getTables();
        Set<FormEntity> formEntities = formsService.findAllForms();
        Map<String, FormEntity> newFormEntities = new HashMap<>();

        for (String formTable : formTables) {
            FormEntity formEntity = new FormEntity();
            formEntity.setTableName(formTable);
            formEntity.setDisplayName(formTable);
            newFormEntities.put(formTable, formEntity);
        }

        for (FormEntity formEntity : formEntities) {
            if (formTables.contains(formEntity.getTableName())) {
                newFormEntities.remove(formEntity.getTableName());
            }

            createFields(formEntity);
        }

        Iterator<FormEntity> mapIterator = newFormEntities.values().iterator();
        while (mapIterator.hasNext()) {
            FormEntity formEntity = mapIterator.next();

            if (formEntity == null) {
                continue;
            }

            formsService.addForm(formEntity);
            createFields(formEntity);
        }
    }

    @Transactional(readOnly = false)
    private void createFields(FormEntity formEntity) {
        Set<FieldEntity> fieldEntities = formsService.getFieldsByFormEntity(formEntity);
        List<String> existingEntities = fieldService.findAllFieldNamesByFormId(formEntity.getId());

        for (FieldEntity fieldEntity : fieldEntities) {
            if (existingEntities.contains(fieldEntity.getName())) {
                continue;
            }

            fieldService.createNewField(fieldEntity);

            Set<FieldOperationEntity> fieldOperationEntities = new LinkedHashSet<>();
            fieldOperationEntities.add(new FieldOperationEntity(fieldEntity));

            ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity(
                    fieldEntity.getName(),
                    fieldEntity.getType(),
                    fieldEntity.getForm(),
                    fieldOperationEntities
            );

            computedFieldService.createNewComputedField(computedFieldEntity);
        }
    }
}
