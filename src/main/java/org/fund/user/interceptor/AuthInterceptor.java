package org.fund.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fund.common.ConstantEnum.AuthType;
import org.fund.exception.NoPermissionException;
import org.fund.user.entity.User;
import org.fund.util.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = UserHolder.getUser();
        if (user.getAuth() == AuthType.VIP_USER.getId() || user.getAuth() == AuthType.SVIP.getId()) {
            return true;
        } else {
            throw new NoPermissionException();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserHolder.remove();
    }

}
