package com.example.electric.Add;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.R;
import com.example.electric.Util.User;

import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class AddFamilyActivity extends AppCompatActivity implements View.OnClickListener {
    String name;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        findViewById(R.id.add).setOnClickListener(this);
    }

    //检查name格式
    private boolean checkName(){
        if(name == null){
            showMessage("家庭名不能为空！");//弹出提示
            return false;
        }else if(name.length() < 3 || name.length() > 10){
            showMessage("家庭名应在3至10个字符之间");//弹出提示
            return false;
        } else return true;
    }
    //检查address格式
    private boolean checkAddress(){
        if(address == null){
            showMessage("地址不能为空！");//弹出提示
            return false;
        }else if(address.length() < 2 || address.length() > 30){
            showMessage("请检查地址是否正确");//弹出提示
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View view) {
        if(checkName() && checkAddress()){//检查输入格式是否正确
            new Thread(() -> {//开启线程向服务器发送请求
                try {
                    URL url = new URL(User.getBaseurl() + "room/add");//new一个访问的url
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();//创建HttpURLConnection 实例
                    connection.setDoOutput(true);//开启输出流
                    connection.setRequestMethod("PUT");//提交数据的方式
                    connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                            "; JSESSIONID=" + User.getJSESSIONID());//登录使用的Cookie
                    connection.setConnectTimeout(8000);//连接超时
                    //使用输出流写数据
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write("roomName:" + name + "roomAddress:" + address);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    //弹出自定义对话框
    private void showMessage(String mess){// 自定义对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFamilyActivity.this);
        builder.setMessage(mess);
        builder.show();// 让弹出框显示
    }
}