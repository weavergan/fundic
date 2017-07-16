package org.fund.autoDeal.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fund.autoDeal.service.AutoService;
import org.fund.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auto/deal")
public class AutoDealController {

    @Autowired
    private AutoService autoService;

    private static Log log = LogFactory.getLog(AutoDealController.class);

    //type 支付方式 0:活期宝 1:银行卡
    @RequestMapping(value = "/loginAction.do")
    @ResponseBody
    public Result doPerchase(@RequestParam(value="loginCS") String loginCS,
                              @RequestParam(value="fundCode") String fundCode,
                              @RequestParam(value="amount") String amount,
                              @RequestParam(value="type", defaultValue = "0") String type,
                              @RequestParam(value="dealPassword") String dealPassword) throws Exception {
        return autoService.doPerchase(null, loginCS, fundCode, amount, type, dealPassword);
    }


}
