package org.motechproject.carereporting.api;

import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(value = "api/user")
@Controller
public class UserApiResource {

    //@Autowired
    //private UserService userService;

    @RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public void registerUser(@Valid UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        //userService.register(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
    }
}
