package org.fund.stat.service;

import org.fund.exception.NoDataException;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Record;
import org.fund.stat.entity.SMSScription;

import java.util.List;

public interface FundService {

    public List<Materiel> getMaterielsByUserId(Long userId);

    //获取列表list
    //isActual:false 理论计算; true 实际操作
    public List<Record> getListByCode(Materiel materiel, Integer guzhiFrom, Integer auth, boolean isActual, Long userId) throws NoDataException;

    // 保存用户参数
    public void saveData(Materiel materiel);

    // 获取估值
    public Float[] getValuation(String code, Integer guzhiFrom) throws Exception;

    // 获取一个基金参数
    public Materiel getMaterielByCode(Long userId, String code);

    // 删除该基金参数
    public void deleteFundData(Long userId, String code);

    public void smsSubscription(Long userId, String code);

    public void cancelSmsSub(Long userId, String code);

    public SMSScription getSmsSubscription(Long userId, String code);
}
