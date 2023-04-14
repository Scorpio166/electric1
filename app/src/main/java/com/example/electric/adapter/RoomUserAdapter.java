package com.example.electric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.entity.RoomUser;

import java.util.List;

public class RoomUserAdapter extends BaseAdapter {
    private Context mContext;
    private List<RoomUser> roomUserList;

    public RoomUserAdapter(Context mContext, List<RoomUser> roomUserList) {
        this.mContext = mContext;
        this.roomUserList = roomUserList;
    }

    @Override
    public int getCount() {
        return roomUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return roomUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if (view == null) {
            viewHoder = new ViewHoder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_room_user, null);
            viewHoder.userName = view.findViewById(R.id.userName);
            viewHoder.createTime = view.findViewById(R.id.createTime);
            view.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)view.getTag();
        }
        viewHoder.userName.setText(roomUserList.get(i).getUserName());
        viewHoder.createTime.setText(roomUserList.get(i).getCreateTime());
        return view;
    }

    static class ViewHoder {
        private TextView userName;
        private TextView createTime;
    }
}
