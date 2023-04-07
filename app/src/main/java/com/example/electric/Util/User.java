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
    private static String severUrl = "http://10.149.3.22:8080";//图片等资源获取url

//    private static String baseurl = "https://app.ceravo.cn:443/wechat/";//登录账号  输入
    private static String baseurl = "http://10.149.3.22:8080/wechat/";//登录账号  输入


//    private static String rememberMe = "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";//
    private static String rememberMe = "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";

    public static String getRememberMe() {
        return rememberMe;
    }

    public static String getJSESSIONID() {
        return JSESSIONID;
    }

    private static String JSESSIONID = "644aafdd-6a9e-473c-8572-a233333e31db";//  50648ca4-f5e8-497c-9dda-10d1bc444315

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
