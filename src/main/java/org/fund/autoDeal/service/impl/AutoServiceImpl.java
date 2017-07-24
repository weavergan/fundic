package org.fund.autoDeal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.fund.autoDeal.service.AutoService;
import org.fund.common.HttpResponseEntity;
import org.fund.common.Result;
import org.fund.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("autoService")
public class AutoServiceImpl implements AutoService {

    private static Log log = LogFactory.getLog(AutoServiceImpl.class);


    //type 支付方式 0:活期宝 1:银行卡
    @Override
    public Result doPerchase(Integer userId, String loginCS, String fundCode, String amount, String type, String dealPassword) throws Exception {
        log.info("start to do perchase! userId:" + userId + "fundCode:" + fundCode + "amount:" + amount);
        Result result = new Result();
        Map<String, Cookie> cookies = new HashMap<>();

        String loginUrl = "https://login.1234567.com.cn/do.aspx/CheckedCS";
        String loginJsonBody = "{\"CS\": \"" + loginCS + "\"}";
//        String loginJsonBody = "{\"CS\": \"MCUyQzAlMkM0MTMwMjMxOTkwMTAxMTAwMzAlMkMyMDE1Z2FuMjA5NDY5dHQlMkMwJTJDJTJD\"}";
//        String fundCode = "210009";
//        String dealPassword = "2015gan209469tt";
//        String amount = "100";

        HttpResponseEntity loginRes = HttpUtils.sendPOST(loginUrl, loginJsonBody, null);
        if(loginRes.getResStatusCode() != 200) {
            log.error("login error!");
            result.addError("login error!");
            return result;
        }
        log.info("login seccess! userId:" + userId);
        String baseUrl = getBaseUrl(loginRes.getResContentStr());
        String requestUrl = "/fundtradepage/default2?fc=" + fundCode + "&amount=" + amount;
        fetchCookie(cookies, loginRes.getResCookies());

        String firstStepUrl = baseUrl + requestUrl;
        HttpResponseEntity firstStepRes = HttpUtils.sendGET(firstStepUrl, new ArrayList<>(cookies.values()));
        if(firstStepRes.getResStatusCode() == 302) {
            String firstStepRedirect = firstStepRes.getResContentStr();
            requestUrl = getRedirect(firstStepRedirect);
            firstStepUrl = baseUrl + requestUrl;
            firstStepRes = HttpUtils.sendGET(firstStepUrl, new ArrayList<>(cookies.values()));
        }
        if(firstStepRes.getResStatusCode() != 200) {
            log.error("get first step error!");
            result.addError("get first step error!");
            return result;
        }
        log.info("firstStep seccess! userId:" + userId);
        String firstStepHtml = firstStepRes.getResContentStr();
        Map<String, NameValuePair> firstStepParams = getFormParam(firstStepHtml);
        //支付方式：活期宝ctl00$body$rblUsableBanks，银行卡ctl00$body$rblBanks
        if(type.equals("0")) {//活期宝
            firstStepParams.remove("ctl00$body$rblBanks");
            //《客户扣款授权书》
            firstStepParams.put("ctl00$body$agreeCheckbox", new BasicNameValuePair("ctl00$body$agreeCheckbox", "0"));
        } else {
            firstStepParams.remove("ctl00$body$rblUsableBanks");
            firstStepParams.remove("ctl00$body$agreeCheckbox");
            firstStepParams.put("ctl00$body$hfPayWay", new BasicNameValuePair("ctl00$body$hfPayWay", "0"));
        }
        firstStepParams.put("ctl00$body$hfBusinType", new BasicNameValuePair("ctl00$body$hfBusinType", firstStepParams.get("ctl00$body$hfPayWay").getValue()));
        firstStepParams.put("__EVENTTARGET", new BasicNameValuePair("__EVENTTARGET", "ctl00$body$lkbOK_diatxt"));
        firstStepParams.remove("ctl00$body$btnSp1");
        String secondStepUrl = baseUrl + requestUrl;
        HttpResponseEntity secondStepRes = HttpUtils.sendPOST(secondStepUrl, new ArrayList<>(firstStepParams.values()), new ArrayList<>(cookies.values()));
        if(secondStepRes.getResStatusCode() != 200) {
            log.error("get second step error!");
            result.addError("get second step error!");
            return result;
        }
        String secondStepHtml = secondStepRes.getResContentStr();
        String secondErrorInfo = getOrderFailInfo(secondStepHtml);
        if(secondErrorInfo != null) {
            log.error("second step fail: " + secondErrorInfo);
            result.addError(secondErrorInfo);
            return result;
        }
        Map<String, NameValuePair> secondStepParams = getFormParam(secondStepHtml);
        secondStepParams.put("ctl00$body$agreeCheckbox", new BasicNameValuePair("ctl00$body$agreeCheckbox", "on"));
        //风险提示弹窗
//        if(secondStepParams.containsKey("ctl00$body$Hfdiatxt") && !StringUtils.isEmpty(secondStepParams.get("ctl00$body$Hfdiatxt").getValue())) {
//            secondStepParams.put("__EVENTTARGET", new BasicNameValuePair("__EVENTTARGET", "ctl00$body$lkbOK_diatxt"));
//            secondStepParams.put("__EVENTARGUMENT", new BasicNameValuePair("__EVENTARGUMENT", ""));
//            secondStepParams.remove("ctl00$body$btnSp1");
//
//            secondStepRes = HttpUtils.sendPOST(secondStepUrl, new ArrayList<>(secondStepParams.values()), new ArrayList<>(cookies.values()));
//            if(secondStepRes.getResStatusCode() != 200) {
//                log.error("get second step error!");
//                result.addError("get second step error!");
//                return result;
//            }
//            secondStepHtml = secondStepRes.getResContentStr();
//            secondErrorInfo = getOrderFailInfo(secondStepHtml);
//            if(secondErrorInfo != null) {
//                log.error("second step fail: " + secondErrorInfo);
//                result.addError(secondErrorInfo);
//                return result;
//            }
//        }
        log.info("secondStep seccess! userId:" + userId);

        secondStepParams = getFormParam(secondStepHtml);
        secondStepParams.put("ctl00$body$txtPaypwd", new BasicNameValuePair("ctl00$body$txtPaypwd", dealPassword));


        String thirdStepUrl = baseUrl + requestUrl;
        HttpResponseEntity thirdStepRes = HttpUtils.sendPOST(thirdStepUrl, new ArrayList<>(secondStepParams.values()), new ArrayList<>(cookies.values()));
        //正常通过流程应该返回302
        if(thirdStepRes.getResStatusCode() == 200) {
            String thirdStepHtml = thirdStepRes.getResContentStr();
            String thirdErrorInfo = getOrderFailInfo(thirdStepHtml);
            if(thirdErrorInfo != null) {
                log.error("third step fail: " + thirdErrorInfo);
                result.addError(thirdErrorInfo);
                return result;
            }
        } else if(thirdStepRes.getResStatusCode() != 302) {
            log.error("get third step error!");
            result.addError("get third step error!");
            return result;
        }
        log.info("thirdStep seccess! userId:" + userId);
        String thirdStepRedirect = thirdStepRes.getResContentStr();
        String redirectUrl = getRedirect(thirdStepRedirect);

        String submitUrl = baseUrl + redirectUrl;
        HttpResponseEntity submitRes = HttpUtils.sendGET(submitUrl, new ArrayList<>(cookies.values()));
        if(submitRes.getResStatusCode() != 200) {
            log.error("get submit error!");
            result.addError("get submit error!");
            return result;
        }
        String submitHtml = submitRes.getResContentStr();
        String submitErrorInfo = getSubmitFailInfo(submitHtml);
        if(!StringUtils.isEmpty(submitErrorInfo)) {
            log.error("submit fail:" + submitErrorInfo);
            result.addError(submitErrorInfo);
            return result;
        }
        String submitSuccessInfo = getSubmitSuccessInfo(submitHtml);
        if(!StringUtils.isEmpty(submitSuccessInfo)) {
            log.info("submit success:" + submitSuccessInfo);
            result.addMsg(submitErrorInfo);
            return result;
        }
        log.error("status unkwnow...");
        result.addError("状态未知，请登录账户确认");
        return result;
    }

