package com.example.electric.mvvm.ViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.Util.ViewUtil;
import com.example.electric.databinding.ActivityUserAccountBinding;
import com.example.electric.entity.Family;
import com.example.electric.mvvm.Model.Account;
import com.example.electric.mvvm.Activity.UserAccountActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
//实现视图与数据的交互，将二者分离，实现解耦

//主要负责业务逻辑的处理。ViewModel层不涉及任何的视图操作。
// 通过官方提供的Data Binding库，View层和ViewModel层中的数据可以实现绑定，
// ViewModel层中数据的变化可以自动通知View层进行更新，
// 因此ViewModel层不需要持有View层的引用。
// ViewModel层可以看作是View层的数据模型和Presenter层的结合

//ViewModel完全不需要关心UI操作，只需要专注于数据与业务操作。
//负责存储view的数据映像以及业务逻辑

@SuppressLint("StaticFieldLeak")
public class UserAccountViewModel extends ViewModel {
    private ActivityUserAccountBinding binding;
    public Account account;//Model
    private static UserAccountActivity userAccountActivity;//view
    public UserAccountViewModel(ActivityUserAccountBinding bind, Account account) {
        this.binding = bind;
        this.account = account;
    }//构造函数
    public void setSex(String sex){
        account.setSex(sex);
    }
    public void ImageGet(){
        if (!Objects.equals(User.getAvatar(), "")) {//显示网络上的图片
            Log.i("MyActivity", "Avatar:" + User.getAvatar());
            String image_url = User.getAvatar();
            ViewUtil.ReturnBitmap(image_url);
        }
    }
    //向服务器获取图片url
    public void doPushData(){
        new Thread(() -> {
            try {
                //new一个访问的url
                URL url = new URL(User.getBaseurl() + "room/group/list?userId=" + User.getUser_id());
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
