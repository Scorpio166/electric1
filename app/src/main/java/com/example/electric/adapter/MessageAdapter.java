package com.example.electric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.entity.Message;

public class MessageAdapter extends BaseAdapter {
    private final Context mContext;
    private Message mess;
    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if(CommonVariables.messageList == null) return 0;
        else return CommonVariables.messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return CommonVariables.messageList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, null);
            holder = new MessageAdapter.ViewHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.message = convertView.findViewById(R.id.message);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        mess = CommonVariables.messageList.get(position);
        holder.title.setText(mess.getNoticeTitle());
        holder.message.setText(mess.getNoticeContent());
        return convertView;
    }

    public static final class ViewHolder {
        public TextView title;
        public TextView message;
    }
}

