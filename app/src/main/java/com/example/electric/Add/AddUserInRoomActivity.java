package com.example.electric.Add;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.electric.R;

public class AddUserInRoomActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_in_room);
        int familyId = this.getIntent().getExtras().getInt("position");

        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddUserInRoomActivity.this);// 自定义对话框
        builder.setMessage("暂未开通此功能！");
        builder.show();// 让弹出框显示
    }
}