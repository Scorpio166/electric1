package com.example.electric.Notice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;

public class NoticeInActivity extends AppCompatActivity {
    private int id;
    private int flag;
    private Switch noticeSwitch;
    @Override
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_in);
        
        Intent intent = this.getIntent();
        id = intent.getIntExtra("id", 0);
        flag = intent.getIntExtra("flag", 0);

        TextView noticeName = findViewById(R.id.noticeName);
        TextView noticeTime = findViewById(R.id.noticeTime);
        TextView noticeType = findViewById(R.id.noticeType);
        noticeSwitch = findViewById(R.id.noticeSwitch);

        //初始化
        noticeName.setText(CommonVariables.noticeList.get(id).getName());
        noticeTime.setText(CommonVariables.noticeList.get(id).getTime());
        noticeType.setText(CommonVariables.noticeList.get(id).getType());
        if(CommonVariables.noticeList.get(id).getIsDo() != 0) {
            noticeSwitch.setChecked(true);
        }//已经受理
        else {
            noticeSwitch.setChecked(false);
        }//未受理
        SetOnCheckedChangeListener1();
    }
    private void SetOnCheckedChangeListener1(){
        noticeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(CommonVariables.noticeList.get(id).getIsDo() != 0) {
                CommonVariables.noticeList.get(id).setIsDo(0);
            }
            else {//现在未处理 点击进行处理
                openDialog("","是否确认处理？");
            }
        });
    }
    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonVariables.noticeList.get(id).setIsDo(1);
                //并在noticedList中添加
                CommonVariables.noticedList.add(0, CommonVariables.noticeList.get(id));
                CommonVariables.noticedAdapter.notifyDataSetChanged();//在在noticedList删除数据的时候 记得要刷新适配器

                //在noticeList中删除
                CommonVariables.noticeList.remove(id);
                CommonVariables.noticeAdapter.notifyDataSetChanged();//在在noticeList删除数据的时候 记得要刷新适配器

            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonVariables.noticeList.get(id).setIsDo(0);
                noticeSwitch.setOnCheckedChangeListener(null);
                noticeSwitch.setChecked(false);
                SetOnCheckedChangeListener1();
            }
        }).setCancelable(false).show();
    }
}