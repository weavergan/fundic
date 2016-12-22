package org.fund.smsutils;

import org.fund.smsutils.common.Config;
import org.fund.smsutils.common.HttpUtil;

/**
 * 邮件短信接口调用示例
 */
public class IndustrySMS {

    // url中20150822之后的部分
    private static String operation = "/industrySMS/sendSMS";

    // 参数详述请参考http://www.qingmayun.com/document.html
    private static String accountSid = Config.ACCOUNT_SID;
    private static String to = "#{mobileNum}";
    private static String smsContent = "【增投】您注册风险大师网站的验证码为#{code}，请于5分钟内正确输入验证码。";

    /**
     * 2016新接口模板短信
     */

    public static void execute() {
        String url = Config.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&smsContent=" + smsContent + "&to=" + to
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        // System.out.println("result:" + System.lineSeparator() + result);
    }

    public static String execute(String username, String code) {
        String url = Config.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&smsContent=" + smsContent.replace("#{code}", code) + "&to="
                + to.replace("#{mobileNum}", username) + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        return result;
    }

    public static void main(String[] args) {
        execute();
    }

    public static String executeWithTemplate(String username, String template) {
        String url = Config.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&smsContent=" + template + "&to="
                + to.replace("#{mobileNum}", username) + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        return result;
    }
}
