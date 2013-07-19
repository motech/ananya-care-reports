package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ConditionServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ConditionService conditionService;

    private final static int EXPECTED_CONDITION_ENTITIES_ALL = 1;
    private final static Integer CONDITION_ID = 1;

    @Test
    public void testGetAllConditions() {
        Set<ConditionEntity> conditionEntities = conditionService.getAllConditions();

        assertNotNull(conditionEntities);
        assertEquals(EXPECTED_CONDITION_ENTITIES_ALL, conditionEntities.size());
    }

    @Test
    public void testGetConditionEntityById() {
        ConditionEntity conditionEntity = conditionService.getConditionEntityById(CONDITION_ID);

        assertNotNull(conditionEntity);
        assertEquals(CONDITION_ID, conditionEntity.getId());
    }
}
