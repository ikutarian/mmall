package com.ikutarian.mmall.controller.portal;

import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    protected ProductService productService;

    /**
     * 商品详情
     */
    @GetMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId) {
        return productService.getProductDetail(productId);
    }

    /**
     * 商品列表
     *
     * keyword
     * categoryId
     * orderBy
     */
    @GetMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "categoryId", required = false) Integer categoryId,
                               @RequestParam(value = "pageNum", defaultValue = Const.Page.DEFAULT_PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = Const.Page.DEFAULT_PAGE_SIZE) int pageSize,
                               @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return productService.getProductByKeywordAndCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
