package org.fund.user.entity;

import java.util.Date;

public class User {

    private Integer userId;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Integer auth; // 用户权限 1:普通用户; 2:会员用户

    private Integer expiredDate; // 会员到期时间（普通会员无效）

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public Integer getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Integer expiredDate) {
        this.expiredDate = expiredDate;
    }

}
