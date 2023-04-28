package com.example.electric.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.electric.My.Device.DeviceInRoomActivity;
import com.example.electric.My.Family.ManageFamily;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.FragmentDeviceAdapter;
import com.example.electric.adapter.FragmentRoomAdapter;
import com.example.electric.adapter.MyDeviceAdapter;
import com.example.electric.databinding.FragmentDashboardBinding;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentDashboardBinding binding;
    private List<String> FragmentRoom;
    FragmentDeviceAdapter adapters;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner room = binding.room;
        //初始化list
        FragmentRoom = new ArrayList<>();
        for (int i = 0; i < CommonVariables.roomList.size(); i++) {
            FragmentRoom.add(CommonVariables.roomList.get(i).getRoomName());
        }
        FragmentRoomAdapter adapter = new FragmentRoomAdapter(getActivity(), FragmentRoom);
        room.setAdapter(adapter);
        room.setSelection(0);
//        room.setOnItemSelectedListener(this);


        //绑定页面控件
        ListView listView = binding.liner;
        TextView textTip = binding.textTip;
        //设置设备列表内元素个数为0时展示”暂无数据“的提示
        listView.setEmptyView(textTip);
        //设备标签所绑定的适配器Adapter
        adapters = new FragmentDeviceAdapter(getActivity(),CommonVariables.deviceList);
        listView.setAdapter(adapters);
        //设置列表每个item被点击的点击事件监听器
        listView.setOnItemClickListener(this);

        return root;
    }
    //item点击事件监听函数
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //带参数跳转到设备详细信息页面
        Intent intent = new Intent(getActivity(), DeviceInRoomActivity.class);
        //向新页面穿的的参数：设备编号
        intent.putExtra("id", i);
        //执行带参数跳转
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapters.notifyDataSetChanged();
    }
}