    //获取baseUrl
    private String getBaseUrl(String json) {
        JSONObject doc = JSON.parseObject(json);
        JSONObject data = JSON.parseObject((String) doc.get("d"));
        String url = (String) data.get("Url");
        return url.substring(0, url.indexOf(".cn") + ".cn".length());
    }

    //最后一步提交的错误信息
    private String getSubmitFailInfo(String html) {
        Document doc = Jsoup.parse(html);
        Elements failDivs = doc.getElementsByClass("fail");
        if(failDivs != null && failDivs.size() > 0) {
            Elements results = failDivs.get(0).getElementsByClass("h1");
            Elements reasons = failDivs.get(0).getElementsByClass("h2");
            StringBuilder res = new StringBuilder();
            for(Element result : results) {
                res.append(result.text().trim() + "\n");
            }
            for(Element reason : reasons) {
                res.append(reason.text().trim() + "\n");
            }
            return res.toString();
        } else {
            return null;
        }
    }

    private String getSubmitSuccessInfo(String submitHtml) {
        Document doc = Jsoup.parse(submitHtml);
        Elements successDivs = doc.getElementsByClass("doing");
        if(successDivs != null && successDivs.size() > 0) {
            Elements results = successDivs.get(0).getElementsByClass("h1");
            Elements reasons = successDivs.get(0).getElementsByClass("h2");
            StringBuilder res = new StringBuilder();
            for(Element result : results) {
                res.append(result.text().trim() + "\n");
            }
            for(Element reason : reasons) {
                res.append(reason.text().trim() + "\n");
            }
            return res.toString();
        } else {
            return null;
        }
    }

    private String getRedirect(String html) {
        Document doc = Jsoup.parse(html);
        Elements as = doc.getElementsByTag("a");
        return as.get(0).attr("href");
    }

    //获取cookie
    private void fetchCookie(Map<String, Cookie> destCookies, List<Cookie> oriCookies) {
        for(Cookie cookie : oriCookies) {
            destCookies.put(cookie.getName(), cookie);
        }
    }

    //获取from参数
    private Map<String, NameValuePair> getFormParam(String html) {
        Map<String, NameValuePair> params = new HashMap<>();
        Document doc = Jsoup.parse(html);
        Elements inputs = doc.getElementsByTag("input");
        for (Element input : inputs) {
            //如果是单选框，取checked的value值
//            if(input.attr("type").equals("radio") && (input.attr("checked") == null || !input.attr("checked").equals("checked"))) {
//                continue;
//            }
            String name = input.attr("name");
            String value = input.attr("value");
            if(!StringUtils.isEmpty(name)) {
                params.put(name, new BasicNameValuePair(name, value));
            }
        }
        return params;
    }

    //获取错误信息
    private String getOrderFailInfo(String html) {
        Document document = Jsoup.parse(html);
        Element fail = document.getElementById("ctl00_MMsg");
        if (fail == null || StringUtils.isEmpty(fail.text().trim())) {
            return null;
        }
        return fail.text().trim();
    }
}
