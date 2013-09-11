package org.motechproject.carereporting.initializers;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.dto.FieldDto;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComputedFieldEntityInitializer {

    @Autowired
    private FormsService formsService;

    @Autowired
    private ComputedFieldService computedFieldService;

    @Transactional(readOnly = false)
    public void loadFormsFromDB() {
        Set<String> formTables = formsService.getTables();
        Set<FormEntity> formEntities = formsService.getAllTables();
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
        }

        for (FormEntity formEntity: newFormEntities.values()) {
            formsService.addForm(formEntity);
            createFields(formEntity);
        }
    }

    @Transactional(readOnly = false)
    private void createFields(FormEntity formEntity) {
        setupSecurityContext();
        Set<FieldDto> fields = formsService.getFieldsByFormEntity(formEntity);
        for (FieldDto field : fields) {
            computedFieldService.createNewComputedField(new ComputedFieldEntity(
                    field.getName(),
                    field.getType(),
                    formEntity,
                    new LinkedHashSet<FieldOperationEntity>(),
                    true));
        }
        SecurityContextHolder.clearContext();
    }

    private void setupSecurityContext() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_COMPUTED_FIELDS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }
}
