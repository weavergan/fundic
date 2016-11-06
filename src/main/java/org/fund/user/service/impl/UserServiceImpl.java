package org.fund.user.service.impl;

import org.fund.user.dao.UserDao;
import org.fund.user.entity.User;
import org.fund.user.service.UserService;
import org.fund.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByName(String username) {
        User u = userDao.getByUserName(username);
        return u;
    }

    @Override
    public boolean authorityPassword(User user, String password) {
        if (user.getPassword().equals(MD5Util.MD5(password))) {
            return true;
        }
        return false;
    }

    @Override
    public Integer createUser(User user) {
        Integer userId = userDao.createUser(user);
        return userId;
    }

    @Override
    public void resetPassword(User user) {
        userDao.resetPassword(user);

    }

}
