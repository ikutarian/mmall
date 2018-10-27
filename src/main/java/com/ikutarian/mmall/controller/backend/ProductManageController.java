package com.ikutarian.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.ikutarian.mmall.common.*;
import com.ikutarian.mmall.form.ProductForm;
import com.ikutarian.mmall.service.FileService;
import com.ikutarian.mmall.service.ProductService;
import com.ikutarian.mmall.service.UserService;
import com.ikutarian.mmall.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/*
TODO
商品搜索：可以使用搜索引擎，比如Lucene、Solr

FTP服务的对接
SpringMVC文件上传
流读取Properties配置文件
POJO、BO、VO对象至之间的转换关系及解决思路
joda-time快速入门
静态块
Mybatis-PageHelper高效、准确地分页及动态排序
Mybatis对List遍历的实现方法
Mybatis对where语句动态拼装的几个版本演变
 */
@Controller
@RequestMapping("manage/product")
public class ProductManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    /**
     * 产品的新增、更新
     */
    @PostMapping("save.do")
    @ResponseBody
    public ServerResponse addProduct(HttpSession session, ProductForm productForm) {
        // TODO 可以用过滤器
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            return productService.saveOrUpdateProduct(productForm);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 商品上下架
     */
    @PostMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        // TODO 可以用过滤器，过滤掉权限验证和是否登陆
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            return productService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 商品详情
     */
    @GetMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session, Integer productId) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            return productService.manageDetail(productId);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 商品列表
     */
    @PostMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = Const.Page.DEFAULT_PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = Const.Page.DEFAULT_PAGE_SIZE) int pageSize) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            return productService.list(pageNum, pageSize);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 商品搜索
     *
     * 可根据id或者name进行搜索
     */
    @PostMapping("search.do")
    @ResponseBody
    public ServerResponse search(HttpSession session,
                                 String productName,
                                 Integer productId,
                                 @RequestParam(value = "pageNum", defaultValue = Const.Page.DEFAULT_PAGE_NUM) int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = Const.Page.DEFAULT_PAGE_SIZE) int pageSize) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            return productService.search(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 商品图片上传
     */
    @PostMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, MultipartFile file, HttpServletRequest request) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");  // webapp/upload
            String savedFileName = fileService.upload(file, path);
            if (StringUtils.isEmpty(savedFileName)) {
                return ServerResponse.createByError(ResponseCode.UPLOAD_IMG_FILE_FAIL);
            } else {
                Map<String, String> fileInfo = Maps.newHashMap();
                fileInfo.put("uri", savedFileName);
                fileInfo.put("url", Config.getImageServerHost() + savedFileName);
                return ServerResponse.createBySuccessData(fileInfo);
            }
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 富文本图片上传
     */
    @PostMapping("richtext_img_upload.do")
    @ResponseBody
    public RichTextResponse richTextImgUpload(HttpSession session, MultipartFile file,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return RichTextResponse.error(ResponseCode.NEED_LOGIN.getMessage());
        }

        if (userService.checkAdminRole(userId).isSuccess()) {
            // 本项目的富文本编辑器使用的是simditor，对图片上传的JSON返回值有要求
            // 可以看文档https://simditor.tower.im/docs/doc-config.html#anchor-upload
            String path = request.getSession().getServletContext().getRealPath("upload");  // webapp/upload
            String savedFileName = fileService.upload(file, path);
            if (StringUtils.isEmpty(savedFileName)) {
                return RichTextResponse.error(ResponseCode.UPLOAD_IMG_FILE_FAIL.getMessage());
            } else {
                return RichTextResponse.success( Config.getImageServerHost() + savedFileName);
            }
        } else {
            return RichTextResponse.error(ResponseCode.INVALID_ROLE.getMessage());
        }
    }
}
