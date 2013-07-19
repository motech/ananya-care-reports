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
import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class AreaServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AreaService areaService;

    private final int EXPECTED_AREAS_ALL = 18;
    private final Integer LEVEL_ID = 1;
    private final int EXPECTED_AREAS_BY_LEVEL_ID = 2;
    private final int PARENT_AREA_ID = 1;
    private final int EXPECTED_AREAS_BY_PARENT_AREA_ID = 2;
    private final int EXPECTED_AREAS_DIRECT_CHILD_BY_PARENT_AREA_ID = 8;
    private final Integer AREA_ID = 1;
    private final int EXPECTED_LEVELS_ALL = 5;
    private final String CREATE_AREA_NAME = "AREA_SERVICE_IT_TEST_AREA";
    private final String CREATE_LEVEL_NAME = "AREA_SERVICE_IT_TEST_LEVEL";
    private final String UPDATE_AREA_NAME = "AREA_SERVICE_IT_TEST_AREA_2";

    @Test
    public void testFindAllAreas() {
        Set<AreaEntity> areaEntities = areaService.getAllAreas();

        assertNotNull(areaEntities);
        assertEquals(EXPECTED_AREAS_ALL, areaEntities.size());
    }

    @Test
    public void testFindAreasByLevelId() {
        Set<AreaEntity> areaEntities = areaService.getAreasByLevelId(LEVEL_ID);

        assertNotNull(areaEntities);
        assertEquals(EXPECTED_AREAS_BY_LEVEL_ID, areaEntities.size());
    }

    @Test
    public void testFindAreasByParentAreaId() {
        Set<AreaEntity> areaEntities = areaService.getAreasByParentAreaId(PARENT_AREA_ID);

        assertNotNull(areaEntities);
        assertEquals(EXPECTED_AREAS_BY_PARENT_AREA_ID, areaEntities.size());
    }

    @Test
    public void testFindAllChildAreasByParentAreaId() {
        Set<AreaEntity> areaEntities = areaService.getAllChildAreasByParentAreaId(PARENT_AREA_ID);

        assertNotNull(areaEntities);
        assertEquals(EXPECTED_AREAS_DIRECT_CHILD_BY_PARENT_AREA_ID, areaEntities.size());
    }

    @Test
    public void testFindAreaById() {
        AreaEntity areaEntity = areaService.getAreaById(AREA_ID);

        assertNotNull(areaEntity);
        assertEquals(AREA_ID, areaEntity.getId());
    }

    @Test
    public void testFindAllLevels() {
        Set<LevelEntity> levelEntities = areaService.getAllLevels();

        assertNotNull(levelEntities);
        assertEquals(EXPECTED_LEVELS_ALL, levelEntities.size());
    }

    @Test
    public void testFindLevelById() {
        LevelEntity levelEntity = areaService.getLevelById(LEVEL_ID);

        assertNotNull(levelEntity);
        assertEquals(LEVEL_ID, levelEntity.getId());
    }

    @Test
    public void testCreateNewArea() {
        LevelEntity levelEntity = areaService.getLevelById(LEVEL_ID);
        assertNotNull(levelEntity);
        assertEquals(LEVEL_ID, levelEntity.getId());

        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setName(CREATE_AREA_NAME);
        areaEntity.setLevel(levelEntity);

        areaService.createNewArea(areaEntity);

        Integer areaId = areaEntity.getId();
        assertNotNull(areaId);

        areaEntity = areaService.getAreaById(areaId);

        assertNotNull(areaEntity);
        assertEquals(areaId, areaEntity.getId());
    }

    @Test
    public void testUpdateArea() {
        AreaEntity areaEntity = areaService.getAreaById(AREA_ID);
        assertNotNull(areaEntity);

        areaEntity.setName(UPDATE_AREA_NAME);

        areaService.updateArea(areaEntity);

        areaEntity = areaService.getAreaById(AREA_ID);
        assertNotNull(areaEntity);
        assertEquals(UPDATE_AREA_NAME, areaEntity.getName());
    }

    @Test
    public void testCreateNewLevel() {
        LevelEntity levelEntity = new LevelEntity();
        levelEntity.setName(CREATE_LEVEL_NAME);
        levelEntity.setHierarchyDepth(0);
        levelEntity.setParentLevel(null);
        levelEntity.setAreas(null);

        areaService.createNewLevel(levelEntity);
    }
}
