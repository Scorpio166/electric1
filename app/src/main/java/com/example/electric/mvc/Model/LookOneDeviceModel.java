package com.example.electric.mvc.Model;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.entity.Device;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class LookOneDeviceModel {
    private Handler mHandler;
    //Socket服务器端ip地址
    private String Socket_ip;
    //Socket服务器端端口号
    private int Socket_port;
    private int device_id;

    //启用线程函数
    public void CreateThread(){
        HandlerThread thread = new HandlerThread("MyHandlerThread");
        thread.start();//创建一个HandlerThread并启动它
        mHandler = new Handler(thread.getLooper());//使用HandlerThread的looper对象创建Handler，
        mHandler.post(getDeviceRunnable);//将线程post到Handler中
    }
    // 销毁线程函数
    public void DestroyThread(){//销毁线程
        mHandler.removeCallbacks(getDeviceRunnable);
    }
    //实现耗时操作的线程
    Runnable getDeviceRunnable = new Runnable() {
        @Override
        public void run() {
            //----------耗时的操作，开始---------------
            while(true){
                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    Socket socket = new Socket(Socket_ip, Socket_port);
                    //2.获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
                    pw.write("user_id:" + User.getUser_id() + ";device_id:" + device_id);
                    pw.flush();//发送验证信息
                    socket.shutdownOutput();//关闭输出流
                    while (true) {
                        if (socket.isConnected()) {//当Socket持续连接时持续监听服务器端返回的数据
                            InputStream is = new DataInputStream(socket.getInputStream());//获取输入缓存区数据
                            //使用JSONObject解析返回数据并处理
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            //拿到信息转换为String字符串
                            String result = br.readLine();
                            JSONObject re = JSON.parseObject(result);
                            for(String str:re.keySet()){
                                if(str.equals("data")){
                                    JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(re.get(str)).toString());
                                    String deviceStr = jsonObject.getString("device");
                                    Log.i("MyViewModel：", "DoDevice返回data:   " + deviceStr);
                                    //使用Fastjson JSON将deviceStr字符串按照Device类进行解析
                                    CommonVariables.deviceList = JSONObject.parseArray(deviceStr, Device.class);
                                }
                            }
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //----------耗时的操作，结束---------------
        }
    };

    public void dododo(){
        CreateThread();
        DestroyThread();
    }

}
