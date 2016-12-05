package org.fund.stat.entity;

public class Record {

    private String date; // 日期
    private Float value; // 净值 4位小数
    private Float increase; // 涨幅 2位小数（百分之）
    private Float sumInc; // 累涨 2位小数（百分之）
    private Float purchasePrice; // 申购金额 2位小数
    private Float purchaseCount; // 确认份额 2位小数
    private Float sellCount; // 赎回份额 2位小数
    private Float sellPrice; // 确认金额 2位小数
    private Float holdCount; // 持有份数 2位小数
    private Float holdPrice; // 资产市值 2位小数
    private Float allInvest; // 总投入 2位小数
    private Float allProfitPrice; // 净盈亏 2位小数
    private Float allProfitPot; // 收益 2位小数
    private Float holdValue; // 持仓成本
    //Deprecated 页面取消展示
    private Float addRatio; // 追加比 2位小数
    private String fundStatus; // 申购状态
    private String fundSellStatus; // 赎回状态
    private String fenhong; // 分红配送

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getIncrease() {
        return increase;
    }

    public void setIncrease(Float increase) {
        this.increase = increase;
    }

    public Float getSumInc() {
        return sumInc;
    }

    public void setSumInc(Float sumInc) {
        this.sumInc = sumInc;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Float purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Float getSellCount() {
        return sellCount;
    }

    public void setSellCount(Float sellCount) {
        this.sellCount = sellCount;
    }

    public Float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Float getHoldCount() {
        return holdCount;
    }

    public void setHoldCount(Float holdCount) {
        this.holdCount = holdCount;
    }

    public Float getHoldPrice() {
        return holdPrice;
    }

    public void setHoldPrice(Float holdPrice) {
        this.holdPrice = holdPrice;
    }

    public Float getAllInvest() {
        return allInvest;
    }

    public void setAllInvest(Float allInvest) {
        this.allInvest = allInvest;
    }

    public Float getAllProfitPrice() {
        return allProfitPrice;
    }

    public void setAllProfitPrice(Float allProfitPrice) {
        this.allProfitPrice = allProfitPrice;
    }

    public Float getAllProfitPot() {
        return allProfitPot;
    }

    public void setAllProfitPot(Float allProfitPot) {
        this.allProfitPot = allProfitPot;
    }

    public Float getAddRatio() {
        return addRatio;
    }

    public void setAddRatio(Float addRatio) {
        this.addRatio = addRatio;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getFenhong() {
        return fenhong;
    }

    public void setFenhong(String fenhong) {
        this.fenhong = fenhong;
    }

    public Float getHoldValue() {
        return holdValue;
    }

    public void setHoldValue(Float holdValue) {
        this.holdValue = holdValue;
    }

    public String getFundSellStatus() {
        return fundSellStatus;
    }

    public void setFundSellStatus(String fundSellStatus) {
        this.fundSellStatus = fundSellStatus;
    }

}
