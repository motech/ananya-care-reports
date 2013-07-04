package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Set;

@RequestMapping(value = "/api/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<RoleEntity> getAllRoles() {
        return userService.findAllRoles();
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<UserEntity> getAllUsers() {
        return userService.findAllUsers();
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
        } catch (UserException e) {
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
        } catch (UserException e) {
            result.rejectValue("username", "Duplicate.userEntity.username");
            throw new CareApiRuntimeException(result.getFieldErrors(), e);
        }
    }

}
