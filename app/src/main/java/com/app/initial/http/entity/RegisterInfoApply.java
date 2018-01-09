/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */
package com.app.initial.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 注册请求报文
 */
public class RegisterInfoApply implements Parcelable {

    public String phone;
    public String password;
    public String verify_code;
    public String device_id;
    public String terminal_type = "android";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(verify_code);
        dest.writeString(device_id);
        dest.writeString(terminal_type);
    }

    public static final Creator<RegisterInfoApply> CREATOR = new Creator<RegisterInfoApply>() {

        @Override
        public RegisterInfoApply createFromParcel(Parcel source) {
            RegisterInfoApply loginInfo = new RegisterInfoApply();
            loginInfo.phone = source.readString();
            loginInfo.password = source.readString();
            loginInfo.verify_code = source.readString();
            loginInfo.device_id = source.readString();
            loginInfo.terminal_type = source.readString();
            return loginInfo;
        }

        @Override
        public RegisterInfoApply[] newArray(int size) {
            return new RegisterInfoApply[size];
        }
    };
}
