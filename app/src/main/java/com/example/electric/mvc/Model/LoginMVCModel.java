package com.example.electric.mvc.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.mvc.Activity.LoginMVCActivity;
import com.example.electric.Util.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class LoginMVCModel {
    String login_name;
    String login_password;
    int login_code;//服务器返回的登录状态码，用来判断是否登录成功

    public void login(String account, String pwd, LoginMVCActivity loginActivity){
        if (account == null || account.length()==0) {
            loginActivity.loginFail();
        }
        if (pwd == null || pwd.length()==0) {
            loginActivity.loginFail();
        }
        if ("user123".equals(account) && "pwd123".equals(pwd)){
            //使用CountDownLatch进行线程同步
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //获取服务器访问的基础url
            String baseurl = User.getBaseurl();
            //开辟子线程进行服务器数据交互
            new Thread(() -> {
                try {
                    //new一个访问的url
                    URL url = new URL(baseurl + "login?loginName=" + login_name + "&password=" + login_password);
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
                        User.setLogin_name(login_name);
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
                    //解除对主线程的阻塞
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
            if(login_code == 0){//通知View层提示：用户登录成功
                login_code = -1;//方便下次登录
                loginActivity.loginSuccess();
            }else{//通知View层提示：登录失败
                loginActivity.loginFail();
            }
        }
    }
}
