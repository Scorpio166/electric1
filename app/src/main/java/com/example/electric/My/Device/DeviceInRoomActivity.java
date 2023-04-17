package com.example.electric.My.Device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.electric.My.Family.ManageFamily;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;

import java.util.Timer;
import java.util.TimerTask;

public class DeviceInRoomActivity extends AppCompatActivity implements View.OnClickListener {
    //定时执行博客
    //https://blog.csdn.net/u012849206/article/details/50487644
    private  int id;
    private Timer timer = null;//计时器
    private TimerTask timerTask = null;

    TextView isOpenText;
    ImageView isOpenImage;
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_in_room);
        id = this.getIntent().getExtras().getInt("id");
        TextView deviceName = findViewById(R.id.deviceName);
        TextView joinTime = findViewById(R.id.joinTime);
        isOpenImage = findViewById(R.id.isOpenImage);
        isOpenText = findViewById(R.id.isOpenText);
        deviceName.setText(CommonVariables.deviceList.get(id).getDeviceName());
        joinTime.setText(CommonVariables.deviceList.get(id).getActivateTime());
        if(CommonVariables.deviceList.get(id).getIsOFF()==1) {
            isOpenText.setText("关闭");
            isOpenText.setTextColor(0xFFA67D3D);
            isOpenImage.setImageResource(R.drawable.switch_off);
        }
        else {
            isOpenText.setText("开启");
            isOpenText.setTextColor(0xFF03DAC5);
            isOpenImage.setImageResource(R.drawable.switch_on);
        }
        isOpenImage.setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
    }

    //定时函数
    private void startTime() {
        if(timer==null){
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(timerTask, 1000);//1000ms执行一次
    }


    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.isOpenImage)){
            if(CommonVariables.deviceList.get(id).getIsOFF() != 0) {
                CommonVariables.deviceList.get(id).setIsOFF(0);
                isOpenText.setText("开启");
                isOpenText.setTextColor(0xFF03DAC5);
                isOpenImage.setImageResource(R.drawable.switch_on);
            }
            else {
                CommonVariables.deviceList.get(id).setIsOFF(1);
                isOpenText.setText("关闭");
                isOpenText.setTextColor(0xFFA67D3D);
                isOpenImage.setImageResource(R.drawable.switch_off);
            }
        }else if(view == findViewById(R.id.delete)){
            openDialog("是否确认删除此家庭？","此操作不可恢复！");
        }
    }
    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDevice(id);
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }

    private void deleteDevice(int DeviceId){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeviceInRoomActivity.this);// 自定义对话框
        builder.setMessage("暂未开通此功能！");
        builder.show();// 让弹出框显示
    }
}