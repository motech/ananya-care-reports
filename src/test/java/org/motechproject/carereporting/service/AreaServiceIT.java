package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class AreaServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAllAreas() {
        LevelEntity level = new LevelEntity("newLevel", null);
        level.setHierarchyDepth(0);
        areaService.createNewLevel(level);
        AreaEntity area = new AreaEntity("newArea", level);
        areaService.createNewArea(area);
        Set<AreaEntity> areas = areaService.findAllAreas();
        AreaEntity [] areaEntities = {};
        areaEntities = areas.toArray(areaEntities);
        assertNotNull(areas);
        assertEquals(8, areas.size());
    }
}
