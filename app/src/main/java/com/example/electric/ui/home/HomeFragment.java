package com.example.electric.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.electric.Util.ChartView;
import com.example.electric.Util.PieChartView;
import com.example.electric.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//以下图片每次切换页面都会刷新
        //饼图
        List<Integer> pieChatDatas=new ArrayList<>();
        pieChatDatas.add(35);
        pieChatDatas.add(53);
        pieChatDatas.add(30);

        List<String> pieChatItems=new ArrayList<>();
        pieChatItems.add("照明");
        pieChatItems.add("厨房");
        pieChatItems.add("其它");

        PieChartView pie_Chart= binding.pieChart;
        pie_Chart.setItemList(pieChatItems);
        pie_Chart.setData(pieChatDatas,"度");

        //折线图
        //x轴坐标对应的数据
        List<String> xValue = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValue = new ArrayList<>();
        //折线对应的数据
        Map<String, Integer> value = new HashMap<>();

        //折线图-月度用电
        for (int i = 0; i < 12; i++) {
            xValue.add(((i + 5) % 12 + 1) + "月");
            value.put(((i + 5) % 12 + 1) + "月", (int) (Math.random() * 141 + 60));//60--221
        }
        for (int i = 0; i < 6; i++) {
            yValue.add(i * 45);
        }
        ChartView chartView = binding.chartView;
        chartView.setValue(value, xValue, yValue);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}