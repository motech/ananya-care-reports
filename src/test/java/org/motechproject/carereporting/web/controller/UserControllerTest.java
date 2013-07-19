package org.motechproject.carereporting.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String REGISTER_JSON = "{\"username\":\"username\", \"password\":\"password\", \"firstName\":\"Test\", \"lastName\":\"Test\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_NO_USERNAME = "{\"password\":\"password\", \"firstName\":\"Test\", \"lastName\":\"Test\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_NO_PASSWORD = "{\"username\":\"username\", \"firstName\":\"Test\", \"lastName\":\"Test\", \"area\":{\"id\":\"1\"}}";
    private static final String REGISTER_JSON_INVALID_EMAIL = "{\"username\":\"username\", \"firstName\":\"Test\", \"lastName\":\"Test\", \"email\":\"this is not a valid email\", \"area\":{\"id\":\"1\"}}";

    @Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController = new UserController();
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

    @Test
    public void testGetUser() throws Exception {
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

}

