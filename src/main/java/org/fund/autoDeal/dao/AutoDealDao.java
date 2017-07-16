package org.fund.autoDeal.dao;

import org.apache.ibatis.annotations.Param;
import org.fund.autoDeal.entity.AutoDeal;

import java.util.List;

/**
 * Created by shaobogan on 17/6/18.
 */
public interface AutoDealDao {
    List<AutoDeal> getAllAutoDealInfo();

    AutoDeal getAutoDealInfoByUserId(@Param("userId") Integer userId);
}
