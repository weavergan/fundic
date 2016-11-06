package org.fund.user.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fund.common.ConstantEnum.AuthType;
import org.fund.common.ErrorEnum;
import org.fund.common.Result;
import org.fund.smsutils.IndustrySMS;
import org.fund.user.UserConstant;
import org.fund.user.UserValidator;
import org.fund.user.entity.User;
import org.fund.user.service.UserService;
import org.fund.util.CheckNumUtil;
import org.fund.util.MD5Util;
import org.fund.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static Log log = LogFactory.getLog(UserController.class);

    @RequestMapping(value = "/loginAction.do")
    @ResponseBody
    public Result loginAction(String username, String password, HttpSession session, HttpServletRequest req,
            HttpServletResponse resp) {
        Result result = new Result();

        List<Integer> codes = UserValidator.loginValidate(username, password);
        if (CollectionUtils.isNotEmpty(codes)) {
            result.addErrorCodes(codes);
            return result;
        }

        User user = userService.getUserByName(username);
        if (user == null) {
            result.addError(ErrorEnum.USERNAME_ERROR.getErrorMsg());
            return result;
        }
        boolean isPasswordCorrect = userService.authorityPassword(user, password);
        if (isPasswordCorrect) {
            session.setAttribute(UserConstant.CURRENT_USER, user);
            result.setData(user);
        } else {
            result.addError(ErrorEnum.PASSWORD_ERROR.getErrorMsg());
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/showCheckNum.do")
    @ResponseBody
    public void showCheckNum(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机验证码
        Random random = new Random();
        String checkNum = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            checkNum += rand;
        }
        // 将认证码存入SESSION
        session.setAttribute(UserConstant.CHECK_NUM, checkNum);
        // 生成图片
        BufferedImage image = CheckNumUtil.generateImg(checkNum);

        // 输出图象到页面
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "JPEG", sos);
        sos.close();
        // out.clear();
        // out = pageContext.pushBody();
    }

    @RequestMapping(value = "/registerAction.do")
    @ResponseBody
    public Result registerAction(String username, String regCode, String password, String password2, String checkNum,
            HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Result result = new Result();

        List<Integer> codes = UserValidator.registerValidate(username, regCode, password, password2, checkNum, session);
        if (CollectionUtils.isNotEmpty(codes)) {
            result.addErrorCodes(codes);
            return result;
        }

        User user = userService.getUserByName(username);
        if (user != null) {
            result.addError(ErrorEnum.USERNAME_EXIST.getErrorMsg());
            return result;
        }

        // 校验成功
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(MD5Util.MD5(password));
        newUser.setAuth(AuthType.NORMAL_USER.getId());
        userService.createUser(newUser);
        newUser = userService.getUserByName(username);

        session.setAttribute(UserConstant.CURRENT_USER, newUser);
        result.setData(newUser);

        return result;
    }

    @RequestMapping(value = "/resetPasswordAction.do")
    @ResponseBody
    public Result resetPasswordAction(String username, String regCode, String password, String password2,
            String checkNum, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Result result = new Result();

        List<Integer> codes = UserValidator.registerValidate(username, regCode, password, password2, checkNum, session);
        if (CollectionUtils.isNotEmpty(codes)) {
            result.addErrorCodes(codes);
            return result;
        }

        User user = userService.getUserByName(username);
        if (user == null) {
            result.addError(ErrorEnum.USERNAME_ERROR.getErrorMsg());
            return result;
        }

        // 校验成功
        user.setPassword(MD5Util.MD5(password));
        userService.resetPassword(user);
        // newUser = userService.getUserByName(username);

        // session.setAttribute(UserConstant.CURRENT_USER, newUser);
        // result.setData(newUser);

        return result;
    }

    @RequestMapping(value = "/exitLogin.do")
    @ResponseBody
    public void exitLogin(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        session.removeAttribute(UserConstant.CURRENT_USER);
        session.removeAttribute(UserConstant.REG_CODE);
        session.removeAttribute(UserConstant.REG_CODE_EXPIRED_TIME);
    }

    @RequestMapping(value = "/sendRegCode.do")
    @ResponseBody
    public Result sendRegCode(String username, HttpSession session) {
        Result result = new Result();

        List<Integer> codes = UserValidator.valiateUsername(username);
        if (CollectionUtils.isNotEmpty(codes)) {
            result.addErrorCodes(codes);
            return result;
        }

        String regCode = randCode();
        // String regCode = "2220";
        String res = IndustrySMS.execute(username, regCode);
        log.info("发送注册码结果：" + res);

        session.setAttribute(UserConstant.REG_CODE, regCode);
        Date curDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        c.add(Calendar.MINUTE, UserConstant.duringTime);
        session.setAttribute(UserConstant.REG_CODE_EXPIRED_TIME, c.getTime());

        return result;
    }

    private String randCode() {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    @RequestMapping(value = "/getAuth.do")
    @ResponseBody
    public Result getAuth() {
        Result result = new Result();
        Integer auth = UserHolder.getUser().getAuth();
        if (AuthType.checkIdeaType(auth)) {
            result.setData(auth);
        } else {
            result.addError(ErrorEnum.AUTH_ERROR.getErrorMsg());
            return result;
        }

        return result;
    }
}
