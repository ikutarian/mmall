package com.ikutarian.mmall.service.impl;

import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.common.TokenCache;
import com.ikutarian.mmall.dao.UserMapper;
import com.ikutarian.mmall.form.UpdateUserInfoForm;
import com.ikutarian.mmall.form.UserForm;
import com.ikutarian.mmall.model.User;
import com.ikutarian.mmall.service.UserService;
import com.ikutarian.mmall.util.MD5Utils;
import com.ikutarian.mmall.util.SessionUtils;
import com.ikutarian.mmall.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    // TODO effect != 0 时进行事务回滚

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse login(String username, String password, HttpSession session) {
        int resultCount = userMapper.getUsernameCount(username);
        if (resultCount == 0) {
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        User user = userMapper.getUserByUsernameAndPassword(username, MD5Utils.encode(password));
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PASSWORD_INVALID);
        }

        SessionUtils.updateUserId(session, user.getId());

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ServerResponse.createBySuccessMsgData("登陆成功", userVo);
    }

    @Override
    public ServerResponse adminLogin(String username, String password, HttpSession session) {
        int resultCount = userMapper.getUsernameCount(username);
        if (resultCount == 0) {
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        User user = userMapper.getUserByUsernameAndPassword(username, MD5Utils.encode(password));
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PASSWORD_INVALID);
        }

        // 验证角色
        if (user.getRole() != Const.Role.ADMIN) {
            return ServerResponse.createByError(ResponseCode.LOGIN_SHOULD_BE_ADMIN);
        }

        SessionUtils.updateUserId(session, user.getId());

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ServerResponse.createBySuccessMsgData("登陆成功", userVo);
    }

    @Override
    public ServerResponse register(UserForm userForm) {
        ServerResponse validResponse = checkValid(Const.Type.USERNAME, userForm.getUsername());
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = checkValid(Const.Type.EMAIL, userForm.getEmail());
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = checkValid(Const.Type.PHONE, userForm.getPhone());
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setRole(Const.Role.CUSTOMER);  // 角色为普通用户
        user.setPassword(MD5Utils.encode(userForm.getPassword()));

        int insertCount = userMapper.insertSelective(user);
        if (insertCount == 0) {
            return ServerResponse.createByError(ResponseCode.REGISTER_FAIL);
        }

        return ServerResponse.createBySuccessMsg("注册成功");
    }

    /**
     * 判断字段是否合法（也就是检测是否有同名的字段）
     * <p>
     * ServerResponse.isSuccess()为true，说明没有有重名，否则有重名
     */
    @Override
    public ServerResponse checkValid(String type, String value) {
        if (StringUtils.isBlank(type)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        int resultCount;

        if (Const.Type.USERNAME.equals(type)) {
            resultCount = userMapper.getUsernameCount(value);
        } else if (Const.Type.EMAIL.equals(type)) {
            resultCount = userMapper.getEmailCount(value);
        } else if (Const.Type.PHONE.equals(type)) {
            resultCount = userMapper.getPhoneCount(value);
        } else {
            // 找不到对应的type
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        if (resultCount > 0) {
            return ServerResponse.createByError(ResponseCode.VALUE_CONFLICT);
        } else {
            return ServerResponse.createBySuccessMsg("校验成功，不存在重名字段");
        }
    }

    @Override
    public ServerResponse getUserInfo(HttpSession session) {
        Integer userId = SessionUtils.getUserId(session);
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        return ServerResponse.createBySuccessData(user);
    }

    @Override
    public ServerResponse getQuestion(String username) {
        ServerResponse validResponse = checkValid(Const.Type.USERNAME, username);
        if (validResponse.isSuccess()) {
            // username没有出现重名，说明数据库中找不到这个username，那么就说明用户不存在
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        String question = userMapper.getQuestionByUsername(username);
        if (StringUtils.isBlank(question)) {
            return ServerResponse.createByError(ResponseCode.QUESTION_NOT_EXIST);
        } else {
            return ServerResponse.createBySuccessData(question);
        }
    }

    @Override
    public ServerResponse checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            // 说明这个用户的密码找回提示问题是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.set(Const.Token.FORGET_PASSWORD + username, forgetToken);
            return ServerResponse.createBySuccessData(forgetToken);
        }
        return ServerResponse.createByError(ResponseCode.ANSWER_NOT_CORRECT);
    }

    @Override
    public ServerResponse forgetResetPassword(String username, String newPassword, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        ServerResponse validResponse = checkValid(Const.Type.USERNAME, username);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        String savedToken = TokenCache.get(Const.Token.FORGET_PASSWORD + username);
        if (StringUtils.isBlank(savedToken)) {
            return ServerResponse.createByError(ResponseCode.INVALID_FORGET_PASSWORD_TOKEN);
        }

        if (StringUtils.equals(forgetToken, savedToken)) {
            String newPasswordMD5 = MD5Utils.encode(newPassword);
            int updateCount = userMapper.updatePasswordByUsername(username, newPasswordMD5);
            if (updateCount == 1) {
                TokenCache.remove(forgetToken); // 清除Token
                return ServerResponse.createBySuccessMsg("修改密码成功");
            } else {
                return ServerResponse.createByError(ResponseCode.RESET_PASSWORD_FAIL);
            }
        } else {
            return ServerResponse.createByError(ResponseCode.INVALID_FORGET_PASSWORD_TOKEN);
        }
    }

    @Override
    public ServerResponse resetPassword(HttpSession session, String oldPassword, String newPassword) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        int resultCount = userMapper.checkPassword(userId, MD5Utils.encode(oldPassword));
        if (resultCount == 0) {
            return ServerResponse.createByError(ResponseCode.INVALID_OLD_PASSWORD);
        }

        User user = new User();
        user.setId(userId);
        user.setPassword(MD5Utils.encode(newPassword));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount == 1) {
            return ServerResponse.createBySuccessMsg("密码更新成功");
        } else {
            return ServerResponse.createByError(ResponseCode.RESET_PASSWORD_FAIL);
        }
    }

    @Override
    public ServerResponse updateInformation(HttpSession session, UpdateUserInfoForm userForm) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        // email需要进行校验：新的email是否会和其他用户的email重名
        int resultCount = userMapper.checkEmailOfOtherUser(userForm.getEmail(), userId);
        if (resultCount > 0) {
            return ServerResponse.createByError(ResponseCode.EMAIL_CONFLICT);
        }

        User updateUser = new User();
        updateUser.setId(userId);
        BeanUtils.copyProperties(userForm, updateUser);
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount == 1) {
            session.setAttribute(Const.Session.CURRENT_USER_ID, updateUser);

            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(updateUser, userVo);
            return ServerResponse.createBySuccessMsgData("更新个人信息成功", userVo);
        } else {
            return ServerResponse.createByError(ResponseCode.UPDATE_INFORMATION_FAIL);
        }
    }

    @Override
    public ServerResponse getInformation(HttpSession session) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.USER_NOT_EXIST);
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ServerResponse.createBySuccessMsgData("获取个人信息成功", userVo);
    }

    public ServerResponse checkAdminRole(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);

        if (user != null && user.getRole() == Const.Role.ADMIN) {
            return ServerResponse.createBySuccessMsg();
        }
        return ServerResponse.createByError(ResponseCode.INVALID_ROLE);
    }
}
