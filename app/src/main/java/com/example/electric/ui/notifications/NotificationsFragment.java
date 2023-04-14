package com.example.electric.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.electric.Notice.NoticeInActivity;
import com.example.electric.Util.CommonVariables;
import com.example.electric.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private TextView warning;
    private TextView warned;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        warning = binding.warning;
        warned = binding.warned;
        if(CommonVariables.noticeList == null) warning.setText("0");
        else warning.setText("" + CommonVariables.noticeList.size());
        if(CommonVariables.noticedList == null) warned.setText("0");
        else warned.setText("" + CommonVariables.noticedList.size());

        ListView listView = binding.list;
        TextView textTip = binding.textTip;

        listView.setAdapter(CommonVariables.noticeAdapter);
        listView.setEmptyView(textTip);//设置当ListView为空的时候显示text_tip "暂无数据"
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NoticeInActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });


        ListView listedView = binding.listed;
        TextView textTip2 = binding.textTip2;
        listedView.setAdapter(CommonVariables.noticedAdapter);
//        Log.i("NotificationsFragment", "onCreateView: ");
        listedView.setEmptyView(textTip2);//设置当ListView为空的时候显示text_tip "暂无数据"
        listedView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NoticeInActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CommonVariables.noticeList == null) warning.setText("0");
        else warning.setText("" + CommonVariables.noticeList.size());
        if(CommonVariables.noticedList == null) warned.setText("0");
        else warned.setText("" + CommonVariables.noticedList.size());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}