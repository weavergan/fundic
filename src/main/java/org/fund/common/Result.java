package org.fund.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 *
 */
public class Result {

    public static final int SUCCESS = 1;
    public static final int GLOBAL_FAILED = 2;
    public static final int FIELD_FAILED = 3;

    /**
     * status map : success: 1 errors : 2 fieldErrors : 3
     */
    private int status = SUCCESS;//

    private boolean success = true;
    private Object data = null;
    private List<String> errors = new ArrayList<String>();// 错误信息
    private List<String> msgs = new ArrayList<String>();// 正确信息

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }

    public void addError(String errorMsg) {
        this.errors.add(errorMsg);
        this.success = false;
        this.status = GLOBAL_FAILED;
    }

    public void addErrorCodes(List<Integer> codes) {
        for (Integer code : codes) {
            this.errors.add(ErrorEnum.getErrorMsg(code));
        }
        this.success = false;
        this.status = GLOBAL_FAILED;
    }

    public void addMsg(String msg) {
        msgs.add(msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
