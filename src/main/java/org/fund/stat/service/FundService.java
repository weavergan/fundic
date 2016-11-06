package org.fund.stat.service;

import java.util.List;

import org.fund.exception.NoDataException;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Record;

public interface FundService {

    public List<Materiel> getMaterielsByUserId(Long userId);

    public List<Record> getListByCode(Materiel materiel, Integer guzhiFrom) throws NoDataException;

    // 保存用户参数
    public void saveData(Materiel materiel);

    // 获取估值
    public Float[] getValuation(String code, Integer guzhiFrom) throws Exception;

    // 获取一个基金参数
    public Materiel getMaterielByCode(Long userId, String code);

    // 删除该基金参数
    public void deleteFundData(Long userId, String code);

}
