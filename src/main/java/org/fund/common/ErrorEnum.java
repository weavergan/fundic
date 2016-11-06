package org.fund.common;

public enum ErrorEnum {

    // 1* 登录错误
    USERNAME_ERROR(101, "用户名不存在！"), PASSWORD_ERROR(102, "密码错误！"),

    // 2* 注册错误
    USERNAME_EMPTY(201, "请输入手机号！"), USERNAME_NOT_MOBILENUM(202, "手机号格式不正确！"), PASSWORD_EMPTY(203, "请输入密码！"), PASSWORD_NOT_SAME(
            204, "两次密码不一致！"), CHECKNUM_EMPTY(205, "请输入验证码！"), CHECKNUM_ERROR(206, "验证码错误！"), REGCODE_EMPTY(207,
            "请输入注册码！"), REGCODE_EXPIRED(208, "注册码已过期，请重新发送！"), REGCODE_ERROR(209, "注册码错误！"), USERNAME_EXIST(210,
            "该用户名已存在！"),

    // 3* 统计错误
    STARTDATE_ERROR(301, "起始日期填写有误！"), ENDDATE_ERROR(302, "结束日期填写有误！"), STARTDATE_AFTER_ENDDATE_ERROR(303,
            "结束日期必须在起始日期之后！"), NOT_MEM_CANNOT_CHOOSE_TODAY(304, "非会员用户无法使用当日计算结果，请重新选择日期或者升级会员！"),

    // 4* 权限错误
    AUTH_ERROR(401, "用户权限异常，请重新登录！");

    private int code;
    private String errorMsg;

    private ErrorEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static String getErrorMsg(int code) {
        for (ErrorEnum le : ErrorEnum.values()) {
            if (le.getCode() == code) {
                return le.errorMsg;
            }
        }
        return null;
    }
}
