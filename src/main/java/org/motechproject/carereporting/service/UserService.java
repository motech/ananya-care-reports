package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;

import java.util.Set;

public interface UserService {

    Set<UserEntity> getAllUsers();

    UserEntity getCurrentlyLoggedUser();

    UserEntity register(String username, AreaEntity area, Set<RoleEntity> roles);

    void register(UserEntity userEntity);

    void updateUser(UserEntity user);

    Set<RoleEntity> getAllRoles();

    RoleEntity getRoleById(Integer id);

    RoleEntity getRoleByName(String name);

    void addRole(String roleName);

    UserEntity getUserById(Integer id);

    UserEntity getUserByName(String name);

    boolean doesUserExist(String userName);

    void removeUserById(Integer userId);

    Set<PermissionEntity> getAllPermissions();

    PermissionEntity getPermissionById(Integer id);

    void createNewPermission(PermissionEntity permissionEntity);

    void updatePermission(PermissionEntity permissionEntity);

    void deletePermission(PermissionEntity permissionEntity);

    void createNewRole(RoleEntity roleEntity);

    void updateRole(RoleEntity roleEntity);

    void removeRoleById(Integer id);

    Set<String> getUserPermissions(UserEntity userEntity);
}

