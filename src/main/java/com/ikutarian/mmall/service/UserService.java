package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.form.UpdateUserInfoForm;
import com.ikutarian.mmall.form.UserForm;

import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     * 普通用户登陆
     */
    ServerResponse login(String username, String password, HttpSession session);

    /**
     * 管理员登陆
     */
    ServerResponse adminLogin(String username, String password, HttpSession session);

    /**
     * 注册
     */
    ServerResponse register(UserForm userForm);

    /**
     * 校验字段是否重复
     */
    ServerResponse checkValid(String type, String value);

    /**
     * 获取用户信息
     */
    ServerResponse getUserInfo(HttpSession session);

    /**
     * 获取密码提示问题
     */
    ServerResponse getQuestion(String username);

    /**
     * 检测密码提示问题的答案
     */
    ServerResponse checkAnswer(String username, String question, String answer);

    /**
     * 忘记密码是进行重置密码
     */
    ServerResponse forgetResetPassword(String username, String newPassword, String forgetToken);

    /**
     * 登陆状态下重置密码
     */
    ServerResponse resetPassword(HttpSession session, String oldPassword, String newPassword);

    /**
     * 登陆状态下更新用户信息
     */
    ServerResponse updateInformation(HttpSession session, UpdateUserInfoForm userForm);

    /**
     * 登陆状态下获取用户信息
     */
    ServerResponse getInformation(HttpSession session);
}
