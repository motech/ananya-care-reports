package org.motechproject.carereporting.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class FrequencyDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FrequencyDao frequencyDao;

    @Test
    public void testGetByFrequencyName() {
        String name = "monthly";
        String parentName = "quarterly";
        FrequencyEntity frequencyEntity = frequencyDao.getByFrequencyName(name);

        assertNotNull(frequencyEntity);
        assertEquals(name, frequencyEntity.getFrequencyName());
        assertEquals(parentName, frequencyEntity.getParentFrequency().getFrequencyName());
    }

}
