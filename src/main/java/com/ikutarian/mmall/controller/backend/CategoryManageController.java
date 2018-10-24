package com.ikutarian.mmall.controller.backend;

import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.service.CategoryService;
import com.ikutarian.mmall.service.UserService;
import com.ikutarian.mmall.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manage/category")
public class CategoryManageController {

    // TODO 权限校验，引入shiro

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     *
     * 默认是添加到一级分类下
     */
    @PostMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = Const.Category.ROOT_CATEGORY_ID) Integer parentId) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        // 检测以下是否是管理员角色
        if (userService.checkAdminRole(userId).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    /**
     * 更新分类名称
     */
    @PostMapping("set_category.do")
    @ResponseBody
    public ServerResponse updateCategoryName(HttpSession session, Integer categoryId, String newCategoryName) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        // 检测以下是否是管理员角色
        if (userService.checkAdminRole(userId).isSuccess()) {
            return categoryService.updateCategoryName(categoryId, newCategoryName);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    @PostMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = Const.Category.ROOT_CATEGORY_ID) Integer categoryId) {
        // TODO 像这种重复的代码，可以用AOP
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        // 检测以下是否是管理员角色
        if (userService.checkAdminRole(userId).isSuccess()) {
            // 查询子节点的category信息，并且不递归，保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }

    @PostMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                             @RequestParam(value = "categoryId", defaultValue = Const.Category.ROOT_CATEGORY_ID) Integer categoryId) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        // 检测以下是否是管理员角色
        if (userService.checkAdminRole(userId).isSuccess()) {
            // 查询子节点的id信息和递归子节点的id
            return categoryService.getCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
        }
    }
}
