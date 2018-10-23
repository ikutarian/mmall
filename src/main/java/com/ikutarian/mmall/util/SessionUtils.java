package com.ikutarian.mmall.util;

import com.ikutarian.mmall.common.Const;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static void updateUserId(HttpSession session, Integer userId) {
        session.setAttribute(Const.Session.CURRENT_USER_ID, userId);
    }

    public static Integer getUserId(HttpSession session) {
        return (Integer) session.getAttribute(Const.Session.CURRENT_USER_ID);
    }

    public static void logout(HttpSession session) {
        // TODO 与session.removeAttribute(Const.Session.CURRENT_USER_ID)有什么区别？
        session.invalidate();
    }
}
