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
import androidx.lifecycle.ViewModelProvider;

import com.example.electric.MainActivity;
import com.example.electric.Notice.NoticeInActivity;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.MyNoticeAdapter;
import com.example.electric.databinding.FragmentNotificationsBinding;
import com.example.electric.entity.Notice;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
//                CommonVariables.noticeList.remove(position);
//                CommonVariables.noticeAdapter.notifyDataSetChanged();//在list删除数据的时候 记得要刷新适配器（）
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}