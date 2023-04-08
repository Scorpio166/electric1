package com.example.electric;
//记住密码

//跳转页面

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();


    }

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
        progressDialog = new Dialog(LoginMainActivity.this,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.activity_loading);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = progressDialog.findViewById(R.id.dialog_loading_tipTextView);
        msg.setText("卖力加载中");
        progressDialog.show();

        String login_name = et_login_name.getText().toString();
        if (login_name.length() > 11) {
            Toast.makeText(this, "账号异常", Toast.LENGTH_SHORT).show();
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String baseurl = User.getBaseurl();
        new Thread(() -> {
            try {
                //new一个访问的url
                URL url = new URL(baseurl + "login?loginName=" + et_login_name.getText().toString() + "&password=" + et_login_password.getText().toString());

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

                if (connection.getResponseCode() == 200) {
                    //设置全局用户信息变量
                    User.setLogin_name(String.valueOf(et_login_name));
                    //接收服务器输入流信息
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //拿到信息
                    String result = br.readLine();
                    Log.i("LoginMainActivity：", "返回数据:" + result);
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
                    connection.disconnect();
                }
                countDownLatch.countDown();
//                Log.i("LoginMainActivity", "countDownLatch:" + countDownLatch);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Log.i("LoginMainActivity", "子线程退出");
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.i("LoginMainActivity", "login_code(提示判断)：" + login_code);

        progressDialog.dismiss();

        if(login_code == 0){// 提示用户登录成功
            login_code = -1;//方便下次登录
            loginSuccess();
        }else{//登录失败
            loginFail();
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
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login_name", et_login_name.getText().toString());
            editor.putString("login_password", et_login_password.getText().toString());
            editor.putBoolean("isRemember", ck_remember.isChecked());
            editor.apply();
            String login_name = preferences.getString("login_name", "");
//            Log.i("login_name", login_name);
            String password = preferences.getString("login_password", "");
//            Log.i("password", password);
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
