package org.fund.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.fund.common.HttpResponseEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by shaobogan on 17/5/10.
 */
public class HttpUtils {

    private static Log log = LogFactory.getLog(HttpUtils.class);

    public static HttpResponseEntity sendPOST(String url, String jsonBody, List<Cookie> cookies) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String cookieStr = cookieListToString(cookies);
        if(cookieStr != null) {
            httpPost.setHeader("Cookie", cookieStr);
        }
        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();
        // Create local HTTP context
        HttpContext localContext = new BasicHttpContext();
        // Bind custom cookie store to the local context
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        if(!StringUtils.isEmpty(jsonBody)) {
            StringEntity entity = new StringEntity(jsonBody, "UTF-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }

        log.info("start to send post request, url:" + url);
        CloseableHttpResponse resp = client.execute(httpPost, localContext);
        HttpEntity he = resp.getEntity();
        String respContent = EntityUtils.toString(he, "UTF-8");
        HttpResponseEntity responseEntity = new HttpResponseEntity(resp.getStatusLine().getStatusCode(), respContent, cookieStore.getCookies());
        return responseEntity;
    }

    public static HttpResponseEntity sendPOST(String url, List<NameValuePair> params, List<Cookie> cookies) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String cookieStr = cookieListToString(cookies);
        if(cookieStr != null) {
            httpPost.setHeader("Cookie", cookieStr);
        }
        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();
        // Create local HTTP context
        HttpContext localContext = new BasicHttpContext();
        // Bind custom cookie store to the local context
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        if(!CollectionUtils.isEmpty(params)) {
            HttpEntity postParams = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(postParams);
        }

        log.info("start to send post request, url:" + url);
        CloseableHttpResponse resp = client.execute(httpPost, localContext);
        HttpEntity he = resp.getEntity();
        String respContent = EntityUtils.toString(he, "UTF-8");
        HttpResponseEntity responseEntity = new HttpResponseEntity(resp.getStatusLine().getStatusCode(), respContent, cookieStore.getCookies());
        return responseEntity;
    }

    public static String cookieListToString(List<Cookie> cookies) {
        if(CollectionUtils.isEmpty(cookies)) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        for(Cookie cookie : cookies) {
            res.append(cookie.getName() + "=" + cookie.getValue() + ";");
        }
        return res.substring(0, res.length() - 1);
    }

    public static HttpResponseEntity sendGET(String url, List<Cookie> cookies) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //最后获取申购结果的时候，url中会有'^'字符，需要转义
        String formatUrl = url.replaceAll("\\^", "%5E");
        HttpGet httpGet = new HttpGet(formatUrl);
        String cookieStr = cookieListToString(cookies);
        if(cookieStr != null) {
            httpGet.setHeader("Cookie", cookieStr);
        }
        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();
        // Create local HTTP context
        HttpContext localContext = new BasicHttpContext();
        // Bind custom cookie store to the local context
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        log.info("start to send get request, url:" + url);
        CloseableHttpResponse resp = httpClient.execute(httpGet, localContext);
        HttpEntity he = resp.getEntity();
        String respContent = EntityUtils.toString(he, "UTF-8");
        HttpResponseEntity responseEntity = new HttpResponseEntity(resp.getStatusLine().getStatusCode(), respContent, cookieStore.getCookies());
        return responseEntity;
    }

}
