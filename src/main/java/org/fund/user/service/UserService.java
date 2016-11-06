package org.fund.user.service;

import org.fund.user.entity.User;

public interface UserService {

    public User getUserByName(String username);

    public boolean authorityPassword(User user, String password);

    public Integer createUser(User user);

    // 重置密码
    public void resetPassword(User user);

}
