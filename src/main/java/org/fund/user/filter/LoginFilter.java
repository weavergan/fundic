package org.fund.user.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.fund.user.UserConstant;
import org.fund.user.entity.User;

public class LoginFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 1L;

    public static final String CONFIG_EXCLUDE_PATH = "excludePath";

    private List<String> excludePath;

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getContextPath();// 根路径
        String urlpath = req.getServletPath();
        HttpSession session = req.getSession(true);

        if (urlPathMatch(excludePath, urlpath)) {
            chain.doFilter(request, response);
            return;
        }
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            // resp.setHeader("Cache-Control", "no-store");
            // resp.setDateHeader("Expires", 0);
            // resp.setHeader("Prama", "no-cache");
            resp.sendRedirect(path + "/index.jsp");
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePathv = filterConfig.getInitParameter(CONFIG_EXCLUDE_PATH);
        excludePath = new ArrayList<String>();
        if (!StringUtils.isBlank(excludePathv)) {
            String[] paths = excludePathv.split(";");
            excludePath.addAll(Arrays.asList(paths));
        }
    }

    private static boolean urlPathMatch(List<String> pathes, String urlPath) {
        for (String path : pathes) {
            boolean f = false;
            if (path.contains("*")) {
                Pattern pattern = Pattern.compile(path);
                Matcher matcher = pattern.matcher(urlPath);
                f = matcher.matches();
            } else {
                f = urlPath.equals(path);
            }
            if (f) {
                return true;
            }
        }

        return false;
    }

}
