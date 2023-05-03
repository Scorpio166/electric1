package com.example.electric.mvvm.Model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.electric.Util.User;

public class Account extends BaseObservable {
    private String login_name;//登录账号  输入
    private String user_name;//用户昵称  userName
    private String phonenumber;//手机号码  phonenumber
    private String sex;//用户性别  sex （0男 1女 2未知）
    public Account() {
        login_name = User.getLogin_name();
        user_name = User.getUser_name();
        phonenumber = User.getPhonenumber();
        sex = User.getSex();
    }//构造函数
    @Bindable
    public String getLogin_name() {
        return login_name;
    }
    @Bindable
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
        notifyChange();//通知数据已经更改
        modifyUserName(this.user_name);//与服务器通信，更改数据库中的信息
    }
    @Bindable
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        notifyChange();
    }
    @Bindable
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
        notifyChange();
    }

    private void modifyUserName(String userName){

    }
}
