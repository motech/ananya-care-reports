package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.service.FormsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FormsServiceImpl implements FormsService {

    @Resource(name = "careDataSource")
    private DataSource dataSource;

    private static final String TABLE_LIST_SQL =
            "SELECT table_name FROM information_schema.tables WHERE table_name like '%_form' and table_schema ='report'";

    private static final String COLUMNS_IN_TABLE_SQL =
            "SELECT column_name FROM information_schema.COLUMNS WHERE table_schema='report' AND TABLE_NAME = ?";

    @Override
    @Transactional
    public List<String> getTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (List<String>) jdbcTemplate.queryForList(TABLE_LIST_SQL, String.class);
    }

    @Override
    @Transactional
    public List<String> getTableColumns(String tableName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (List<String>) jdbcTemplate.queryForList(COLUMNS_IN_TABLE_SQL, String.class, tableName);
    }
}


