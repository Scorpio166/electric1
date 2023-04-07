package com.example.electric.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.electric.Util.PieChartView;
import com.example.electric.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}