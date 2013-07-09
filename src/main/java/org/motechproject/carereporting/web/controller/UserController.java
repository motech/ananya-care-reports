package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RequestMapping(value = "/api/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private IndicatorService indicatorService;

    @RequestMapping(value = "/indicators", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorEntity> getIndicatorsInUserArea(Principal principal) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken)principal;
        UserEntity userEntity = (UserEntity)usernamePasswordAuthenticationToken.getPrincipal();
        return indicatorService.findAllIndicatorsUnderUserArea(userEntity.getArea().getId());
    }

    @RequestMapping(value = "/areas", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<AreaEntity> getAllAreas() {
        return areaService.findAllAreas();
    }

    @RequestMapping(value = "/areas/{areaId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AreaEntity getArea(@PathVariable Integer areaId) {
        return areaService.findAreaById(areaId);
    }

    @RequestMapping(value = "/areas/level/{levelId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<AreaEntity> getAreasByLevelId(@PathVariable Integer levelId) {
        return areaService.findAreasByLevelId(levelId);
    }

    @RequestMapping(value = "/areas/{areaId}/list", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<AreaEntity> getAreasByParentAreaId(@PathVariable Integer areaId) {
        return areaService.findAreasByParentAreaId(areaId);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<RoleEntity> getAllRoles() {
        return userService.findAllRoles();
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RoleEntity getRole(@PathVariable Integer roleId) {
        return userService.findRoleById(roleId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<UserEntity> getAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewRole(@RequestBody @Valid RoleEntity roleEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        userService.createNewRole(roleEntity);
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateRole(@RequestBody @Valid RoleEntity roleEntity, BindingResult bindingResult,
            @PathVariable Integer roleId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        roleEntity.setId(roleId);
        userService.updateRole(roleEntity);
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateRole(@PathVariable Integer roleId) {
        userService.removeRoleById(roleId);
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<PermissionEntity> getAllPermissions() {
        return userService.findAllPermissions();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserEntity getUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteUser(@PathVariable Integer userId) {
        userService.removeUserById(userId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody @Valid UserEntity userEntity, BindingResult bindingResult,
                             HttpServletRequest request) {
        if (StringUtils.isEmpty(userEntity.getPassword())) {
            bindingResult.rejectValue("password", "NotNull.userEntity.password");
        }

        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        try {
            userService.register(userEntity);
        } catch (EntityException e) {
            bindingResult.rejectValue("username", "Duplicate.userEntity.username");
            throw new CareApiRuntimeException(bindingResult.getFieldErrors(), e);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody @PathVariable Integer userId, @RequestBody @Valid UserEntity userEntity, BindingResult result) {
        if (result.hasErrors()) {
            throw new CareApiRuntimeException(result.getFieldErrors());
        }

        try {
            userService.updateUser(userEntity);
        } catch (EntityException e) {
            result.rejectValue("username", "Duplicate.userEntity.username");
            throw new CareApiRuntimeException(result.getFieldErrors(), e);
        }
    }

}
