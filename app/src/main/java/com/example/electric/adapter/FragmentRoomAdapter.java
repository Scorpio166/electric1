package com.example.electric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.electric.R;

import java.util.List;

public class FragmentRoomAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> FragmentRoom;

    public FragmentRoomAdapter(Context mContext, List<String> fragmentRoom) {
        this.mContext = mContext;
        FragmentRoom = fragmentRoom;
    }

    @Override
    public int getCount() {
        return FragmentRoom.size();
    }

    @Override
    public Object getItem(int i) {
        return FragmentRoom.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_room, null);
            viewHoder.roomName = view.findViewById(R.id.roomName);
            view.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)view.getTag();
        }
        viewHoder.roomName.setText(FragmentRoom.get(i));
        return view;
    }

    static class ViewHoder {
        private TextView roomName;
    }
}
