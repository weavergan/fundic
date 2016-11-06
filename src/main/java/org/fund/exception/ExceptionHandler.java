package org.fund.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 全局异常处理类
 * 
 * @author 
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        logger.info("System Error Occurred. " + e.getMessage(), e);

        ModelAndView model = new ModelAndView(new MappingJacksonJsonView());
        List<String> errors = new ArrayList<String>();

        if (e instanceof NoPermissionException) {
            errors.add("抱歉，该功能开通会员之后才能使用！");
        } else {
            errors.add("系统异常");
        }

        return paramError(model, errors);
    }

    private ModelAndView paramError(ModelAndView mv, List<String> errors) {
        mv.addObject("success", false);
        mv.addObject("data", null);
        mv.addObject("errors", errors);
        return mv;
    }

}
