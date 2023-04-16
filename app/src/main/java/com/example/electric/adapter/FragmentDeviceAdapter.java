package com.example.electric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.entity.Device;

import java.util.List;

public class FragmentDeviceAdapter extends BaseAdapter {
    private Context mContext;
    private List<Device> deviceList;

    public FragmentDeviceAdapter(Context mContext, List<Device> deviceList) {
        this.mContext = mContext;
        this.deviceList = deviceList;
    }

    public int getCount() {
        if(deviceList == null) return 0;
        else return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_device, null);
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
        Device device = CommonVariables.deviceList.get(position);
        holder.deviceName.setText(device.getDeviceName());
        holder.joinTime.setText(device.getActivateTime());
        holder.isOnline.setText("在线");//接口未完善
        if(device.getIsOFF()==1) {
            holder.isOpen.setText("关闭");
            holder.isOpen.setTextColor(0xFFA67D3D);
        }
        else {
            holder.isOpen.setText("开启");
            holder.isOpen.setTextColor(0xFF03DAC5);
        }
        return convertView;
    }
    public class ViewHolder {
        public ImageView deviceIcon;
        public TextView deviceName;
        public TextView joinTime;
        public TextView isOnline;
        public TextView isOpen;
    }
}
