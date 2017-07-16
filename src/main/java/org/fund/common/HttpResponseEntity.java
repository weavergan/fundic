package org.fund.common;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by shaobogan on 17/5/29.
 */
public class HttpResponseEntity {
    private int resStatusCode;
    private String resContentStr;
    private List<Cookie> resCookies;

    public HttpResponseEntity(int resStatusCode, String resContentStr, List<Cookie> resCookies) {
        this.resStatusCode = resStatusCode;
        this.resContentStr = resContentStr;
        this.resCookies = resCookies;
    }

    public int getResStatusCode() {
        return resStatusCode;
    }

    public void setResStatusCode(int resStatusCode) {
        this.resStatusCode = resStatusCode;
    }

    public String getResContentStr() {
        return resContentStr;
    }

    public void setResContentStr(String resContentStr) {
        this.resContentStr = resContentStr;
    }

    public List<Cookie> getResCookies() {
        return resCookies;
    }

    public void setResCookies(List<Cookie> resCookies) {
        this.resCookies = resCookies;
    }
}
