package com.ikutarian.mmall.dao;

import com.ikutarian.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取username的个数
     */
    int getUsernameCount(String username);

    /**
     * 根据username和password查找用户
     */
    User getUserByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);
}