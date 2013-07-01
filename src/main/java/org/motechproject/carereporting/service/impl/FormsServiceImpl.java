package org.motechproject.carereporting.service.impl;

import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.UserException;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FormsServiceImpl implements FormsService {

    @Autowired
    private SessionFactory sessionFactory;

    @Resource(name = "careDataSource")
    private DataSource dataSource;

    private static final String TABLE_LIST_SQL =
            "SELECT table_name FROM information_schema.tables WHERE table_name like '%_form' and table_schema='report'";

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


