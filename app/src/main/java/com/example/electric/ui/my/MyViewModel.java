package com.example.electric.ui.my;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.entity.Device;
import com.example.electric.entity.Family;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class MyViewModel  extends ViewModel {

//    private final MutableLiveData<String> mText;
//    private List<Device> deviceList; //CommonVariables
//    private List<Family> roomList; //CommonVariables
    CountDownLatch countDownLatch;
    String baseurl = User.getBaseurl();
    String user_id = User.getUser_id();
    public MyViewModel() {
        countDownLatch = new CountDownLatch(2);
        DoDevice();
        DoRoomGroup();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        mText = new MutableLiveData<>();
//        mText.setValue("This is my fragment");
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

    public List<Family> getFamilyList() {
        return CommonVariables.roomList;
    }

//    public LiveData<String> getText() {
//        return mText;
//    }
}
