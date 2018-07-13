/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-4-10 上午9:02
 * ********************************************************
 */
package com.app.webshell.biz;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.webshell.entity.UserInfo;
import com.zcolin.frame.app.BaseApp;

public class UserMgr {

    private static UserInfo curUserInfo;

    public static UserInfo getCurUserInfo() {
        return curUserInfo;
    }

    public static void resetUserInfo() {
        curUserInfo = null;
    }

    public static boolean isLogin() {
        return curUserInfo != null;
    }


    public static void logout() {
        removeTokenFromFile();
        resetUserInfo();
    }

    public static void login(UserInfo info) {
        saveTokenToFile(info.token);
        curUserInfo = info;
    }


    public static String getUserId() {
        return curUserInfo == null ? "" : curUserInfo.user_id;
    }

    public static String getUserName() {
        return curUserInfo == null ? "" : curUserInfo.user_name;
    }

    public static String getLoginName() {
        SharedPreferences sp = BaseApp.APP_CONTEXT.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getString("login_name", null);
    }

    public static void putLoginName(String loginName) {
        SharedPreferences sp = BaseApp.APP_CONTEXT.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        sp.edit().putString("login_name", loginName).apply();
    }

    private static void saveTokenToFile(String token) {
        SharedPreferences sp = BaseApp.APP_CONTEXT.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("login_token", token);
        editor.commit();
    }

    private static void removeTokenFromFile() {
        SharedPreferences sp = BaseApp.APP_CONTEXT.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("login_token");
        editor.commit();
    }

    public static String getTokenFromFile() {
        SharedPreferences sp = BaseApp.APP_CONTEXT.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getString("login_token", null);
    }
}
