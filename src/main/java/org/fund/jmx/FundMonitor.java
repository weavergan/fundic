package org.fund.jmx;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author jiangxinglan
 *
 */
public class FundMonitor implements FundMonitorMBean {

    private FundMonitorService fundMonitorService;

    public FundMonitorService getFundMonitorService() {
        return fundMonitorService;
    }

    public void setFundMonitorService(FundMonitorService fundMonitorService) {
        this.fundMonitorService = fundMonitorService;
    }

    public String howToUse() {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("<font size=4 color=red>findFailedMarsTask()</font>").append("</br>");
            sb.append("获得失败的任务信息").append("</br>");
            sb.append("</br>");

            sb.append("<font size=4 color=red>getAllRunningTask()</font>").append("</br>");
            sb.append("获得正在运行的任务信息").append("</br>");
            sb.append("</br>");

            return new String(sb.toString().getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String findMemUserInfo() {
        return getFundMonitorService().findMemUserInfo();
    }

    @Override
    public String upgradeUserQuarter(String username) {
        return getFundMonitorService().upgradeUserQuarter(username);
    }

    @Override
    public String upgradeUserHalfYear(String username) {
        return getFundMonitorService().upgradeUserHalfYear(username);
    }

    @Override
    public String upgradeUserOneYear(String username) {
        return getFundMonitorService().upgradeUserOneYear(username);
    }

    @Override
    public String upgradeSVIPUser(String username) {
        return getFundMonitorService().upgradeSVIPUser(username);
    }

    @Override
    public String findAllUserInfo() {
        return getFundMonitorService().findAllUserInfo();
    }

}
