package com.ikutarian.mmall.controller.portal;

import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     */
    @PostMapping(value = "login.do")
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession session) {
        ServerResponse response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.Session.CURRENT_USER, response.getData());
        }
        return response;
    }
}
