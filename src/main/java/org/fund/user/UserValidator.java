package org.fund.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.fund.common.ErrorEnum;
import org.fund.util.ValidateUtil;

public class UserValidator {

    public static List<Integer> loginValidate(String username, String password) {
        List<Integer> codes = new ArrayList<Integer>();
        codes.addAll(valiateUsername(username));
        codes.addAll(validatePassword(password));
        return codes;
    }

    public static List<Integer> registerValidate(String username, String regCode, String password, String password2,
            String checkNum, HttpSession session) {
        List<Integer> codes = new ArrayList<Integer>();
        codes.addAll(valiateUsername(username));
        codes.addAll(validatePassword(password));

        if (!password.equals(password2)) {
            codes.add(ErrorEnum.PASSWORD_NOT_SAME.getCode());
        }

        if (StringUtils.isEmpty(regCode)) {
            codes.add(ErrorEnum.REGCODE_EMPTY.getCode());
        } else {
            Date expiredTime = (Date) session.getAttribute(UserConstant.REG_CODE_EXPIRED_TIME); // 过期时间
            Date curDate = new Date();
            if (expiredTime != null && curDate.after(expiredTime)) {
                codes.add(ErrorEnum.REGCODE_EXPIRED.getCode());
            } else if (!regCode.equals(session.getAttribute(UserConstant.REG_CODE))) {
                codes.add(ErrorEnum.REGCODE_ERROR.getCode());
            }
        }

        if (StringUtils.isEmpty(checkNum)) {
            codes.add(ErrorEnum.CHECKNUM_EMPTY.getCode());
        } else if (!checkNum.equals(session.getAttribute(UserConstant.CHECK_NUM))) {
            codes.add(ErrorEnum.CHECKNUM_ERROR.getCode());
        }

        return codes;
    }

    public static List<Integer> valiateUsername(String username) {
        List<Integer> codes = new ArrayList<Integer>();
        if (StringUtils.isEmpty(username)) {
            codes.add(ErrorEnum.USERNAME_EMPTY.getCode());
        } else if (!ValidateUtil.isMobileNum(username)) {
            codes.add(ErrorEnum.USERNAME_NOT_MOBILENUM.getCode());
        }
        return codes;
    }

    public static List<Integer> validatePassword(String password) {
        List<Integer> codes = new ArrayList<Integer>();
        if (StringUtils.isEmpty(password)) {
            codes.add(ErrorEnum.PASSWORD_EMPTY.getCode());
        }
        return codes;
    }

}
