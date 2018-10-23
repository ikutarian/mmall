package com.ikutarian.mmall.controller.backend;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manage/user")
public class UserManageController {

    @Autowired
    private UserService userService;

    @PostMapping("login.do")
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession session) {
        return userService.adminLogin(username, password, session);
    }
}
