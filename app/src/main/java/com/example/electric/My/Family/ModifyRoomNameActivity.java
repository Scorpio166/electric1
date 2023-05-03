package com.example.electric.My.Family;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.electric.Util.CommonVariables;

public class ModifyRoomNameActivity extends AppCompatActivity implements View.OnClickListener{
    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modiy_room_name);
        position = this.getIntent().getExtras().getInt("position");
        EditText roomName = findViewById(R.id.roomName);
        Button button = findViewById(R.id.button);
        roomName.setText(CommonVariables.roomList.get(position).getRoomName());
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button)){
            openDialog("", "是否确认修改家庭信息？");
        }
    }
    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modifyRoomName();
//                finish();
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setCancelable(false).show();
    }
    private void modifyRoomName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyRoomNameActivity.this);// 自定义对话框
        builder.setMessage("暂未开通此功能！");
        builder.show();// 让弹出框显示
    }
}