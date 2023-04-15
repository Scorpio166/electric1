package com.example.electric.My.Family;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.adapter.RoomUserAdapter;
import com.example.electric.entity.RoomUser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class ManageFamily extends AppCompatActivity implements View.OnClickListener {
    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_family);
        position = this.getIntent().getExtras().getInt("id");
        TextView familyName = findViewById(R.id.familyName);
        TextView familyAddress = findViewById(R.id.familyAddress);
        familyName.setText(CommonVariables.roomList.get(position).getRoomName());
        familyAddress.setText(CommonVariables.roomList.get(position).getRoomAddress());
        familyName.setOnClickListener(this);
        familyAddress.setOnClickListener(this);
        InitRoomUserList();

        ListView listView = findViewById(R.id.liner);
        TextView textTip = findViewById(R.id.textTip);
        RoomUserAdapter adapter = new RoomUserAdapter(this, CommonVariables.roomUserList);
        listView.setAdapter(adapter);
        listView.setEmptyView(textTip);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ManageFamily.this, ManageUserInRoomActivity.class);
                intent.putExtra("userName", CommonVariables.roomUserList.get(position).getUserName());
                intent.putExtra("userId", CommonVariables.roomUserList.get(position).getUserId());
                startActivity(intent);
            }
        });

        TextView manageDevice = findViewById(R.id.manageDevice);
        manageDevice.setOnClickListener(this);

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);
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
                            CommonVariables.roomUserList = JSONObject.parseArray(roomUserStr, RoomUser.class);
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

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.manageDevice)){
            Intent intent = new Intent(ManageFamily.this, ManageDeviceInRoomActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }else if(view == findViewById(R.id.delete)){
            openDialog("是否确认删除此家庭","此操作不可恢复！");
        }else if(view == findViewById(R.id.familyName) || view == findViewById(R.id.familyAddress)){
            Intent intent = new Intent(ManageFamily.this, ModifyRoomNameActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    //弹出对话框--------------------------------------------------
    private void openDialog(String strMsg, String strTitle){
        new AlertDialog.Builder(this).setTitle(strTitle).setMessage(strMsg).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRoom(CommonVariables.roomList.get(position).getRoomId());
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }

    private void deleteRoom(int roomId){
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageFamily.this);// 自定义对话框
        builder.setMessage("暂未开通此功能！");
        builder.show();// 让弹出框显示
    }
}