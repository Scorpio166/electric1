package com.example.electric.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.electric.entity.Device;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketUtil {
    //Socket服务器端ip地址
    private String Socket_ip;
    //Socket服务器端端口号
    private int Socket_port;
    //后端服务器包包名
    private String Socket_server_package_name;
    //后端服务器包服务函数名
    private String Socket_server_function_name;
    //可用的计算资源：线程核心数
    int coreSize = Runtime.getRuntime().availableProcessors() + 2;
    //最大线程数
    int maxSize = coreSize * 2 + 1;
    //开启线程池：管理线程
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(coreSize, maxSize, 3,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>(3),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    //使用Handler处理异步数据，Looper.getMainLooper()实现子线程刷新UI
    private final Handler handler = new Handler(Looper.getMainLooper());
    public void doSocket(){//外部调用执行函数
        InitData();//对Socket参数进行初始化
        poolExecutor.submit(() ->{
            try {
                Socket socket = new Socket(Socket_ip, Socket_port);//建立Socket连接
                InetAddress inetAddress = socket.getInetAddress();//获取服务器主机IP地址
                while (true) {
                    if (socket.isConnected()) {//当Socket持续连接时持续监听服务器端返回的数据
                        InputStream is = new DataInputStream(socket.getInputStream());//获取输入缓存区数据
                        {
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
                        }//使用JSONObject解析返回数据并处理
                        handler.post(() -> {
                            String hostName = inetAddress.getHostName();//获取目标主机名
                            String hostAddress = inetAddress.getHostAddress();//获取目标主机地址
                        });//将刷新数据显示的代码post给主进程，让主进程执行
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void dododo(){
        doSocket();
    }

    public void InitData(){

    }
}
