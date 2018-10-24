package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;

public interface CategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String newCategoryName);

    ServerResponse getChildrenParallelCategory(Integer categoryId);

    ServerResponse getCategoryAndChildrenById(Integer categoryId);
}
