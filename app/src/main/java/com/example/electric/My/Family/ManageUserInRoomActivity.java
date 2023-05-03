package com.example.electric.My.Family;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManageUserInRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private String user_name;
    private String create_time;
    private int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_in_room);
        user_name = this.getIntent().getExtras().getString("userName");
        create_time = this.getIntent().getExtras().getString("createTime");
        user_id = this.getIntent().getExtras().getInt("userId");
        TextView userName = findViewById(R.id.userName);
        TextView createTime = findViewById(R.id.createTime);
        Button deleteUser = findViewById(R.id.deleteUser);

        userName.setText(user_name);
        createTime.setText(create_time);
        deleteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        openDialog("是否确认删除此用户？","此操作不可恢复！");
    }

    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserByUserId(user_id);
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }

    private void deleteUserByUserId(int id){
        int flag = 0;//判断是否删除成功
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageUserInRoomActivity.this);// 自定义对话框
        builder.setMessage("服务器暂未开通此接口！");
        builder.show();// 让弹出框显示
    }
}