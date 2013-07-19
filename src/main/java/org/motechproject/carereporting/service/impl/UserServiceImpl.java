package org.motechproject.carereporting.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.motechproject.carereporting.dao.PermissionDao;
import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
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
    @Transactional(readOnly = false)
    public void removeUserById(Integer userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        userDao.remove(user);
    }

    @Transactional
    @Override
    public UserEntity login(String username, String password) {
        String salt = userDao.getSaltForUser(username);
        String encodedPassword = encodePasswordWithSalt(password, salt);
        return userDao.getByUsernameAndPassword(username, encodedPassword);
    }

    @Transactional
    @Override
    public UserEntity getCurrentlyLoggedUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional(readOnly = false)
    @Override
    public void register(String username, String password, String firstName, String lastName, AreaEntity area, Set<RoleEntity> roles) {
        UserEntity user = new UserEntity(username, password, roles);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setArea(area);
        String encodedPassword = encodePasswordWithSalt(user.getPassword(), user.getSalt());
        user.setPassword(encodedPassword);
        try {
            userDao.save(user);
        } catch (ConstraintViolationException e) {
            throw new EntityException("Username already exists", e);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void register(UserEntity userEntity) {
        String encodedPassword = encodePasswordWithSalt(userEntity.getPassword(), userEntity.getSalt());
        userEntity.setPassword(encodedPassword);
        try {
            userDao.save(userEntity);
        } catch (ConstraintViolationException e) {
            throw new EntityException("Username already exists", e);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void updateUser(UserEntity user) {
        try {
            UserEntity userToUpdate = getUserById(user.getId());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRoles(user.getRoles());
            userToUpdate.setArea(user.getArea());
            if (!StringUtils.isEmpty(user.getPassword())) {
                String encodedPassword = encodePasswordWithSalt(user.getPassword(), userToUpdate.getSalt());
                userToUpdate.setPassword(encodedPassword);
            }

            userDao.update(userToUpdate);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new EntityException("Username already exists", e);
        }
    }

    private String encodePasswordWithSalt(String password, String salt) {
        try {
            String stringToEncode = password + salt;
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(stringToEncode.getBytes("UTF-8"));
            return byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new EntityException("Cannot encode user password", e);
        }
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    @Transactional
    @Override
    public Set<RoleEntity> findAllRoles() {
        return roleDao.getAll();
    }

    @Transactional
    @Override
    public RoleEntity getRoleById(Integer id) {
        return roleDao.getById(id);
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

        for (UserEntity userEntity : roleEntity.getUsers()) {
            userEntity.getRoles().remove(roleEntity);
        }

        roleDao.remove(roleEntity);
    }

}
