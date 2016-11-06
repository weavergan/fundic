package org.fund.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.fund.user.UserConstant;
import org.springframework.beans.factory.InitializingBean;

import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class JmxAgent implements InitializingBean {

    private FundMonitorMBean fundMonitorMBean;

    private final Logger log = Logger.getLogger(JmxAgent.class);

    public FundMonitorMBean getFundMonitorMBean() {
        return fundMonitorMBean;
    }

    public void setFundMonitorMBean(FundMonitorMBean fundMonitorMBean) {
        this.fundMonitorMBean = fundMonitorMBean;
    }

    public void create() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        ObjectName fundMonitorServiceName = new ObjectName("fundMonitorMBean:name=fundMonitorMBean");
        server.registerMBean(fundMonitorMBean, fundMonitorServiceName);

        ObjectName adapterName = new ObjectName("agent:name=htmladapter,port=" + UserConstant.JMX_PORT);
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(UserConstant.JMX_PORT);
        server.registerMBean(adapter, adapterName);
        AuthInfo login = new AuthInfo();
        login.setLogin("genghongwei");
        login.setPassword("geng!@#gan12");
        adapter.addUserAuthenticationInfo(login);
        adapter.start();
        log.info("JmxAgent start in " + UserConstant.JMX_PORT + " .....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        create();
    }
}
