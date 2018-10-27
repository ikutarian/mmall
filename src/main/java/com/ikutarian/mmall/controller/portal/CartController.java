package com.ikutarian.mmall.controller.portal;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 购物车模块
 */
@Controller
@RequestMapping("cart")
public class CartController {

    private CartService cartService;

    /**
     * 添加商品到购物车
     */
    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        return null;
    }
}
