package com.example.electric.mvvm.Model;


//model层，主要用于数据处理。
//Model: 数据层，包含数据实体和对数据实体的操作
//要负责数据的提供。Model层提供业务逻辑的数据结构（比如，实体类），
//  提供数据的获取（比如，从本地数据库或者远程网络获取数据），提供数据的存储。
//同样是负责网络数据获取或者本地数据库数据获取。

import com.example.electric.mvvm.ViewModel.UserAccountViewModel;

public class UserAccountModel {
    //数据实体类
    private Account account = new Account();
    //viewmodule
    private UserAccountViewModel feeViewModule;

    public UserAccountModel(UserAccountViewModel feeViewModule){
        this.feeViewModule = feeViewModule;
    }

    public void getFee(MCallback callback){
        //网络请求操作

    }

    private String getFeeInforUrl(){
        return " ";
    }

    //网络请求回调接口
    public interface MCallback {
        void onHttpTaskSuccess();

        void onHttpTaskFailure();
    }
}
