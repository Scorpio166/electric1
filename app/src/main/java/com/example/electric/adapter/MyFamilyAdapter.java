package com.example.electric.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.electric.LoginMainActivity;
import com.example.electric.MainActivity;
import com.example.electric.My.Family.ManageFamily;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.entity.Family;

import java.util.List;


public class MyFamilyAdapter extends BaseAdapter  {//implements View.OnClickListener
    private final Context mContext;
    private Family family;
    private int id;

    public MyFamilyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        if(CommonVariables.roomList == null) return 0;
        else return CommonVariables.roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return CommonVariables.roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        id = position;
        ViewHolder holder;
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_family, null);
//            convertView.setOnClickListener(this);
            holder = new ViewHolder();

            holder.all = convertView.findViewById(R.id.all);
//            holder.iv_icon = convertView.findViewById(R.id.iv_icon);//需要getImage函数
            holder.roomName = convertView.findViewById(R.id.roomName);
            holder.isRoomOwner = convertView.findViewById(R.id.isRoomOwner);
            holder.roomAddress = convertView.findViewById(R.id.roomAddress);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 给控制设置好数据
        family = CommonVariables.roomList.get(position);
//        holder.iv_icon.setImageResource(planet.image);
        holder.roomName.setText(family.getRoomName());
        if(family.getIsRoomOwner()==1) holder.isRoomOwner.setText("房主");
        else holder.isRoomOwner.setText("成员");
        holder.roomAddress.setText(family.getRoomAddress());
        return convertView;
    }

    public static final class ViewHolder {
//        public ImageView iv_icon;
        public LinearLayout all;
        public TextView roomName;
        public TextView isRoomOwner;
        public TextView roomAddress;
    }
}
