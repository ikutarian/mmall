package com.ikutarian.mmall.dao;

import com.ikutarian.mmall.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> list();

    List<Product> getProductListByIdOrName(@Param("productName") String productName,
                                           @Param("productId") Integer productId);

    List<Product> getProductListByNameOrCategoryId(@Param("productName") String productName,
                                                   @Param("categoryIdList") List<Integer> categoryIdList);
}