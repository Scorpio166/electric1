package com.example.electric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.entity.Notice;

public class MyNoticeAdapter extends BaseAdapter {
    private final Context mContext;

    public MyNoticeAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        if(CommonVariables.noticeList == null) return 0;
        else return CommonVariables.noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return CommonVariables.noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notifications, null);
            viewHoder.name = convertView.findViewById(R.id.name);
            viewHoder.time = convertView.findViewById(R.id.time);
            viewHoder.type = convertView.findViewById(R.id.type);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        Notice notice = CommonVariables.noticeList.get(position);
        viewHoder.name.setText(notice.getName());
        viewHoder.time.setText(notice.getTime());
        viewHoder.type.setText(notice.getType());
        return convertView;
    }
    static class ViewHoder {
        private TextView name;
        private TextView time;
        private TextView type;
    }

}
