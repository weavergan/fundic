package org.fund.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.fund.user.dao.UserDao;
import org.fund.user.entity.User;
import org.fund.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("memUserTaskManager")
public class MemUserTaskManager {

    @Autowired
    private UserDao userDao;

    /**
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */

    /**
     * 定时卡点计算。每天凌晨 02:00 执行一次
     */
    @Scheduled(cron = "0 11 2 * * *")
    public void memExpiredUpdate() {
        Integer curDate = DateUtil.getDateFormat2(new Date());
        // 1. 查找过期的会员
        List<User> expiredUsers = userDao.findExpiredMemUsers(curDate);
        List<Integer> userIds = getId(expiredUsers);
        // 2. 把过期会员变成普通会员
        if (!CollectionUtils.isEmpty(userIds)) {
            userDao.updateMemToNormal(userIds);
        }
    }

    private List<Integer> getId(List<User> expiredUsers) {
        if (CollectionUtils.isEmpty(expiredUsers)) {
            return Collections.emptyList();
        }
        List<Integer> userIds = new ArrayList<Integer>();
        for (User u : expiredUsers) {
            userIds.add(u.getUserId());
        }
        return userIds;
    }
}
