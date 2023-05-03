package com.example.electric.mvvm.Activity;




//View: 界面层，对应于Activity，XML,View，负责数据显示以及用户交互
//view层，主要指Activity、Dialog、Fragment,用于视图展示。.
//负责UI界面的显示以及与用户的交汇。


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.electric.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.electric.LoginMainActivity;
import com.example.electric.My.Account.ModifyPhoneActivity;
import com.example.electric.databinding.ActivityUserAccountBinding;
import com.example.electric.mvvm.Model.Account;
import com.example.electric.mvvm.ViewModel.UserAccountViewModel;

public class UserAccountActivity extends AppCompatActivity {
    private ActivityUserAccountBinding binding;
    private Account account;
    private String[] sexArray = {"男","保密","女"};
    UserAccountViewModel userAccountViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //databinding双向绑定
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_account);
        userAccountViewModel = new UserAccountViewModel(binding, account);
        binding.setViewModel(userAccountViewModel);//binding与viewmodule实例进行绑定
        binding.informationPhoneNumber.setOnClickListener(v ->{
            Intent intent = new Intent(UserAccountActivity.this, ModifyPhoneActivity.class);
            startActivity(intent);
        });//点击跳转
        binding.chooseSex.setOnClickListener(v -> {//性别点击后弹出性别选择框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            // checkedItem默认的选中 setSingleChoiceItems设置单选按钮组
            builder.setSingleChoiceItems(sexArray, 1, (dialog, which) -> {// which是被选中的位置
                userAccountViewModel.setSex(sexArray[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            });
            builder.show();// 让弹出框显示
        });
        //退出登录
        binding.unload.setOnClickListener(v -> {
            // 先清空缓存
            SharedPreferences userSettings = getSharedPreferences("cache", MODE_PRIVATE);
            SharedPreferences.Editor editor = userSettings.edit();
            editor.clear();
            editor.apply();
            finishAffinity();
            Intent intent = new Intent(this, LoginMainActivity.class);
            startActivity(intent);
        });
        binding.informationUserName.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            builder.setMessage("尚未完善");
            builder.show();// 让弹出框显示

//            Intent intent = new Intent(MyAccountActivity.this, ModifyAddressActivity.class);
//            startActivity(intent);

        });
    }

}