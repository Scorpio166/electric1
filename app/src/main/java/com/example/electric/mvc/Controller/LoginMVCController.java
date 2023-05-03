package com.example.electric.mvc.Controller;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electric.LoginForgetActivity;
import com.example.electric.LoginRegisterActivity;
import com.example.electric.mvc.Activity.LoginMVCActivity;
import com.example.electric.mvc.Model.LoginMVCModel;

public class LoginMVCController extends AppCompatActivity {
    LoginMVCModel mvcModel;
    public LoginMVCController() {
        mvcModel = new LoginMVCModel();
    }
    //登录逻辑
    public void login(String account, String pwd, LoginMVCActivity loginActivity) {
        if (account == null || account.length()==0) {
            loginActivity.loginFail();
        }
        else if (pwd == null || pwd.length()==0) {
            loginActivity.loginFail();
        }
        else{
            mvcModel.login(account, pwd, loginActivity);
        }
    }
    //跳转到注册页面
    public void IntentToRegister(LoginMVCActivity loginActivity){
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
    }
    //跳转到忘记密码页面
    public void IntentToForget(LoginMVCActivity loginActivity){
        Intent intent = new Intent(this, LoginForgetActivity.class);
        startActivity(intent);
    }
}
