package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;

public interface UserService {

    ServerResponse login(String username, String password);
}
