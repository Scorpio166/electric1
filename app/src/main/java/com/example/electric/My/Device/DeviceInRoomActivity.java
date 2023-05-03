package com.example.electric.My.Device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.R;
import com.example.electric.Util.ChartView;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.Util.ViewUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DeviceInRoomActivity extends AppCompatActivity implements View.OnClickListener {
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
//        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_in_room);


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

        //折线图
        //x轴坐标对应的数据
        List<String> xValue = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValue = new ArrayList<>();
        //折线对应的数据
        Map<String, Integer> value = new HashMap<>();

        //折线图-月度用电
        for (int i = 0; i < 12; i++) {
            xValue.add(((i + 5) % 12 + 1) + "月");
            value.put(((i + 5) % 12 + 1) + "月", (int) (Math.random() * 35 + 10));
        }
        for (int i = 0; i < 10; i++) {
            yValue.add(i * 5);
        }
        ChartView chartView = findViewById(R.id.chartView);
        chartView.setValue(value, xValue, yValue);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.isOpenImage)){//点击开关控制设备
            openADialog("是否确认控制设备开关？", "");
        }else if(view == findViewById(R.id.delete)){
            openDialog("是否确认删除此设备？","此操作不可恢复！");
        }
    }
    private void openADialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //设备此时关闭，点击开启
                if(CommonVariables.deviceList.get(id).getIsOFF() != 0) {
                    if(returnCode(CommonVariables.deviceList.get(id).getIsOFF()).equals(0)){//控制成功
                        ViewUtil.showAToast(DeviceInRoomActivity.this, "控制成功");//弹出提示
                        CommonVariables.deviceList.get(id).setIsOFF(0);//渲染画面
                        isOpenText.setText("开启");
                        isOpenText.setTextColor(0xFF03DAC5);
                        isOpenImage.setImageResource(R.drawable.switch_on);
                    }else{//控制失败
                        ViewUtil.showAToast(DeviceInRoomActivity.this, "控制失败！请重试");//弹出提示
                    }
                }
                //设备此时开启，点击关闭
                else {
                    CommonVariables.deviceList.get(id).setIsOFF(1);
                    isOpenText.setText("关闭");
                    isOpenText.setTextColor(0xFFA67D3D);
                    isOpenImage.setImageResource(R.drawable.switch_off);
                }
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }
    private AtomicInteger returnCode(int isOff){
        AtomicInteger codes = new AtomicInteger();//设置返回数据
        new Thread(() -> {//开启线程向服务器发送请求
            try {
                URL url = new URL(User.getBaseurl() + "device/updateDevice/?deviceId=" + CommonVariables.deviceList.get(id).getDeviceId() + "&isOff=" + isOff);//new一个访问的url
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();//创建HttpURLConnection 实例
                connection.setDoOutput(true);//开启输出流
                connection.setRequestMethod("PUT");//提交数据的方式
                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                        "; JSESSIONID=" + User.getJSESSIONID());//登录使用的Cookie
                connection.setConnectTimeout(8000);//连接超时
                if (connection.getResponseCode() == 200) {
                    //接收服务器输入流信息
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //拿到信息
                    String result = br.readLine();
//                    Log.i("LoginMainActivity：", "返回数据:" + result);
                    JSONObject re = JSON.parseObject(result);
                    int i = 0;
                    for(String str:re.keySet()){
                        if(str.equals("code")){
                            if(re.get(str) == "0") codes.set(0);
                            else codes.set(1);
                        }
                    }
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return codes;
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
        ViewUtil.showAToast(DeviceInRoomActivity.this, "暂未开通此功能！");
    }
}