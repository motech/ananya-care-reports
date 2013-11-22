package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.FormEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class FormServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final int EXPETED_NUMBER_OF_TABLES = 34;
    private static final int EXPETED_NUMBER_OF_FORMS = 30;
    private static final int EXPECTED_NUMBER_OF_COLUMNS = 11;

    @Autowired
    private FormsService formsService;

    @Test
    public void testGetTables() {
        String[] tab = new String[]{
                "cf_child_form",
                "bp_form",
                "abort_form",
                "ui_mother_form",
                "ui_child_form",
                "refer_mother_form",
                "refer_child_form",
                "new_form",
                "registration_mother_form",
                "registration_child_form",
                "pnc_mother_form",
                "pnc_child_form",
                "mi_form",
                "mo_form",
                "cf_mother_form",
                "ebf_mother_form",
                "ebf_child_form",
                "delivery_mother_form",
                "delivery_child_form",
                "death_mother_form",
                "death_child_form",
                "close_mother_form",
                "close_child_form",
                "mother_case",
                "child_case"
        };
        Set<String> tables = formsService.getTables();

        assertEquals(EXPETED_NUMBER_OF_TABLES, tables.size());
        assertThat(tables, hasItems(tab));
    }

    @Test
    public void testGetTableColumns() {
        String tableName = "abort_form";
        String[] tab = new String[] {
                "date_aborted",
                "birth_status",
                "close",
                "abortion_type",
                "date_modified",
                "time_start",
                "time_end"
        };

        Set<String> columns = formsService.getTableColumns(tableName);

        assertEquals(EXPECTED_NUMBER_OF_COLUMNS, columns.size());
        assertThat(columns, hasItems(tab));
    }

    @Test
    public void testGetForeignKeyForTable() {
        String tableName = "abort_form";
        String foreignKey = "mother_case";

        String result = formsService.getForeignKeyForTable(tableName);

        assertEquals(foreignKey, result);
    }

    @Test
    public void testGetAllTables() {
        Set<FormEntity> formEntities = formsService.getAllTables();

        assertEquals(EXPETED_NUMBER_OF_TABLES, formEntities.size());
    }

    @Test
    public void testGetAllForms() {
        Set<FormEntity> formEntities = formsService.getAllForms();

        assertEquals(EXPETED_NUMBER_OF_FORMS, formEntities.size());
    }

}
