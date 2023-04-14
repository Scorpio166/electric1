package com.example.electric.My.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.adapter.MyDeviceAdapter;
import com.example.electric.adapter.RoomUserAdapter;
import com.example.electric.entity.Device;
import com.example.electric.entity.RoomUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class ManageFamily extends AppCompatActivity {
    private  int position;
    private List<RoomUser> roomUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_family);
        position = this.getIntent().getExtras().getInt("id");
        TextView familyName = findViewById(R.id.familyName);
        TextView familyAddress = findViewById(R.id.familyAddress);
        familyName.setText(CommonVariables.roomList.get(position).getRoomName());
        familyAddress.setText(CommonVariables.roomList.get(position).getRoomAddress());
        InitRoomUserList();

        ListView listView = findViewById(R.id.liner);
        TextView textTip = findViewById(R.id.textTip);
        if(roomUserList == null){
            listView.setEmptyView(textTip);
        }else {
            RoomUserAdapter adapter = new RoomUserAdapter(this, roomUserList);
            listView.setAdapter(adapter);
        }
    }
    private void InitRoomUserList(){
        CountDownLatch countDownLatch;
        countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                //new一个访问的url
                URL url = new URL(User.getBaseurl() + "/room/user/" + CommonVariables.roomList.get(position).getRoomId() + "/" + User.getUser_id());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                        "; JSESSIONID=" + User.getJSESSIONID());
                connection.setConnectTimeout(8000);//连接超时
                connection.setReadTimeout(8000);
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String result = br.readLine();
                    JSONObject re = JSON.parseObject(result);
                    for(String str:re.keySet()){
                        if(str.equals("data")){
                            JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
                            String roomUserStr = jsonObject.getString("user");
                            Log.i("ManageFamily：", "InitRoomUserList返回data:   " + roomUserStr);
                            roomUserList = JSONObject.parseArray(roomUserStr, RoomUser.class);
                        }
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}