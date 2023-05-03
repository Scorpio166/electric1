package com.example.electric;
//记住密码

//跳转页面

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.electric.R;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.Util.User;
import com.example.electric.Util.ViewUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText et_login_name;
    private EditText et_login_password;

    private CheckBox ck_remember;
    //    private final String baseurl = "https://app.ceravo.cn:443/wechat/";
    private int login_code = -1;
    private SharedPreferences preferences;
    private Dialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        et_login_name = findViewById(R.id.et_login_name);
        et_login_password = findViewById(R.id.et_login_password);
        ck_remember = findViewById(R.id.ck_remember);
        Button btn_login = findViewById(R.id.btn_login);

        et_login_name.setOnFocusChangeListener(this);
        et_login_password.setOnFocusChangeListener(this);


        //登录
        btn_login.setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_forget).setOnClickListener(this);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();


    }
//添加Jetpack Security
    private void reload() {
        boolean isRemember = preferences.getBoolean("isRemember", false);
        if (isRemember) {
            String login_name = preferences.getString("login_name", "");
            et_login_name.setText(login_name);

            String password = preferences.getString("login_password", "");
            et_login_password.setText(password);
            ck_remember.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.btn_login)){//登陆部分验证
            String login_name = et_login_name.getText().toString();
            if (login_name.length() > 11) {
                Toast.makeText(this, "账号异常", Toast.LENGTH_SHORT).show();
                return;
            }
            //使用CountDownLatch进行线程同步
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //获取服务器访问的基础url
            String baseurl = User.getBaseurl();
            //开辟子线程进行服务器数据交互
            new Thread(() -> {
                try {
                    //使用HttpURLConnection访问网络的步骤如下：
                    // (1) 创建URL对象。
                    // (2) 调用URL对象的openConnection()方法获取HttpURLConnection对象。
                    // (3) 调用setRequestMethod()方法设置http请求的方式。
                    // (4) 通过setConnectTimeout()方法设置连接的超时时间。
                    // (5) 调用getInputStream()方法获取服务器返回的输入流。
                    // (6) 最后调用disconnect()方法关闭http连接。
                    //new一个访问的url
                    URL url = new URL(baseurl + "login?loginName=" + et_login_name.getText().toString() + "&password=" + et_login_password.getText().toString());
                    //创建HttpURLConnection 实例
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //提交数据的方式
                    connection.setRequestMethod("GET");
                    //登录使用的Cookie
                    connection.setRequestProperty("Cookie", "rememberMe=" + User.getRememberMe() +
                            "; JSESSIONID=" + User.getJSESSIONID());
                    //设置超时时间
                    connection.setConnectTimeout(8000);//连接超时
                    //读取超时
                    connection.setReadTimeout(8000);
                    //处理服务器返回的数据
                    if (connection.getResponseCode() == 200) {
                        //设置全局用户信息变量
                        User.setLogin_name(String.valueOf(et_login_name));
                        //接收服务器输入流信息
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        //拿到信息
                        String result = br.readLine();
//                    Log.i("LoginMainActivity：", "返回数据:" + result);
                        JSONObject re = JSON.parseObject(result);
                        int i = 0;
                        for(String str:re.keySet()){
                            i++;
//                        Log.i(String.valueOf(i), str + ":" + re.get(str));
                            if(str.equals("code")){
                                login_code = Integer.parseInt(Objects.requireNonNull(re.get(str)).toString());
//                            Log.i("LoginMainActivity", "login_code：" + login_code);
                            }else if(str.equals("data")){
                                JSONObject re0 = JSON.parseObject(Objects.requireNonNull(re.get(str)).toString());
                                for(String str0:re0.keySet()){
//                                Log.i("LoginMainActivity", str0 + ":" + re0.get(str0));
                                    JSONObject re1 = JSON.parseObject(Objects.requireNonNull(re0.get(str0)).toString());
                                    for(String str1:re1.keySet()){
                                        if(str1.equals("dept")){
//                                        JSONObject re2 = JSON.parseObject(Objects.requireNonNull(re1.get(str1)).toString());
//                                        for(String str2:re2.keySet()){
//                                            Log.i("LoginMainActivity", "dept:     " + str2 + ":" + re2.get(str2));
//                                        }
//                                        Log.i("LoginMainActivity", "userinfo: dept     " + str1 + ":" + re1.get(str1));
                                        }else{
                                            switch (str1){
                                                case "userId":
                                                    User.setUser_id(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getUser_id());
                                                    break;
                                                case "userName":
                                                    User.setUser_name(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getUser_name());
                                                    break;
                                                case "userType":
                                                    User.setUser_type(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getUser_type());
                                                    break;
                                                case "deptId":
                                                    User.setDept_id(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getDept_id());
                                                    break;
                                                case "email":
                                                    User.setEmail(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getEmail());
                                                    break;
                                                case "phonenumber":
                                                    User.setPhonenumber(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getPhonenumber());
                                                    break;
                                                case "sex":
                                                    User.setSex(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getSex());
                                                    break;
                                                case "avatar":
                                                    User.setAvatar(Objects.requireNonNull(re1.get(str1)).toString());
//                                                Log.i("LoginMainActivity", "User." + str1 + ": " + User.getAvatar());
                                                    break;

                                            }
//                                        Log.i("LoginMainActivity", "userinfo:     " + str1 + ":" + re1.get(str1));
                                        }
                                    }

                                }
                            }
                        }
                        is.close();
                    }
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            //主线程等待子线程完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(login_code == 0){// 提示用户登录成功
                login_code = -1;//方便下次登录
                loginSuccess();
            }else{//登录失败
                loginFail();//向用户提示登录失败
            }
        }else if(v == findViewById(R.id.btn_register)){//跳转到注册页面
            Intent intent = new Intent(LoginMainActivity.this, LoginRegisterActivity.class);
            startActivity(intent);
        }else if(v == findViewById(R.id.btn_forget)){//跳转到忘记密码页面
            Intent intent = new Intent(LoginMainActivity.this, LoginForgetActivity.class);
            startActivity(intent);
        }

//        switch (v.getId()) {
//            case R.id.btn_forget:
//                // 选择了密码方式校验，此时要跳到找回密码页面
//                if (rb_password.isChecked()) {
//                    // 以下携带手机号码跳转到找回密码页面
//                    Intent intent = new Intent(this, LoginForgetActivity.class);
//                    intent.putExtra("phone", phone);
//                    register.launch(intent);
//                } else if (rb_verifycode.isChecked()) {
//                    // 生成六位随机数字的验证码
//                    mVerifyCode = String.format("%06d", new Random().nextInt(999999));
//                    // 以下弹出提醒对话框，提示用户记住六位验证码数字
//                    AlertDialog.Builder buider = new AlertDialog.Builder(this);
//                    buider.setTitle("请记住验证码");
//                    buider.setMessage("手机号" + phone + ",本次验证码是" + mVerifyCode + ",请输入验证码");
//                    buider.setPositiveButton("好的", null);
//                    AlertDialog dialog = buider.create();
//                    dialog.show();
//                }
//                break;
//            case R.id.btn_login:
//                // 密码方式校验
//                if (rb_password.isChecked()) {
//                    if (!mPassword.equals(et_password.getText().toString())) {
//                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    // 提示用户登录成功
//                    loginSuccess();
//                } else if (rb_verifycode.isChecked()) {
//                    // 验证码方式校验
//                    if (!mVerifyCode.equals(et_password.getText().toString())) {
//                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    // 提示用户登录成功
//                    loginSuccess();
//                }
//                break;
//        }
    }

    //登录成功
    private void loginSuccess() {
//        Log.i("LoginMainActivity", "loginSuccess.登陆成功");
        if (ck_remember.isChecked()) {
            //创建SharedPreferences.Editor对象
            SharedPreferences.Editor editor = preferences.edit();
            //为Editor对象添加成员变量
            editor.putString("login_name", et_login_name.getText().toString());
            editor.putString("login_password", et_login_password.getText().toString());
            editor.putBoolean("isRemember", ck_remember.isChecked());
            //存储
            editor.apply();
            String login_name = preferences.getString("login_name", "");
            String password = preferences.getString("login_password", "");
        }
        //提示登录成功
        Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
        //跳转到新页面
        Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
    }
    private void loginFail(){
        //需要清除记住密码
//        Log.i("onclick", "登陆失败");
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        //提示登录失败
        Toast.makeText(this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        List<View> viewList = new ArrayList<>();
        viewList.add(et_login_name);
        viewList.add(et_login_password);
//        Log.i("FocusChange", String.valueOf(view));
        ViewUtil.hideSoftKeyboard(LoginMainActivity.this,viewList);
    }

}
