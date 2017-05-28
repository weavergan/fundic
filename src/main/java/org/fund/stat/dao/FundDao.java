package org.fund.stat.dao;

import org.apache.ibatis.annotations.Param;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Operation;
import org.fund.stat.entity.SMSScription;

import java.util.List;

public interface FundDao {

    // 通过用户ID获取物料
    List<Materiel> getMaterielsById(Long userId);

    // 通过用户ID和code获取物料
    Materiel getMaterielByCode(@Param("userId") Long userId, @Param("code") String code);

    // 添加物料
    void addMaterielsByCode(Materiel materiel);

    // 修改物料
    void updateMaterielsByCode(Materiel materiel);

    // 删除一个基金参数
    void deleteFundData(@Param("userId") Long userId, @Param("code") String code);

    //订阅短信提醒
    public void smsSubscription(@Param("userId") Long userId, @Param("code") String code);

    //取消订阅
    public void cancelSmsSub(@Param("userId") Long userId, @Param("code") String code);

    //获取订阅情况
    public SMSScription getSmsSubscription(@Param("userId") Long userId, @Param("code") String code);

    public List<SMSScription> getAllSmsSubscription();

    //获取用户操作
    public List<Operation> getOperationsByUserIdAndCode(@Param("userId") Integer userId, @Param("code") String code);

    public void setOperation(Operation setOperation);

    public Operation getOperationsByUserIdAndCodeAndDate(@Param("userId") Integer userId, @Param("code") String code, @Param("date") String date);

    public void updateOperation(Operation operation);

    public void addOperation(Operation operation);
}
