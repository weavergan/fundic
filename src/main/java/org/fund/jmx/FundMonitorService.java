package org.fund.jmx;

public interface FundMonitorService {

    /**
     * 获得所有用户的信息
     * @return
     */
    public String findAllUserInfo();

    /**
     * 获得会员账号和到期时间
     * @return
     */
    public String findMemUserInfo();

    /**
     * 升级用户成会员(季度)
     * @return
     */
    public String upgradeUserQuarter(String username);

    /**
     * 升级用户成会员(半年)
     * @return
     */
    public String upgradeUserHalfYear(String username);

    /**
     * 升级用户成会员(一年)
     * @return
     */
    public String upgradeUserOneYear(String username);

    /**
     * 升级用户成超级会员(SVIP永久)
     * @return
     */
    public String upgradeSVIPUser(String username);

}
