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
 * 密码修改请求报文
 */
public class ChangePwdApply {
    public String phone;
    public String password;
    public String verify_code;
}
