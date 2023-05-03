package com.example.electric.My;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.example.electric.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.adapter.MessageAdapter;
import com.example.electric.entity.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class MyMessageActivity extends AppCompatActivity {
    private final String baseurl = User.getBaseurl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        InitMessage();
        TextView textTip = findViewById(R.id.textTip);;
        ListView listView = findViewById(R.id.list);
        listView.setEmptyView(textTip);
        MessageAdapter adapter = new MessageAdapter(this);
        listView.setAdapter(adapter);
        listView.setEmptyView(textTip);
    }
    private void InitMessage(){
        CountDownLatch countDownLatch;
        countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                URL url = new URL(baseurl + "notice/99");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                        "; JSESSIONID=" + User.getJSESSIONID());
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(8000);//连接超时
                connection.setReadTimeout(8000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String result = br.readLine();
//                    Log.i("MyMessageActivity：", "InitMessage返回数据:  " + result);
                    JSONObject re = JSON.parseObject(result);
                    for(String str:re.keySet()){
                        if(str.equals("data")){
                            JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
                            String messageStr = jsonObject.getString("notice");
                            CommonVariables.messageList = JSONObject.parseArray(messageStr, Message.class);
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