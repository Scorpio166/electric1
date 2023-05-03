package com.example.electric.mvc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electric.mvc.Controller.LoginMVCController;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginMVCActivity extends AppCompatActivity {
    TextView tvResult;
    LoginMVCController mvcController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mvcactivity);
        //绑定视图控件（仅展示部分）
        tvResult = findViewById(R.id.tv_result);
        TextView tvAccount = findViewById(R.id.tv_account);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvPwd = findViewById(R.id.tv_pwd);
        {
            TextView tvPwd1;
        }
        //绑定Controller
        mvcController = new LoginMVCController();
        //点击Button时触发的事件（仅展示登录部分）
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvcController.login(tvAccount.getText().toString(),tvPwd.getText().toString(), LoginMVCActivity.this);
            }
        });
        {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mvcController.login(tvAccount.getText().toString(),tvPwd.getText().toString(), LoginMVCActivity.this);
                }
            });
        }
    }
    public void loginSuccess(){
        tvResult.setText("登录结果：登录成功");
    }
    public void loginFail(){
        tvResult.setText("登录结果：登录失败");
    }



    public void justDo(){
        mvcController.IntentToRegister(LoginMVCActivity.this);
    }
    public void justDoo(){
        mvcController.IntentToForget(LoginMVCActivity.this);
    }
}