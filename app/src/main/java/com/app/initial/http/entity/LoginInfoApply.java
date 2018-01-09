/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.initial.http.entity;

/**
 * 登录请求报文
 */
public class LoginInfoApply {
    public String phone;
    public String password;
    public String device_id;
    public String terminal_type = "android";
}
