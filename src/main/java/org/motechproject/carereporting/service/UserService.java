package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface UserService {

    String HAS_ROLE_MANAGE_SYSTEM_USERS = "hasRole('CAN_MANAGE_SYSTEM_USERS')";

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    Set<UserEntity> findAllUsers();

    UserEntity login(String username, String password);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    void register(String username, String password, String firstName, String lastName, AreaEntity area, Set<RoleEntity> roles);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    void register(UserEntity userEntity);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    void updateUser(UserEntity user);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    Set<RoleEntity> findAllRoles();

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    RoleEntity findRoleById(Integer id);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    void addRole(String roleName);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    UserEntity findUserById(Integer id);

    @PreAuthorize(HAS_ROLE_MANAGE_SYSTEM_USERS)
    void removeUserById(Integer userId);

    Set<PermissionEntity> findAllPermissions();

    PermissionEntity findPermissionById(Integer id);

    void createNewPermission(PermissionEntity permissionEntity);

    void updatePermission(PermissionEntity permissionEntity);

    void deletePermission(PermissionEntity permissionEntity);

    void createNewRole(RoleEntity roleEntity);

    void updateRole(RoleEntity roleEntity);

    void removeRoleById(Integer id);
}

