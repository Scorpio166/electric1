package com.example.electric.My.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.electric.R;
import androidx.appcompat.app.AlertDialog;
import com.example.electric.Util.User;

public class ModifyPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        TextView passPhone = findViewById(R.id.passPhone);
        passPhone.setText(User.getPhonenumber());
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setMessage("系统接口已经关闭！");
        builder.show();// 让弹出框显示
    }
}