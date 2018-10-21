package com.ikutarian.mmall.service.impl;

import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.dao.UserMapper;
import com.ikutarian.mmall.pojo.User;
import com.ikutarian.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse login(String username, String password) {
        int resultCount = userMapper.getUsernameCount(username);
        if (resultCount == 0) {
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        User user = userMapper.getUserByUsernameAndPassword(username, password);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PASSWORD_INVALID);
        }

        return ServerResponse.createBySuccess("登陆成功", user);
    }
}
