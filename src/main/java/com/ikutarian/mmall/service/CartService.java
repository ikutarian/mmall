package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface CartService {

    ServerResponse add(HttpSession session, Integer count, Integer productId);
}
