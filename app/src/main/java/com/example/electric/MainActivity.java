package com.example.electric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.My.Family.ManageDeviceInRoomActivity;
import com.example.electric.My.MyAccountActivity;
import com.example.electric.My.MyDeviceActivity;
import com.example.electric.My.MyFamilyActivity;
import com.example.electric.My.MyMessageActivity;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.adapter.MyNoticeAdapter;
import com.example.electric.databinding.ActivityMainBinding;
import com.example.electric.entity.Device;
import com.example.electric.entity.Family;
import com.example.electric.entity.Notice;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String baseurl = User.getBaseurl();
    String user_id = User.getUser_id();
    CountDownLatch countDownLatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_my).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //NotificationsFragment 适配器设置
        CommonVariables.noticeAdapter = new MyNoticeAdapter(this, 0);
        CommonVariables.noticeList = new ArrayList<>();
        CommonVariables.noticedAdapter = new MyNoticeAdapter(this, 1);
        CommonVariables.noticedList = new ArrayList<>();

        //初始化noticeList数据
        CommonVariables.noticeList.add(new Notice("工作时间过长", "2023/4/28T18:06", "提示", 0));
        CommonVariables.noticeList.add(new Notice("电器断开连接", "2023/4/24T01:26", "警报", 0));
        CommonVariables.noticeList.add(new Notice("电器电压不足", "2023/4/23T07:15", "警报", 0));
        CommonVariables.noticeList.add(new Notice("工作温度异常", "2023/4/22T12:34", "警报", 0));
        //初始化noticedList数据
        CommonVariables.noticedList.add(new Notice("电器电压不足", "2023/4/09T17:59", "警报",1));
        CommonVariables.noticedList.add(new Notice("电器频繁开关", "2023/3/21T15:38", "提示",1));
        CommonVariables.noticedList.add(new Notice("工作时间过长", "2023/3/01T10:55", "提示",1));
        CommonVariables.noticedList.add(new Notice("电器电压不足", "2023/2/17T07:15", "警报",1));
        CommonVariables.noticedList.add(new Notice("工作温度异常", "2023/1/23T18:19", "警报", 1));
//        for (int i = 0; i < 10; i++) {
//            CommonVariables.noticeList.add(new Notice("姓名" + i, "时间：" + i, "类型" + i, 0));
//        }
//        for (int i = 100; i < 110; i++) {
//            CommonVariables.noticedList.add(new Notice("姓名" + i, "时间：" + i, "类型" + i, 1));
//        }

        countDownLatch = new CountDownLatch(2);
        DoDevice();
        DoRoomGroup();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void DoDevice(){
        new Thread(() -> {
            try {
                //new一个访问的url
                URL url = new URL(baseurl + "device/" + user_id);
//                Log.i("MyViewModel：", "url:" + url);
                //创建HttpURLConnection 实例
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //提交数据的方式
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                        "; JSESSIONID=" + User.getJSESSIONID());
                //设置超时时间
                connection.setConnectTimeout(8000);//连接超时
                //读取超时
                connection.setReadTimeout(8000);

//                Log.d("MyViewModel", "DoDevice: " + connection.getResponseCode());
                if (connection.getResponseCode() == 200) {
                    //接收服务器输入流信息
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //拿到信息
                    String result = br.readLine();
//                    Log.i("MyViewModel：", "DoDevice返回数据:  " + result);
                    JSONObject re = JSON.parseObject(result);
                    for(String str:re.keySet()){
                        if(str.equals("data")){
                            JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
                            String deviceStr = jsonObject.getString("device");
                            Log.i("MyViewModel：", "DoDevice返回data:   " + deviceStr);
                            CommonVariables.deviceList = JSONObject.parseArray(deviceStr, Device.class);
                        }
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void DoRoomGroup(){
        new Thread(() -> {
            try {
                //new一个访问的url
                URL url = new URL(baseurl + "room/group/list?userId=" + user_id);
//                Log.i("MyViewModel：", "DoRoomGroup url:" + url);
                //创建HttpURLConnection 实例
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //提交数据的方式
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                        "; JSESSIONID=" + User.getJSESSIONID());
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                //设置超时时间
                connection.setConnectTimeout(8000);//连接超时
                //读取超时
                connection.setReadTimeout(8000);

                connection.connect();

                if (connection.getResponseCode() == 200) {
                    //接收服务器输入流信息
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //拿到信息
                    String result = br.readLine();
                    JSONObject re = JSON.parseObject(result);
//                    Log.i("MyViewModel：", "DoRoomGroup 返回数据:  " + result);
                    for(String str:re.keySet()){
                        if(str.equals("data")){
                            JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
                            String roomStr = jsonObject.getString("room");
                            Log.i("MyViewModel：", "DoRoomGroup 返回data:   " + roomStr);
                            CommonVariables.roomList = JSONObject.parseArray(roomStr, Family.class);
                        }
                    }

                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void onClick_my_account(View view) {
        Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
        startActivity(intent);
    }

    public void onClick_my_family(View view) {
        Intent intent = new Intent(MainActivity.this, MyFamilyActivity.class);
        startActivity(intent);
    }

    public void onClick_my_device(View view) {
        Intent intent = new Intent(MainActivity.this, MyDeviceActivity.class);
        startActivity(intent);
    }
    public void onClick_message(View view) {
        Intent intent = new Intent(MainActivity.this, MyMessageActivity.class);
        startActivity(intent);
    }
}