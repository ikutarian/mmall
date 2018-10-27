package com.ikutarian.mmall.controller.portal;

import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.form.ShippingForm;
import com.ikutarian.mmall.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 收货地址
 */
@Controller
@RequestMapping("shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    /**
     * 添加地址
     */
    @PostMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, ShippingForm shippingForm) {
        return shippingService.add(session, shippingForm);
    }

    /**
     * 删除地址
     */
    @PostMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session, Integer shippingId) {
        return shippingService.del(session, shippingId);
    }

    /**
     * 更新收货地址
     */
    @PostMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, ShippingForm shippingForm) {
        return shippingService.update(session, shippingForm);
    }

    /**
     * 查询指定id的收货地址
     */
    @GetMapping("select.do")
    @ResponseBody
    public ServerResponse getById(HttpSession session, Integer shippingId) {
        return shippingService.getById(session, shippingId);
    }

    /**
     * 列表
     */
    @GetMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = Const.Page.DEFAULT_PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = Const.Page.DEFAULT_PAGE_SIZE) int pageSize) {
        return shippingService.list(session, pageNum, pageSize);
    }
}
