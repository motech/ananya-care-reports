package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public UserEntity login(String username, String password) {
        String salt = userDao.getSaltForUser(username);
        String encodedPassword = encodePasswordWithSalt(password, salt);
        UserEntity user = userDao.findByUsernameAndPassword(username, encodedPassword);
        if (user != null) {
            return user;
        }
        throw new UserException("Bad username or password");
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
    public List<RoleEntity> getAllRoles() {
        return roleDao.findAll();
    }

    @Transactional(readOnly = false)
    @Override
    public void addRole(String roleName) {
        RoleEntity role = new RoleEntity();
        role.setName(roleName);
        roleDao.save(role);
    }

    @Override
    public void updateUser(UserEntity user) {
        userDao.update(user);
    }

}


