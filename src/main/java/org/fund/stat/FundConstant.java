package org.fund.stat;

public class FundConstant {

    public static final String stat_list_url = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=#{code}&page=1&per=500&sdate=#{startDate}&edate=#{endDate}&rt=#{rand}";

    public static final String stat_list_url_encoding = "utf8";
    // 天天基金
    public static final String stat_valuation_tiantian = "http://fundgz.1234567.com.cn/js/#{code}.js?rt=#{rand}";
    // 好买基金
    public static final String stat_valuation_haomai = "http://www.howbuy.com/fund/ajax/gmfund/valuation/valuationnav.htm?jjdm=#{code}";
    // 爱基金
    public static final String stat_valuation_aijijin = "http://gz.fund.10jqka.com.cn/?module=api&controller=index&action=real&datatype=jsonp&callback=jQuery18303425260568037629_1460010584121&codes=#{code}&_=#{rand}";

}
