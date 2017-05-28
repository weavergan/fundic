package org.fund.stat.entity;

import java.util.Date;

public class Operation {

    private Long id;

    private Integer userId;

    private String code; // 基金代码

    private String date; // 操作日期

    private Integer purchasePrice; // 申购金额（分）

    private Float sellCount; // 赎回份额

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getSellCount() {
        return sellCount;
    }

    public void setSellCount(Float sellCount) {
        this.sellCount = sellCount;
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

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", userId=" + userId +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellCount=" + sellCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
