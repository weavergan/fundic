package org.fund.stat.entity;

import java.util.Date;

public class Materiel {

    private Long userId;

    private String code; // 基金代码

    private String startDate; // 统计起始时间

    private String endDate; // 统计结束时间

    private Date createTime;

    private Date updateTime;

    private Integer selected; // 1是选中；0是非选中

    private Integer purchaseFee; // 申购费率 （万分之）

    private Integer sellFee; // 赎回费率 （万分之）

    private Float baseMultiple; // 倍数

    private Integer startInvest; // 起投跌幅 （万分之）

    private Float moreMultiple; // 加倍

    //Deprecated
    private Float firstInvest; // 首次投入

    private Integer isStopProfit; // 是否开启止盈提醒（0：关闭，1：开启），控制stopProfit属性开关

    private Integer stopProfitPot; // 止盈点（万分之）

    private Integer isAutoSell; // 是否开启“越涨越卖”
                                // （0：关闭，1：开启），控制startSell,minSellCount 属性开关

    private Integer startSell; // 起赎收益（万分之）

    private Integer minSellCount;// 最低赎回

    //Deprecated
    private Integer isZS; // 是否开启“折算提醒”（0：关闭，1：开启）

    private Integer isFenhongInvest; // 是否开启“红利再投”（0：关闭，1：开启）

    private Integer isIncrease; // 是否开启“加仓优先”（0：关闭，1：开启）

    private Integer isSetMinRate; // 是否开启“保证追加比”（0：关闭，1：开启）

    private Integer minHoldCount; // 最低保留份数

    private Integer totalInvest; //资金总量

    private Integer riskRate; //风控仓位 （百分之）

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
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

    public Integer getPurchaseFee() {
        return purchaseFee;
    }

    public void setPurchaseFee(Integer purchaseFee) {
        this.purchaseFee = purchaseFee;
    }

    public Integer getSellFee() {
        return sellFee;
    }

    public void setSellFee(Integer sellFee) {
        this.sellFee = sellFee;
    }

    public Float getBaseMultiple() {
        return baseMultiple;
    }

    public void setBaseMultiple(Float baseMultiple) {
        this.baseMultiple = baseMultiple;
    }

    public Integer getStartInvest() {
        return startInvest;
    }

    public void setStartInvest(Integer startInvest) {
        this.startInvest = startInvest;
    }

    public Float getMoreMultiple() {
        return moreMultiple;
    }

    public void setMoreMultiple(Float moreMultiple) {
        this.moreMultiple = moreMultiple;
    }

    public Float getFirstInvest() {
        return firstInvest;
    }

    public void setFirstInvest(Float firstInvest) {
        this.firstInvest = firstInvest;
    }

    public Integer getIsStopProfit() {
        return isStopProfit;
    }

    public void setIsStopProfit(Integer isStopProfit) {
        this.isStopProfit = isStopProfit;
    }

    public Integer getStopProfitPot() {
        return stopProfitPot;
    }

    public void setStopProfitPot(Integer stopProfitPot) {
        this.stopProfitPot = stopProfitPot;
    }

    public Integer getIsAutoSell() {
        return isAutoSell;
    }

    public void setIsAutoSell(Integer isAutoSell) {
        this.isAutoSell = isAutoSell;
    }

    public Integer getStartSell() {
        return startSell;
    }

    public void setStartSell(Integer startSell) {
        this.startSell = startSell;
    }

    public Integer getMinSellCount() {
        return minSellCount;
    }

    public void setMinSellCount(Integer minSellCount) {
        this.minSellCount = minSellCount;
    }

    public Integer getIsZS() {
        return isZS;
    }

    public void setIsZS(Integer isZS) {
        this.isZS = isZS;
    }

    public Integer getIsFenhongInvest() {
        return isFenhongInvest;
    }

    public void setIsFenhongInvest(Integer isFenhongInvest) {
        this.isFenhongInvest = isFenhongInvest;
    }

    public Integer getIsIncrease() {
        return isIncrease;
    }

    public void setIsIncrease(Integer isIncrease) {
        this.isIncrease = isIncrease;
    }

    public Integer getIsSetMinRate() {
        return isSetMinRate;
    }

    public void setIsSetMinRate(Integer isSetMinRate) {
        this.isSetMinRate = isSetMinRate;
    }

    public Integer getMinHoldCount() {
        return minHoldCount;
    }

    public void setMinHoldCount(Integer minHoldCount) {
        this.minHoldCount = minHoldCount;
    }

    public Integer getTotalInvest() {
        return totalInvest;
    }

    public void setTotalInvest(Integer totalInvest) {
        this.totalInvest = totalInvest;
    }

    public Integer getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(Integer riskRate) {
        this.riskRate = riskRate;
    }
}
