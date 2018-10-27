package com.ikutarian.mmall.service.impl;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.dao.CartMapper;
import com.ikutarian.mmall.dao.ProductMapper;
import com.ikutarian.mmall.service.CartService;
import com.ikutarian.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        return null;
    }

    private CartVo getCartVoLimit(Integer userId) {
        return null;
    }
}
