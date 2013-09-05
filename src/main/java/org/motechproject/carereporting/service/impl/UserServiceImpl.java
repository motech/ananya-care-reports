package org.motechproject.carereporting.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.motechproject.carereporting.dao.PermissionDao;
import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private DashboardService dashboardService;

    @Override
    @Transactional
    public Set<UserEntity> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public UserEntity getUserById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public UserEntity getUserByName(String name) {
        return userDao.getByField("username", name);
    }

    @Override
    public boolean doesUserExist(String userName) {
        return userDao.doesUserExist(userName);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeUserById(Integer userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        userDao.remove(user);
    }

    @Override
    public UserEntity getCurrentlyLoggedUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional(readOnly = false)
    @Override
    public UserEntity register(String username, AreaEntity area, Set<RoleEntity> roles) {
        UserEntity user = new UserEntity(username, roles);
        user.setArea(area);

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(1);
        user.setDefaultLanguage(languageEntity);
        DashboardEntity dashboardEntity = dashboardService.getDashboardById(1);
        user.setDefaultDashboard(dashboardEntity);
        try {
            userDao.save(user);
            return userDao.getByField("username", user.getUsername());
        } catch (ConstraintViolationException e) {
            throw new EntityException("Username already exists", e);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void register(UserEntity userEntity) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(1);
        userEntity.setDefaultLanguage(languageEntity);
        DashboardEntity dashboardEntity = dashboardService.getDashboardById(1);
        userEntity.setDefaultDashboard(dashboardEntity);
        try {
            userDao.save(userEntity);
        } catch (ConstraintViolationException e) {
            throw new EntityException("Username already exists", e);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void updateUser(UserEntity user) {
        userDao.update(user);
    }

    @Transactional
    @Override
    public Set<RoleEntity> getAllRoles() {
        return roleDao.getAll();
    }

    @Transactional
    @Override
    public RoleEntity getRoleById(Integer id) {
        return roleDao.getById(id);
    }

    @Transactional
    @Override
    public RoleEntity getRoleByName(String name) {
        return roleDao.getByName(name);
    }

    @Transactional(readOnly = false)
    @Override
    public void addRole(String roleName) {
        RoleEntity role = new RoleEntity();
        role.setName(roleName);
        roleDao.save(role);
    }

    @Transactional
    @Override
    public Set<PermissionEntity> getAllPermissions() {
        return permissionDao.getAll();
    }

    @Transactional
    @Override
    public PermissionEntity getPermissionById(Integer id) {
        return permissionDao.getById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewPermission(PermissionEntity permissionEntity) {
        permissionDao.save(permissionEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updatePermission(PermissionEntity permissionEntity) {
        permissionDao.update(permissionEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void deletePermission(PermissionEntity permissionEntity) {
        permissionDao.remove(permissionEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void createNewRole(RoleEntity roleEntity) {
        roleDao.save(roleEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateRole(RoleEntity roleEntity) {
        roleDao.update(roleEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void removeRoleById(Integer id) {
        RoleEntity roleEntity = this.getRoleById(id);
        roleDao.remove(roleEntity);
    }

}
