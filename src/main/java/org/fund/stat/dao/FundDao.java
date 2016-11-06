package org.fund.stat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fund.stat.entity.Materiel;

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

}
