package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.enums.FieldType;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FormsServiceImpl extends AbstractService implements FormsService {

    @Resource(name = "careDataSource")
    private DataSource dataSource;

    @Autowired
    private FormDao formDao;

    @Value("${care.jdbc.schema}")
    private String careSchemaName;

    private static final String TABLE_LIST_SQL =
            "SELECT table_name FROM information_schema.tables WHERE table_name like '%_form' and table_schema = ?";

    private static final String COLUMNS_IN_TABLE_SQL =
            "SELECT column_name FROM information_schema.COLUMNS WHERE table_schema= ? AND TABLE_NAME = ?";

    private static final String COLUMN_INFO_IN_TABLE =
            "SELECT column_name, data_type FROM information_schema.COLUMNS WHERE table_schema = ? AND TABLE_NAME = ?";

    private static final String FOREIGN_KEY_FOR_TABLE =
            "SELECT ccu.table_name AS foreign_table_name FROM information_schema.table_constraints AS tc " +
                    "JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name " +
                    "WHERE tc.constraint_type = 'FOREIGN KEY' AND ccu.table_name like '%_case' AND tc.table_name = ?";

    @Override
    @Transactional(readOnly = false)
    public void addForm(FormEntity form) {
        formDao.save(form);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateForm(FormEntity form) {
        formDao.update(form);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormById(Integer id) {
        FormEntity form = new FormEntity();
        form.setId(id);
        formDao.remove(form);
    }

    @Override
    public FormEntity findFormById(Integer formId) {
        return formDao.findById(formId);
    }

    @Override
    public Set<FormEntity> findAllForms() {
        return formDao.findAll();
    }

    @Override
    @Transactional
    public Set<String> getTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new HashSet<String>(jdbcTemplate.queryForList(TABLE_LIST_SQL, String.class, careSchemaName));
    }

    @Override
    @Transactional
    public Set<String> getTableColumns(String tableName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Set<String> columnNames = new HashSet<String>(jdbcTemplate.queryForList(COLUMNS_IN_TABLE_SQL, String.class,
            careSchemaName, tableName));

        Iterator<String> iterator = columnNames.iterator();
        while (iterator.hasNext()) {
            String columnName = iterator.next();

            if (columnName.equals("id") || columnName.endsWith("_id")) {
                iterator.remove();
            }
        }

        return columnNames;
    }

    @Override
    @Transactional
    public Set<FieldEntity> getFieldsByFormEntity(FormEntity formEntity) {
        Set<FieldEntity> fieldEntities = new LinkedHashSet<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet columns = jdbcTemplate.queryForRowSet(COLUMN_INFO_IN_TABLE,
                careSchemaName, formEntity.getTableName());

        while (columns.next()) {
            String columnName = columns.getString("column_name");
            FieldType columnType = FieldType.getValueOf(columns.getString("data_type"));

            if (columnName.equals("id") || columnName.endsWith("_id")) {
                continue;
            }

            FieldEntity fieldEntity = new FieldEntity();
            fieldEntity.setName(columnName);
            fieldEntity.setType(columnType);
            fieldEntity.setForm(formEntity);

            fieldEntities.add(fieldEntity);
        }

        return fieldEntities;
    }

    @Override
    public String getForeignKeyForTable(String tableName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new String(jdbcTemplate.queryForObject(FOREIGN_KEY_FOR_TABLE, String.class, tableName));
    }

    @Override
    public Set<ComputedFieldEntity> findAllComputedFieldsByFormId(Integer formId) {
        return formDao.findById(formId).getComputedFields();
    }
}


