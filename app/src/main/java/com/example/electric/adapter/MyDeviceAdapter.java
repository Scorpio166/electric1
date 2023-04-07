package com.example.electric.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.entity.Device;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;


public class MyDeviceAdapter  extends BaseAdapter {
    private final Context mContext;
    public ViewHolder holder;
    CountDownLatch countDownLatch;
    public MyDeviceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        return CommonVariables.deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return CommonVariables.deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device, null);
            holder = new ViewHolder();
            holder.deviceIcon = convertView.findViewById(R.id.deviceIcon);
            holder.deviceName = convertView.findViewById(R.id.deviceName);
            holder.joinTime = convertView.findViewById(R.id.joinTime);
            holder.isOnline = convertView.findViewById(R.id.isOnline);
            holder.isOpen = convertView.findViewById(R.id.isOpen);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        Device device = CommonVariables.deviceList.get(position);
//        //base64解密
//        byte[] decodedUrlByte = Base64.getDecoder().decode(device.getDeviceModelPicture());
//        String decodedUrl = new String(decodedUrlByte);
//        countDownLatch = new CountDownLatch(1);
//        getImage(User.getSeverUrl() + decodedUrl);//4/7
////        holder.deviceIcon.setImageURI(Uri.parse(User.getSeverUrl() + decodedUrl));
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.i("MyDeviceAdapter：", "url:" + User.getSeverUrl() + decodedUrl);
        holder.deviceName.setText(device.getDeviceName());
        holder.joinTime.setText(device.getActivateTime());
        holder.isOnline.setText("在线");//接口未完善
        if(device.getIsOFF()==1) {
//            holder.isOpen.setText("关闭");
            holder.isOpen.setChecked(false);
        }
        else {
//            holder.isOpen.setText("开启");
            holder.isOpen.setChecked(true);
        }

        holder.isOpen.setOnCheckedChangeListener((compoundButton, b) -> {
            if(device.getIsOFF() != 0) {
                device.setIsOFF(0);
            }
            else {
                device.setIsOFF(1);
            }
            Log.i("MyDeviceAdapter：", "开关状态:" + b);
            Log.i("MyDeviceAdapter：", "IsOff:" + device.getIsOFF());
            //进行服务器数据修改
        });
        return convertView;
    }

    public void getImage(final String src) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(input);
                    input.close();
                    holder.deviceIcon.setImageBitmap(bmp);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public static final class ViewHolder {
        public ImageView deviceIcon;
        public TextView deviceName;
        public TextView joinTime;
        public TextView isOnline;
        public Switch isOpen;
    }
}
