package org.fund.stat.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.fund.common.Result;
import org.fund.exception.NoDataException;
import org.fund.stat.FundValidator;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Record;
import org.fund.stat.service.FundService;
import org.fund.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fund")
public class FundController {

    @Autowired
    private FundService fundService;

    @RequestMapping(value = "/getCodes.do")
    @ResponseBody
    public Result getUserCodes() {
        List<Materiel> data = fundService.getMaterielsByUserId(UserHolder.getUserId());
        Result result = new Result();
        if (data != null) {
            result.setData(data);
        }

        return result;
    }

    @RequestMapping(value = "/getOneCode.do")
    @ResponseBody
    public Result getOneUserCode(String code) {
        Materiel data = fundService.getMaterielByCode(UserHolder.getUserId(), code);
        Result result = new Result();
        if (data != null) {
            result.setData(data);
        }

        return result;
    }

    @RequestMapping(value = "/getCodeList.do")
    @ResponseBody
    public Result getCodeList(Materiel materiel, Integer guzhiFrom) {
        Result result = new Result();

        List<Integer> codes = FundValidator.DateValidate(materiel);
        if (CollectionUtils.isNotEmpty(codes)) {
            result.addErrorCodes(codes);
            return result;
        }

        List<Record> data;
        try {
            data = fundService.getListByCode(materiel, guzhiFrom);
        } catch (NoDataException e) {
            result.addError("暂无数据，请确认基金代码或者日期是否填写正确！");
            return result;
        }
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    @RequestMapping(value = "/getActualCodeList.do")
    @ResponseBody
    public Result getActualCodeList(Materiel materiel, Integer guzhiFrom) {
        Result result = new Result();

        // List<Integer> codes = FundValidator.DateValidate(materiel);
        // if (CollectionUtils.isNotEmpty(codes)) {
        // result.addErrorCodes(codes);
        // return result;
        // }
        //
        // List<Record> data;
        // try {
        // // data = fundService.getActualListByCode(materiel, guzhiFrom);
        // } catch (NoDataException e) {
        // result.addError("暂无数据，请确认基金代码或者日期是否填写正确！");
        // return result;
        // }
        // if (data != null) {
        // result.setData(data);
        // }
        return result;
    }

    @RequestMapping(value = "/saveData.do")
    @ResponseBody
    public Result saveData(Materiel materiel) {
        Result result = new Result();
        materiel.setUserId(UserHolder.getUserId());
        fundService.saveData(materiel);
        return result;
    }

    @RequestMapping(value = "/mem/calValuation.do")
    @ResponseBody
    public Result calValuation(String code, Integer guzhiFrom) {
        Result result = new Result();

        try {
            Float[] valuation = fundService.getValuation(code, guzhiFrom);
            result.setData(valuation);
        } catch (Exception e) {
            result.addError("获取估值失败！");
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/deleteData.do")
    @ResponseBody
    public Result deleteData(String code) {
        Result result = new Result();
        fundService.deleteFundData(UserHolder.getUserId(), code);
        return result;
    }

}
