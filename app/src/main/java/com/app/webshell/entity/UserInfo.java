/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-4-10 上午9:03
 * ********************************************************
 */

/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午3:55
 * *********************************************************
 */

package com.app.webshell.entity;


import java.util.List;

/**
 * 登录/自动登录返回数据
 */
public class UserInfo {
    public String           user_id;      //cms的user_id
    public String           user_name;     //sso的 user_id
    public String           user_role;
    public List<Permission> user_permissions;
    public String           token;

    public static class Permission {
        public String roleName;
        public String userId;
    }
}
