package org.fund.jmx;

import java.util.List;

import org.fund.common.ConstantEnum.AuthType;
import org.fund.user.dao.UserDao;
import org.fund.user.entity.User;
import org.fund.util.DateUtil;

public class FundMonitorServiceImpl implements FundMonitorService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String findAllUserInfo() {
        StringBuilder sb = new StringBuilder();
        List<User> users = userDao.getAllUsers();
        sb.append("total:" + users.size() + "</br>");
        for (User u : users) {
            sb.append(u.getUsername()).append("&emsp;");
            if (u.getAuth() == AuthType.VIP_USER.getId()) {
                sb.append("VIP").append("&emsp;").append(DateUtil.dateIntegerToString(u.getExpiredDate()));
            } else if (u.getAuth() == AuthType.SVIP.getId()) {
                sb.append("SVIP");
            }
            sb.append("</br>");
        }

        return sb.toString();
    }

    @Override
    public String findMemUserInfo() {
        StringBuilder sb = new StringBuilder();
        List<User> memUsers = userDao.getMemUsers();
        sb.append("total:" + memUsers.size() + "</br>");
        for (User u : memUsers) {
            sb.append(u.getUsername()).append("&emsp;").append(DateUtil.dateIntegerToString(u.getExpiredDate()))
                    .append("</br>");
        }

        return sb.toString();
    }

    @Override
    public String upgradeUserQuarter(String username) {
        StringBuilder sb = new StringBuilder();
        try {
            User u = userDao.getByUserName(username);
            if (u == null) {
                sb.append("username not exist!");
            } else {
                int auth = u.getAuth();
                int newExpiredDate;
                if (auth == AuthType.VIP_USER.getId()) { // 已经是会员
                    newExpiredDate = DateUtil.getDateFormat2AddQuarter(u.getExpiredDate());
                } else { // 非会员
                    newExpiredDate = DateUtil.getDateFormat2AddQuarter();
                }
                userDao.upgradeUser(u.getUserId(), newExpiredDate, AuthType.VIP_USER.getId());
                sb.append("success!");
            }
        } catch (Exception e) {
            sb.append("<font color='red'>system error!</font>");
        }
        return sb.toString();
    }

    @Override
    public String upgradeUserHalfYear(String username) {
        StringBuilder sb = new StringBuilder();
        try {
            User u = userDao.getByUserName(username);
            if (u == null) {
                sb.append("username not exist!");
            } else {
                int auth = u.getAuth();
                int newExpiredDate;
                if (auth == AuthType.VIP_USER.getId()) { // 已经是会员
                    newExpiredDate = DateUtil.getDateFormat2AddHalfYear(u.getExpiredDate());
                } else { // 非会员
                    newExpiredDate = DateUtil.getDateFormat2AddHalfYear();
                }
                userDao.upgradeUser(u.getUserId(), newExpiredDate, AuthType.VIP_USER.getId());
                sb.append("success!");
            }
        } catch (Exception e) {
            sb.append("<font color='red'>system error!</font>");
        }
        return sb.toString();
    }

    @Override
    public String upgradeUserOneYear(String username) {
        StringBuilder sb = new StringBuilder();
        try {
            User u = userDao.getByUserName(username);
            if (u == null) {
                sb.append("username not exist!");
            } else {
                int auth = u.getAuth();
                int newExpiredDate;
                if (auth == AuthType.VIP_USER.getId()) { // 已经是会员
                    newExpiredDate = DateUtil.getDateFormat2AddOneYear(u.getExpiredDate());
                } else { // 非会员
                    newExpiredDate = DateUtil.getDateFormat2AddOneYear();
                }
                userDao.upgradeUser(u.getUserId(), newExpiredDate, AuthType.VIP_USER.getId());
                sb.append("success!");

            }
        } catch (Exception e) {
            sb.append("<font color='red'>system error!</font>");
        }
        return sb.toString();
    }

    @Override
    public String upgradeSVIPUser(String username) {
        StringBuilder sb = new StringBuilder();
        try {
            User u = userDao.getByUserName(username);
            if (u == null) {
                sb.append("username not exist!");
            } else {
                userDao.upgradeUser(u.getUserId(), 0, AuthType.SVIP.getId());
                sb.append("success!");
            }
        } catch (Exception e) {
            sb.append("<font color='red'>system error!</font>");
        }
        return sb.toString();
    }

}
