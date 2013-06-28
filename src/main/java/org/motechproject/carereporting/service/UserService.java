package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserEntity login(String username, String password);
    void register(String username, String password, Set<RoleEntity> roles);
    List<RoleEntity> getAllRoles();
    void addRole(String roleName);
    void updateUser(UserEntity user);

}

