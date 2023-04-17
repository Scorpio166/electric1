package com.example.electric.Add;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.electric.My.Device.DeviceInRoomActivity;
import com.example.electric.R;

public class AddFamilyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFamilyActivity.this);// 自定义对话框
        builder.setMessage("暂未开通此功能！");
        builder.show();// 让弹出框显示
    }
}