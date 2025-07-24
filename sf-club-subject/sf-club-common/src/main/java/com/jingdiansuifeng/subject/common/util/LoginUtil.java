package com.jingdiansuifeng.subject.common.util;


import com.jingdiansuifeng.subject.common.context.LoginContextHolder;

/**
 * 用户登录util
 */
public class LoginUtil {
    public static String getLoginId(){
        return LoginContextHolder.getLoginId();
    }

}
