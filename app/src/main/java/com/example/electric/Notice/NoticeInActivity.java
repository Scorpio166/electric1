package com.example.electric.Notice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.electric.My.Device.DeviceInRoomActivity;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.entity.Device;
import com.example.electric.entity.Notice;
import com.example.electric.ui.notifications.NotificationsFragment;

import java.util.List;

public class NoticeInActivity extends AppCompatActivity implements View.OnClickListener {
    private int id;
    private int flag;
    private Switch noticeSwitch;
    private int positionDevice = (int) (Math.random() * 5);
    @Override
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_in);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("id", 0);
        flag = intent.getIntExtra("flag", 0);

        //初始化
        if(flag == 0){
            InitData(CommonVariables.noticeList);
        }else{
            InitData(CommonVariables.noticedList);
        }
        TextView deviceName = findViewById(R.id.deviceName);
        TextView joinTime = findViewById(R.id.joinTime);
        TextView isOnline = findViewById(R.id.isOnline);

        LinearLayout deviceUrl = findViewById(R.id.deviceUrl);

        Device device = CommonVariables.deviceList.get(positionDevice);
        deviceName.setText(device.getDeviceName());
        joinTime.setText(device.getActivateTime());
        isOnline.setText("在线");//接口未完善
        deviceUrl.setOnClickListener(this);
    }
    private void InitData(List<Notice> noList){
        TextView noticeName = findViewById(R.id.noticeName);
        TextView noticeTime = findViewById(R.id.noticeTime);
        TextView noticeType = findViewById(R.id.noticeType);
        noticeSwitch = findViewById(R.id.noticeSwitch);

        noticeName.setText(noList.get(id).getName());
        noticeTime.setText(noList.get(id).getTime());
        noticeType.setText(noList.get(id).getType());
        if(noList.get(id).getIsDo() != 0) {
            noticeSwitch.setChecked(true);
            SetOnCheckedChangeListener1(noList);
        }//已经受理
        else {
            noticeSwitch.setChecked(false);
            SetOnCheckedChangeListener1(noList);
        }//未受理
    }
    private void SetOnCheckedChangeListener1(List<Notice> noList){
        noticeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(noList.get(id).getIsDo() != 0) {
                openDialog("","是否确认取消处理？", noList);
            }
            else {//现在未处理 点击进行处理
                openDialog("","是否确认处理？", noList);
            }
        });
    }
    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle, List<Notice> noList){
        if(flag == 0){
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
                    finish();
                }
            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonVariables.noticeList.get(id).setIsDo(0);
                    noticeSwitch.setOnCheckedChangeListener(null);
                    noticeSwitch.setChecked(false);
                    SetOnCheckedChangeListener1(noList);
                }
            }).setCancelable(false).show();
        }else{
            new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonVariables.noticedList.get(id).setIsDo(0);
                    //并在noticedList中添加
                    CommonVariables.noticeList.add(0, CommonVariables.noticedList.get(id));
                    CommonVariables.noticeAdapter.notifyDataSetChanged();//在在noticedList删除数据的时候 记得要刷新适配器

                    //在noticeList中删除
                    CommonVariables.noticedList.remove(id);
                    CommonVariables.noticedAdapter.notifyDataSetChanged();//在在noticeList删除数据的时候 记得要刷新适配器

                    finish();
                }
            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonVariables.noticedList.get(id).setIsDo(1);
                    noticeSwitch.setOnCheckedChangeListener(null);
                    noticeSwitch.setChecked(true);
                    SetOnCheckedChangeListener1(noList);
                }
            }).setCancelable(false).show();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(NoticeInActivity.this, DeviceInRoomActivity.class);
        intent.putExtra("id", positionDevice);
        startActivity(intent);
    }
}