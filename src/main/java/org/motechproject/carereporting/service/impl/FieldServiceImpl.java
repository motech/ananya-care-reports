package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.FieldDao;
import org.motechproject.carereporting.domain.FieldEntity;
import org.motechproject.carereporting.domain.types.FieldType;
import org.motechproject.carereporting.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<String> getAllFieldNamesByFormId(Integer formId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = String.format("select name from dashboard_app.field where form_id = %s", formId);

        return jdbcTemplate.queryForList(query, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<FieldEntity> getAllFieldsByFormId(Integer formId) {
        Query query = sessionFactory.getCurrentSession()
            .createQuery("from FieldEntity where form.id = :formId");
        query.setParameter("formId", formId);

        return new LinkedHashSet<FieldEntity>(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<FieldEntity> getAllFieldsByType(FieldType fieldType) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from FieldEntity where type = :fieldType");
        query.setParameter("fieldType", fieldType);

        return new LinkedHashSet<FieldEntity>(query.list());
    }

    @Override
    public FieldEntity getFieldById(Integer fieldId) {
        return fieldDao.getById(fieldId);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewField(FieldEntity fieldEntity) {
        fieldDao.save(fieldEntity);
    }
}
