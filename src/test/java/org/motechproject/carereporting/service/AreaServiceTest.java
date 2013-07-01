package org.motechproject.carereporting.service;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class AreaServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AreaService areaService;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testGetAllAreas() {
        LevelEntity level = new LevelEntity("newLevel", null);
        areaService.createNewLevel(level);
        AreaEntity area = new AreaEntity("newArea", level);
        areaService.createNewArea(area);
        List<AreaEntity> areas = areaService.getAllAreas();
        assertNotNull(areas);
        assertEquals(areas.size(), 1);
        assertEquals(areas.get(0).getLevel().getName(), "newLevel");
        assertEquals(areas.get(0).getName(), "newArea");
    }
}
