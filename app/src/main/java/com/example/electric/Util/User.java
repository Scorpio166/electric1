package com.example.electric.Util;

import java.math.BigInteger;

public class User {
    private static String user_id;
    private static String login_name;//登录账号  输入
    private static String user_name;//用户昵称  userName
    private static String user_type;//用户类型  userType （00系统用户 01注册用户）
    private static String dept_id;//部门ID  deptId
    private static String email;//用户邮箱  email
    private static String phonenumber;//手机号码  phonenumber
    private static String sex;//用户性别  sex （0男 1女 2未知）
    private static String avatar;//头像路径  avatar

//    private static String severUrl = "https://app.ceravo.cn";//图片等资源获取url  //ipconfig/all
    private static String severUrl = "http://10.149.2.102:8080";//图片等资源获取url

//    private static String baseurl = "https://app.ceravo.cn:443/wechat/";//登录账号  输入
    private static String baseurl = "http://10.149.2.102:8080/wechat/";//登录账号  输入


//    private static String rememberMe = "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";//
    private static String rememberMe = "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";

    public static String getRememberMe() {
        return rememberMe;
    }

    public static String getJSESSIONID() {
        return JSESSIONID;
    }

    private static String JSESSIONID = "0dae1477-1be5-42e9-8fe8-26f97439a114";//  50648ca4-f5e8-497c-9dda-10d1bc444315

    public static String getBaseurl() {
        return baseurl;
    }

    public User() {
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        User.user_id = user_id;
    }

    public static String getLogin_name() {
        return login_name;
    }

    public static void setLogin_name(String login_name) {
        User.login_name = login_name;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        User.user_name = user_name;
    }

    public static String getUser_type() {
        return user_type;
    }

    public static void setUser_type(String user_type) {
        User.user_type = user_type;
    }

    public static String getDept_id() {
        return dept_id;
    }

    public static void setDept_id(String dept_id) {
        User.dept_id = dept_id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPhonenumber() {
        return phonenumber;
    }

    public static void setPhonenumber(String phonenumber) {
        User.phonenumber = phonenumber;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        User.sex = sex;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static void setAvatar(String avatar) {
        User.avatar = avatar;
    }

    public static String getSeverUrl() {
        return severUrl;
    }

}
