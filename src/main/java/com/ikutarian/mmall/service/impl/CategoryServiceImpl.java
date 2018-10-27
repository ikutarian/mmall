package com.ikutarian.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.dao.CategoryMapper;
import com.ikutarian.mmall.model.Category;
import com.ikutarian.mmall.service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加商品分类
     */
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) || parentId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }


        // TODO 节点名称不能重复

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);  // 这个分类是可用的

        int insertCount = categoryMapper.insertSelective(category);
        if (insertCount == 1) {
            return ServerResponse.createBySuccessMsg("添加商品分类成功");
        } else {
            return ServerResponse.createByError(ResponseCode.ADD_CATEGORY_FAILURE);
        }
    }

    /**
     * 更新商品分类的名称
     */
    public ServerResponse updateCategoryName(Integer categoryId, String newCategoryName) {
        if (categoryId == null || StringUtils.isBlank(newCategoryName)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(newCategoryName);

        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount == 1) {
            return ServerResponse.createBySuccessMsg("更新商品分类的名称成功");
        } else {
            return ServerResponse.createByError(ResponseCode.UPDATE_CATEGORY_FAILURE);
        }
    }

    public ServerResponse getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.getCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccessData(categoryList);
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     */
    public ServerResponse getCategoryAndChildrenById(Integer categoryId) {
        HashSet<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccessData(categoryIdList);
    }

    /**
     * 递归算法，算出子节点
     */
    // TODO 不太理解这个算法
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        // 查找子节点，递归算法一点要有一个退出条件
        List<Category> categoryList = categoryMapper.getCategoryChildrenByParentId(categoryId);
        for (Category item : categoryList) {
            findChildCategory(categorySet, item.getId());
        }
        return categorySet;
    }
}
