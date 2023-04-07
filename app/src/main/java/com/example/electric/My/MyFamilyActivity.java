package com.example.electric.My;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.electric.MainActivity;
import com.example.electric.My.Family.ManageFamily;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.MyFamilyAdapter;
import com.example.electric.entity.Family;

import java.util.List;

@SuppressLint("MissingInflatedId")
public class MyFamilyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        ListView listView = findViewById(R.id.liner);

//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        String baseurl = User.getBaseurl();
//        String user_id = User.getUser_id();
//        new Thread(() -> {
//            try {
//                //new一个访问的url
//                URL url = new URL(baseurl + "room/group/list?userId=" + user_id);
////                Log.i("MyFamilyActivity：", "url:" + url);
//                //创建HttpURLConnection 实例
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                //提交数据的方式
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
//                        "; JSESSIONID=" + User.getJSESSIONID());
//                connection.setDoInput(true);
//                connection.setDoOutput(true);
//                connection.setUseCaches(false);
//                //设置超时时间
//                connection.setConnectTimeout(8000);//连接超时
//                //读取超时
//                connection.setReadTimeout(8000);
//
//                connection.connect();
//
//                if (connection.getResponseCode() == 200) {
//                    InputStream is = connection.getInputStream();
////                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
////                    String result = br.readLine();
//                    String result = inputStream2String(is);
//                    JSONObject re = JSON.parseObject(result);
////                    Log.i("MyFamilyActivity：", "Response:" + result);
//                    for(String str:re.keySet()){
////                        Log.i("MyFamilyActivity：", str + ":" + re.get(str));
//                        if(str.equals("data")){
////                            JSONObject re0 = JSON.parseObject(Objects.requireNonNull(re.get(str)).toString());
//                            JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
//                            String roomStr = jsonObject.getString("room");
////                            Log.i("MyFamilyActivity：",  "room:" + roomStr);
//                            roomList = JSONObject.parseArray(roomStr, Family.class);
//
////                            for(String str0:re0.keySet()){
////                                if(str0.equals("room")){
////                                    Log.i("MyFamilyActivity：", str0 + ":" + re0.get(str0));
////                                    roomList = JSONObject.parseArray(Objects.requireNonNull(re0.get(str0)).toString(), Family.class);
////                                }
////                            }
//                        }
//                    }
//
//                }
//                countDownLatch.countDown();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //添加是否有roomlist判断

//        Log.i("MyFamilyActivity：", "roomList.size: " + roomList.size());
//        Log.i("MyFamilyActivity：", "roomList[0].roomName: " + roomList.get(0).getRoomName());
        //构建一个适配器
//        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        MyFamilyAdapter adapter = new MyFamilyAdapter(this);
        listView.setAdapter(adapter);
//        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(this);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Log.d("TAG", "onItemClick: ");
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ManageFamily.class);
        intent.putExtra("id", i);
        Log.d("TAG", "onItemClick: " + i);
        startActivity(intent);
    }
}