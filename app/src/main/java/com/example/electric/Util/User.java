package com.example.electric.Util;

import androidx.databinding.BaseObservable;

import java.math.BigInteger;

public class User extends BaseObservable {
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
    private static String severUrl = "http://8.130.116.206:80";//图片等资源获取url

//    private static String baseurl = "https://app.ceravo.cn:443/wechat/";//登录账号  输入
    private static String baseurl = "http://8.130.116.206:80/wechat/";//登录账号  输入


//    private static String rememberMe = "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";//
    private static String rememberMe = "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";

    public static String getRememberMe() {
        return rememberMe;
    }

    public static String getJSESSIONID() {
        return JSESSIONID;
    }

    private static String JSESSIONID = "a1f70616-b7d4-4cfa-8a2d-a16bb25753ab";//  50648ca4-f5e8-497c-9dda-10d1bc444315

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
