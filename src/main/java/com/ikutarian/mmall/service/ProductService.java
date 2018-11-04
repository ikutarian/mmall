package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.form.ProductForm;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(ProductForm productForm);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse manageDetail(Integer productId);

    ServerResponse list(int pageNum, int pageSize);

    ServerResponse search(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse getProductDetail(Integer productId);

    ServerResponse getProductByKeywordAndCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
