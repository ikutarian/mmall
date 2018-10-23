package com.ikutarian.mmall.dao;

import com.ikutarian.mmall.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取指定的username名称的个数
     */
    int getUsernameCount(String username);

    /**
     * 获取指定的email名称的个数
     */
    int getEmailCount(String email);

    /**
     * 获取指定的phone名称的个数
     */
    int getPhoneCount(String phone);

    /**
     * 根据username和password查找用户
     */
    User getUserByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);

    /**
     * 根据username后去question
     */
    String getQuestionByUsername(String username);

    /**
     * 验证密码找回问题的答案
     */
    int checkAnswer(@Param("username") String username,
                    @Param("question") String question,
                    @Param("answer") String answer);

    /**
     * 根据username更改password
     */
    int updatePasswordByUsername(@Param("username") String username,
                                 @Param("newPassword") String newPassword);

    /**
     * 校验密码
     */
    int checkPassword(@Param("userId") Integer userId,
                      @Param("password") String password);

    /**
     * 校验email是否和其他用户重名
     */
    int checkEmailOfOtherUser(@Param("email") String email,
                              @Param("myUserId") Integer myUserId);
}