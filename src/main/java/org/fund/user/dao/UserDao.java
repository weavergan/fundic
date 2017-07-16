package org.fund.user.dao;

import org.apache.ibatis.annotations.Param;
import org.fund.user.entity.User;

import java.util.List;

public interface UserDao {

    User getUserById(@Param("userId") Long userId);

    public User getByUserName(@Param("username")String username);

    public Integer createUser(User user);

    // 查询所有用户
    public List<User> getAllUsers();

    // 查询所有会员用户
    public List<User> getMemUsers();

    // 升级成会员
    public void upgradeUser(@Param("userId") Integer userId, @Param("expiredDate") Integer expiredDate,
            @Param("auth") Integer auth);

    // 查找当前过期的会员
    public List<User> findExpiredMemUsers(Integer curDate);

    // 把过期会员变成普通会员
    public void updateMemToNormal(@Param("userIds") List<Integer> userIds);

    public void resetPassword(User user);
}
