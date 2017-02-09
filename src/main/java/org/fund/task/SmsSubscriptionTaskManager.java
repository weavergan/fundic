package org.fund.task;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fund.common.ConstantEnum;
import org.fund.exception.NoDataException;
import org.fund.smsutils.IndustrySMS;
import org.fund.stat.FundValidator;
import org.fund.stat.dao.FundDao;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Record;
import org.fund.stat.entity.SMSScription;
import org.fund.stat.service.FundService;
import org.fund.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

//每天14：50分定时发送加仓操作信息
//@Component("smsSubscriptionTaskManager")
public class SmsSubscriptionTaskManager {

    @Autowired
    private FundService fundService;
    @Autowired
    private FundDao fundDao;

    private Integer defaultGuzhiFrom = 0;//默认使用天天基金估值

    private Integer auth = ConstantEnum.AuthType.SVIP.getId();//默认都是VIP

    private static Log log = LogFactory.getLog(SmsSubscriptionTaskManager.class);

    private Date today = null;

    private static String smsContent1 = "【增投】您增投的【#{code}】，根据【天天】在【14:50】的估算【#{value}】，" +
            "今日需要加【#{price}】元，请在15:00前操作完毕。买基需谨慎！";
    private static String smsContent2 = "【增投】您增投的【#{code}】，根据【天天】在【14:50】的估算【#{value}】，" +
            "今日不需要加。买基需谨慎！";

    /**
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */

    /**
     * 每天 14:50 执行一次
     */
    //@Scheduled(cron = "0 12 16 * * *")
    public void dealSmsSubscription() {
        today = new Date();

        List<SMSScription> sMSScriptions = fundDao.getAllSmsSubscription();
        if(!CollectionUtils.isEmpty(sMSScriptions)) {
            for(SMSScription s : sMSScriptions) {
                long startTime = System.currentTimeMillis();
                String[] data = cal(s);
                sendSms(data);
                long endTime = System.currentTimeMillis();    //获取结束时间
                log.info("用户:" + s.getUsername() +  "基金代码:" + s.getCode() + "处理时间为：" + (endTime - startTime) + "ms");    //输出程序运行时间
            }
        }
    }

    private void sendSms(String[] data) {
        if(ArrayUtils.isEmpty(data)) {
            return;
        }
        String smsContent;
        log.info("开始发送短信，userId:" + data[0] + ", 基金代码code:" + data[1] + "加仓金额为：" + data[2]);
        if(Float.parseFloat(data[3]) > 0) {
            smsContent = smsContent1.replace("#{code}", data[1]).replace("#{value}", data[2]).replace("#{price}", data[3]);
        } else {
            smsContent = smsContent2.replace("#{code}", data[1]).replace("#{value}", data[2]);
        }

        String res = IndustrySMS.executeWithTemplate(data[0], smsContent);
        log.info("发送注册码结果：" + res);
    }

    private String[] cal(SMSScription s) {
        if(s == null) {
            return null;
        }
        log.info("开始计算，username:" + s.getUsername() + ", 基金代码code:" + s.getCode());
        //username,code,value,price
        String[] res = new String[4];
        res[0] = s.getUsername();
        res[1] = s.getCode();

        Materiel materiel = fundService.getMaterielByCode(s.getUserId(), s.getCode());
        List<Integer> codes = FundValidator.DateValidate(materiel, auth);
        if (CollectionUtils.isNotEmpty(codes)) {
            log.error("用户参数错误，username:" + s.getUsername() + ", 错误代码:" + codes);
            return null;
        }
        materiel.setEndDate(DateUtil.dateToString(today));//结束日期设置为今天
        try {
            List<Record> data = fundService.getListByCode(materiel, defaultGuzhiFrom, auth, false, null);
            if(!CollectionUtils.isEmpty(data)) {
                Record lastRow = data.get(data.size() - 1);
                if(lastRow.getDate().equals(DateUtil.dateToString(today))) {
                    res[2] = lastRow.getValue().toString();
                    res[3] = lastRow.getPurchasePrice().toString();
                    return res;
                }
            }
        } catch (NoDataException e) {
            log.error("暂无数据，请确认基金代码或者日期是否填写正确！username:" + s.getUsername() + ", 错误代码:" + s.getCode());
        }

        return null;
    }
}
