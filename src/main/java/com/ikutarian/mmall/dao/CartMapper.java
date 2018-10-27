package com.ikutarian.mmall.dao;

import com.ikutarian.mmall.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart getCartByUserIdAndProductId(@Param("userId") Integer userId,
                                     @Param("productId") Integer productId);

    List<Cart> getCartByUserId(Integer userId);
}