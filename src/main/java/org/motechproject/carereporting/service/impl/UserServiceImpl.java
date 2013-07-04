package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.PermissionDao;
import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.PermissionEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    @Transactional
    public Set<UserEntity> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public UserEntity findUserById(Integer id) {
        return userDao.findById(id);
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
        return userDao.findByUsernameAndPassword(username, encodedPassword);
    }

    @Transactional(readOnly = false)
    @Override
    public void register(String username, String password, Set<RoleEntity> roles) {
        UserEntity user = new UserEntity(username, password, roles);
        String encodedPassword = encodePasswordWithSalt(user.getPassword(), user.getSalt());
        user.setPassword(encodedPassword);
        userDao.save(user);
    }

    @Transactional(readOnly = false)
    @Override
    public void register(UserEntity userEntity) {
        String encodedPassword = encodePasswordWithSalt(userEntity.getPassword(), userEntity.getSalt());
        userEntity.setPassword(encodedPassword);
        userDao.save(userEntity);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateUser(UserEntity user) {
        UserEntity userToUpdate = findUserById(user.getId());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setRoles(user.getRoles());
        if (!StringUtils.isEmpty(user.getPassword())) {
            String encodedPassword = encodePasswordWithSalt(user.getPassword(), userToUpdate.getSalt());
            userToUpdate.setPassword(encodedPassword);
        }
        userDao.update(userToUpdate);
    }

    private String encodePasswordWithSalt(String password, String salt) {
        try {
            String stringToEncode = password + salt;
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(stringToEncode.getBytes("UTF-8"));
            return byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new UserException("Cannot encode user password", e);
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
        return roleDao.findAll();
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
    public Set<PermissionEntity> findAllPermissions() {
        return permissionDao.findAll();
    }

    @Transactional
    @Override
    public PermissionEntity findPermissionById(Integer id) {
        return permissionDao.findById(id);
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

}


