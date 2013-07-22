package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Autowired
    private FormsService formsService;

    @Test
    public void testGetTables() {
        int tablesNumber = 23;
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
                "close_child_form"
        };
        Set<String> tables = formsService.getTables();

        assertEquals(tablesNumber, tables.size());
        assertThat(tables, hasItems(tab));
    }

    @Test
    public void testGetTableColumns() {
        String tableName = "abort_form";
        int columnsNumber = 9;
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

        assertEquals(columnsNumber, columns.size());
        assertThat(columns, hasItems(tab));
    }

    @Test
    public void testGetForeignKeyForTable() {
        String tableName = "abort_form";
        String foreignKey = "mother_case";

        String result = formsService.getForeignKeyForTable(tableName);

        assertEquals(foreignKey, result);
    }
}
