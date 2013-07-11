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
import java.util.LinkedHashSet;
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

        for (FormEntity formEntity : formEntities) {
            if (formTables.contains(formEntity.getTableName())) {
                formTables.remove(formEntity.getTableName());
            }
        }

        for (String formTable : formTables) {
            FormEntity formEntity = new FormEntity();
            formEntity.setTableName(formTable);
            formEntity.setDisplayName(formTable);

            formsService.addForm(formEntity);
            createFields(formEntity);
        }
    }

    @Transactional(readOnly = false)
    private void createFields(FormEntity formEntity) {
        Set<FieldEntity> fieldEntities = formsService.getFieldsByFormEntity(formEntity);
        Set<FieldEntity> existingEntities = fieldService.findAllFieldsByFormId(formEntity.getId());

        for (FieldEntity fieldEntity : fieldEntities) {
            if (existingEntities.contains(fieldEntity)) {
                continue;
            }

            fieldService.createNewField(fieldEntity);

            Set<FieldOperationEntity> fieldOperationEntities = new LinkedHashSet<>();
            fieldOperationEntities.add(new FieldOperationEntity(fieldEntity));

            ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
            computedFieldEntity.setName(fieldEntity.getName());
            computedFieldEntity.setType(fieldEntity.getType());
            computedFieldEntity.setForm(formEntity);
            computedFieldEntity.setFieldOperations(fieldOperationEntities);

            computedFieldService.createNewComputedField(computedFieldEntity);
        }
    }
}
