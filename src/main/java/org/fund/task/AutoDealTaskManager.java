package org.fund.task;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fund.autoDeal.dao.AutoDealDao;
import org.fund.autoDeal.entity.AutoDeal;
import org.fund.autoDeal.service.AutoService;
import org.fund.common.ConstantEnum;
import org.fund.common.Result;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Record;
import org.fund.stat.service.FundService;
import org.fund.user.dao.UserDao;
import org.fund.user.entity.User;
import org.fund.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("autoDealTaskManager")
public class AutoDealTaskManager {

    @Autowired
    private AutoService autoservice;
    @Autowired
    private AutoDealDao autoDealDao;
    @Autowired
    private FundService fundService;
    @Autowired
    private UserDao userDao;

    private static Log logger = LogFactory.getLog(AutoDealTaskManager.class);

    private int threadCount = 5;
    private ExecutorService service;


    /**
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */

    @PostConstruct
    public void init() {
        logger.info("start to create auto deal thread, thread count:" + threadCount);
        service = Executors.newFixedThreadPool(threadCount);
    }

    @Scheduled(cron = "0 50 14 * * *")
    public void autoDealTask() {
        List<AutoDeal> autoDeals = autoDealDao.getAllAutoDealInfo();
        //TODO 改成线程池完成
        for(final AutoDeal autoDeal : autoDeals) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    doAutoDeal(autoDeal);
                }
            });
        }
    }

    private void doAutoDeal(AutoDeal autoDeal) {
        User user = userDao.getUserById(autoDeal.getUserId().longValue());
        if(user.getAuth() != ConstantEnum.AuthType.SVIP.getId()) {
            logger.error("cannot auto deal, user:" + user.getUserId() + " is not SVIP!");
            return;
        }
        Materiel materiel = fundService.getMaterielByCode(user.getUserId().longValue(), autoDeal.getCode());
        try {
            List<Record> data = fundService.getListByCode(materiel, 0, user.getAuth(), false, user.getUserId().longValue());
            if(CollectionUtils.isEmpty(data)) {
                logger.info("no data to auto deal, user:" + user.getUserId() + "fundCode:" + autoDeal.getCode());
                return;
            }
            Record todayRecord = data.get(data.size() - 1);
            Date today = new Date();
            if (!DateUtil.dateToString(today).equals(todayRecord.getDate())) {
                logger.info("today date is not exist , userId:" + user.getUserId() + "fundCode:" + autoDeal.getCode());
                return;
            }
            if(todayRecord.getPurchasePrice() <= 0F) {
                logger.info("today no need to purchase, userId:" + user.getUserId() + "fundCode:" + autoDeal.getCode());
                return;
            }
            Result result = autoservice.doPerchase(user.getUserId(), autoDeal.getLoginCS(), autoDeal.getCode(), String.valueOf(todayRecord.getPurchasePrice()), autoDeal.getType(), autoDeal.getDealPassword());
            if(result.isSuccess()) {
                logger.info("auto deal success, user:" + user.getUserId() + "fundCode:" + autoDeal.getCode());
            } else {
                logger.error("auto deal failed, user:" + user.getUserId() + "fundCode:" + autoDeal.getCode());
                for(String error : result.getErrors()) {
                    logger.error(error);
                }
            }
        } catch (Exception e) {
            logger.error("auto deal failed, user:" + user.getUserId() + "fundCode:" + autoDeal.getCode(), e);
        }
    }

}
