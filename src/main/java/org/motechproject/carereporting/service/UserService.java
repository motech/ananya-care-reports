package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity login(String username, String password);
    void register(String username, String password, List<String> roles);

}

