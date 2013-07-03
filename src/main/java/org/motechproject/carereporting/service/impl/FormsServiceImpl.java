package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FormsServiceImpl extends AbstractService implements FormsService {

    @Resource(name = "careDataSource")
    private DataSource dataSource;

    @Autowired
    private FormDao formDao;

    private static final String TABLE_LIST_SQL =
            "SELECT table_name FROM information_schema.tables WHERE table_name like '%_form' and table_schema ='report'";

    private static final String COLUMNS_IN_TABLE_SQL =
            "SELECT column_name FROM information_schema.COLUMNS WHERE table_schema='report' AND TABLE_NAME = ?";

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
        FormEntity formEntity = formDao.findById(formId);
        validateEntity(formEntity);

        return formEntity;
    }

    @Override
    public Set<FormEntity> findAllForms() {
        return formDao.findAll();
    }

    @Override
    @Transactional
    public Set<String> getTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new HashSet<String>(jdbcTemplate.queryForList(TABLE_LIST_SQL, String.class));
    }

    @Override
    @Transactional
    public Set<String> getTableColumns(String tableName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new HashSet<String>(jdbcTemplate.queryForList(COLUMNS_IN_TABLE_SQL, String.class, tableName));
    }
}


