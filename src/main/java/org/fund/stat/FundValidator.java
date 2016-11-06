package org.fund.stat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fund.common.ConstantEnum.AuthType;
import org.fund.common.ErrorEnum;
import org.fund.stat.entity.Materiel;
import org.fund.util.DateUtil;
import org.fund.util.UserHolder;

public class FundValidator {

    public static List<Integer> DateValidate(Materiel materiel) {
        List<Integer> codes = new ArrayList<Integer>();
        Date startDate = DateUtil.stringToDate(materiel.getStartDate());
        Date endDate = DateUtil.stringToDate(materiel.getEndDate());
        if (startDate == null) {
            codes.add(ErrorEnum.STARTDATE_ERROR.getCode());
        } else if (endDate == null) {
            codes.add(ErrorEnum.ENDDATE_ERROR.getCode());
        } else if (startDate.after(endDate)) {
            codes.add(ErrorEnum.STARTDATE_AFTER_ENDDATE_ERROR.getCode());
        }

        // 非会员用户无法选择当天
        String today = DateUtil.dateToString(new Date());
        try {
            if (UserHolder.getUser().getAuth() == AuthType.NORMAL_USER.getId()
                    && !DateUtil.before(materiel.getEndDate(), today)) {
                codes.add(ErrorEnum.NOT_MEM_CANNOT_CHOOSE_TODAY.getCode());
            }
        } catch (ParseException e) {}

        return codes;
    }

}
