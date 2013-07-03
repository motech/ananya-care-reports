package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;

import java.util.Set;

public interface UserService {

    Set<UserEntity> getAllUsers();

    UserEntity login(String username, String password);

    void register(String username, String password, Set<RoleEntity> roles);

    void register(UserEntity userEntity);

    void updateUser(UserEntity user);

    Set<RoleEntity> getAllRoles();

    void addRole(String roleName);

    Set<PermissionEntity> getAllPermissions();

    PermissionEntity getPermissionById(Integer id);

    void createNewPermission(PermissionEntity permissionEntity);

    void updatePermission(PermissionEntity permissionEntity);

    void deletePermission(PermissionEntity permissionEntity);
}

