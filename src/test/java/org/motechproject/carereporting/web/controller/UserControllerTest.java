package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String REGISTER_JSON = "{\"username\":\"username\", \"password\":\"password\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_NO_USERNAME = "{\"password\":\"password\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_NO_PASSWORD = "{\"username\":\"username\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_INVALID_EMAIL = "{\"username\":\"username\", \"email\":\"this is not a valid email\", \"area\":{\"id\":\"1\"}}";
    private static final String CREATE_ROLE_JSON = "{\"id\":\"1\", \"name\":\"name\"}";
    private static final String UPDATE_ROLE_JSON = "{\"name\":\"new name\"}";
    private static final String UPDATE_USER_JSON = "{\"username\":\"new username\", \"password\":\"password\", \"area\":{\"id\":\"1\"}}";

    @Mock
    private UserService userService;

    @Mock
    private AreaService areaService;

    @Mock
    private IndicatorService indicatorService;

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private UserController userController = new UserController();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /*@Test
    public void testGetIndicatorsInUserArea() throws Exception {
        Integer id = 1;
        String username = "username";
        String password = "password";
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserEntity userEntity = new UserEntity(username, password);
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setId(id);
        userEntity.setArea(areaEntity);
        String indicatorName = "indicatorName";
        FrequencyEntity frequencyEntity = new FrequencyEntity();
        frequencyEntity.setId(id);
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setId(id);
        indicatorEntity.setName(indicatorName);
        indicatorEntity.setDefaultFrequency(frequencyEntity);
        Set<IndicatorEntity> indicatorEntities = new LinkedHashSet<>();
        indicatorEntities.add(indicatorEntity);

        when(indicatorService.getAllIndicatorsUnderUserArea(id)).thenReturn(indicatorEntities);
        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);

        mockMvc.perform(get("/api/users/indicators"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(indicatorName));

        verify(indicatorService).getAllIndicatorsUnderUserArea(id);
    }*/

    @Test
    public void testGetAllAreas() throws Exception {
        Integer id = 1;
        String areaName = "areaName";
        String levelName = "levelName";
        LevelEntity levelEntity = new LevelEntity();
        levelEntity.setId(id);
        levelEntity.setName(levelName);
        AreaEntity areaEntity = new AreaEntity(areaName, levelEntity);
        areaEntity.setId(id);

        Set<AreaEntity> areaEntitySet = new LinkedHashSet<>();
        areaEntitySet.add(areaEntity);

        when(areaService.getAllAreas()).thenReturn(areaEntitySet);

        mockMvc.perform(get("/api/users/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(areaName))
                .andExpect(jsonPath("$[0].level.id").value(id))
                .andExpect(jsonPath("$[0].level.name").value(levelName));

        verify(areaService).getAllAreas();
    }

    @Test
    public void testGetArea() throws Exception {
        Integer id = 1;
        String areaName = "areaName";
        String levelName = "levelName";
        LevelEntity levelEntity = new LevelEntity();
        levelEntity.setId(id);
        levelEntity.setName(levelName);
        AreaEntity areaEntity = new AreaEntity(areaName, levelEntity);
        areaEntity.setId(id);

        when(areaService.getAreaById(id)).thenReturn(areaEntity);

        mockMvc.perform(get("/api/users/areas/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(areaName));

        verify(areaService).getAreaById(id);
    }

    @Test
    public void testGetAreasByLevelId() throws Exception {
        Integer id = 1;
        String areaName = "areaName";
        String levelName = "levelName";
        LevelEntity levelEntity = new LevelEntity();
        levelEntity.setId(id);
        levelEntity.setName(levelName);
        AreaEntity areaEntity = new AreaEntity(areaName, levelEntity);
        areaEntity.setId(id);

        Set<AreaEntity> areaEntitySet = new LinkedHashSet<>();
        areaEntitySet.add(areaEntity);

        when(areaService.getAreasByLevelId(id)).thenReturn(areaEntitySet);

        mockMvc.perform(get("/api/users/areas/level/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(areaName));

        verify(areaService).getAreasByLevelId(id);
    }

    @Test
    public void testGetAreasByParentAreaId() throws Exception {
        Integer id = 1;
        String areaName = "areaName";
        String levelName = "levelName";
        LevelEntity levelEntity = new LevelEntity();
        levelEntity.setId(id);
        levelEntity.setName(levelName);
        AreaEntity areaEntity = new AreaEntity(areaName, levelEntity);
        areaEntity.setId(id);

        Set<AreaEntity> areaEntitySet = new LinkedHashSet<>();
        areaEntitySet.add(areaEntity);

        when(areaService.getAreasByParentAreaId(id)).thenReturn(areaEntitySet);

        mockMvc.perform(get("/api/users/areas/" + id + "/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(areaName));

        verify(areaService).getAreasByParentAreaId(id);
    }

    @Test
    public void testGetAllRoles() throws Exception {
        Integer id = 1;
        String name = "roleName";
        RoleEntity roleEntity = new RoleEntity(name);
        roleEntity.setId(id);

        Set<RoleEntity> roleEntities = new LinkedHashSet<>();
        roleEntities.add(roleEntity);

        when(userService.getAllRoles()).thenReturn(roleEntities);

        mockMvc.perform(get("/api/users/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name));

        verify(userService).getAllRoles();
    }

    @Test
    public void testGetRole() throws Exception {
        Integer id = 1;
        String name = "roleName";
        RoleEntity roleEntity = new RoleEntity(name);
        roleEntity.setId(id);

        when(userService.getRoleById(id)).thenReturn(roleEntity);

        mockMvc.perform(get("/api/users/roles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));

        verify(userService).getRoleById(id);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Integer id = 1;
        String username = "username";
        String password = "password";
        UserEntity userEntity = new UserEntity(username, password);
        userEntity.setId(id);

        Set<UserEntity> userEntities = new LinkedHashSet<>();
        userEntities.add(userEntity);

        when(userService.getAllUsers()).thenReturn(userEntities);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].username").value(username));

        verify(userService).getAllUsers();
    }

    @Test
    public void testGetAllPermissions() throws Exception {
        Integer id = 1;
        String name = "name";
        String displayName = "displayName";
        PermissionEntity permissionEntity = new PermissionEntity(name, displayName);
        permissionEntity.setId(id);

        Set<PermissionEntity> permissionEntities = new LinkedHashSet<>();
        permissionEntities.add(permissionEntity);

        when(userService.getAllPermissions()).thenReturn(permissionEntities);

        mockMvc.perform(get("/api/users/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].displayName").value(displayName));

        verify(userService).getAllPermissions();
    }

    @Test
    public void testCreateNewRole() throws Exception {
        mockMvc.perform(post("/api/users/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CREATE_ROLE_JSON))
                .andExpect(status().isOk());

        verify(userService).createNewRole((RoleEntity) anyObject());
    }

    @Test
    public void testUpdateRole() throws Exception {
        Integer id = 1;
        mockMvc.perform(put("/api/users/roles/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_ROLE_JSON))
                .andExpect(status().isOk());

        verify(userService).updateRole((RoleEntity) anyObject());
    }

    @Test
    public void testUpdateUser() throws Exception {
        Integer id = 1;
        mockMvc.perform(put("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_USER_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUser((UserEntity) anyObject());
    }

    @Test
    public void testUpdateUserWithDuplicateUsername() throws Exception {
        Integer id = 1;

        doThrow(new EntityException()).when(userService).updateUser((UserEntity) anyObject());

        mockMvc.perform(put("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_USER_JSON))
                .andExpect(status().isBadRequest());

        verify(userService).updateUser((UserEntity) anyObject());
    }

    @Test
    public void testDeleteRole() throws Exception {
        Integer id = 1;
        mockMvc.perform(delete("/api/users/roles/" + id))
                .andExpect(status().isOk());

        verify(userService).removeRoleById(id);
    }

    @Test
    public void testDeleteUser() throws Exception {
        Integer id = 1;
        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isOk());

        verify(userService).removeUserById(id);
    }

    @Test
    public void testGetUserById() throws Exception {
        Integer userId = 1;
        String username = "username";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRoles(new HashSet<RoleEntity>());

        when(userService.getUserById(userId)).thenReturn(userEntity);

        mockMvc.perform(get("/api/users/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(username));

        verify(userService).getUserById(userId);
    }

    @Test
    public void testRegisterUsernameDuplicate() throws Exception {
        doThrow(new EntityException()).when(userService).register((UserEntity) anyObject());

        mockMvc.perform(put("/api/users")
                .content(REGISTER_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).register((UserEntity) anyObject());
    }

    @Test
    public void testRegisterSuccessful() throws Exception {
        mockMvc.perform(put("/api/users")
                    .content(REGISTER_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).register((UserEntity) anyObject());
    }

    @Test
    public void testRegisterInvalidEmail() throws Exception {
        mockMvc.perform(put("/api/users")
                .content(REGISTER_JSON_INVALID_EMAIL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).register((UserEntity) anyObject());
    }

    @Test
    public void testRegisterUsernameEmptyValidation() throws Exception {
        mockMvc.perform(put("/api/users")
                .content(REGISTER_JSON_NO_USERNAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).register((UserEntity) anyObject());
    }

    @Test
    public void testRegisterPasswordEmptyValidation() throws Exception {
        mockMvc.perform(put("/api/users")
                .content(REGISTER_JSON_NO_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).register((UserEntity) anyObject());
    }

    @Test
    public void testGetDefaultUserDashboard() throws Exception {
        Integer id = 2;
        UserEntity userEntity = new UserEntity();
        DashboardEntity dashboardEntity = new DashboardEntity();
        dashboardEntity.setId(id);
        userEntity.setDefaultDashboard(dashboardEntity);

        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);

        mockMvc.perform(get("/api/users/logged_in/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(userService).getCurrentlyLoggedUser();
    }

    @Test
    public void testUpdateDefaultUserDashboard() throws Exception {
        Integer id = 2;
        UserEntity userEntity = new UserEntity();
        DashboardEntity dashboardEntity = new DashboardEntity();
        dashboardEntity.setId(id);

        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);
        when(dashboardService.getDashboardById(id)).thenReturn(dashboardEntity);

        when(userService.getCurrentlyLoggedUser()).thenReturn(userEntity);
        mockMvc.perform(put("/api/users/logged_in/dashboard/" + id))
                .andExpect(status().isOk());

        verify(userService).getCurrentlyLoggedUser();
        verify(dashboardService).getDashboardById(id);
    }

}

