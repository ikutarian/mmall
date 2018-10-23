package com.ikutarian.mmall.controller.portal;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.form.UpdateUserInfoForm;
import com.ikutarian.mmall.form.UserForm;
import com.ikutarian.mmall.service.UserService;
import com.ikutarian.mmall.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户模块
 */
@Controller
@RequestMapping("user")
public class UserController {

    // TODO 参数合法验证
    // TODO 自动化测试

    @Autowired
    private UserService userService;

    /**
     * 登陆
     */
    @PostMapping("login.do")
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession session) {
        return userService.login(username, password, session);
    }

    /**
     * 登出
     */
    @GetMapping("logout.do")
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        SessionUtils.logout(session);
        return ServerResponse.createBySuccessMsg("登出成功");
    }

    /**
     * 注册
     */
    @PostMapping("register.do")
    @ResponseBody
    public ServerResponse register(UserForm userForm) {
        return userService.register(userForm);
    }

    /**
     * 检查某个字段是否重复
     *
     * 支持的字段可以查看Const.Type的定义
     */
    @PostMapping("check_valid.do")
    @ResponseBody
    public ServerResponse checkValid(String type, String value) {
        return userService.checkValid(type, value);
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session) {
        return userService.getUserInfo(session);
    }

    /**
     * 获取找回密码问题
     */
    @PostMapping("forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username) {
        return userService.getQuestion(username);
    }

    /**
     * 验证密码提示问题答案
     */
    @PostMapping("forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码的重置密码
     */
    @PostMapping("forget_reset_password.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(String username, String newPassword, String forgetToken) {
        return userService.forgetResetPassword(username, newPassword, forgetToken);
    }

    /**
     * 登陆状态下重置密码
     */
    @PostMapping("reset_password.do")
    @ResponseBody
    public ServerResponse resetPassword(HttpSession session, String oldPassword, String newPassword) {
        return userService.resetPassword(session, oldPassword, newPassword);
    }

    /**
     * 登录状态下更新用户信息
     */
    @PostMapping("update_information.do")
    @ResponseBody
    public ServerResponse updateInformation(HttpSession session, UpdateUserInfoForm userForm) {
        return userService.updateInformation(session, userForm);
    }

    /**
     * 登录状态下获取用户信息
     */
    @GetMapping("get_information.do")
    @ResponseBody
    public ServerResponse getInformation(HttpSession session) {
        return userService.getInformation(session);
    }
}
