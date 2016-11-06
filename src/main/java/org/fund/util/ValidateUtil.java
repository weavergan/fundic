package org.fund.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

    public static boolean isMobileNum(String num) {
        String regExp = "^0?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher matcher = p.matcher(num);
        return matcher.matches();
    }
}
