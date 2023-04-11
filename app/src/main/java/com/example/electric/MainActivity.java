package com.example.electric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.electric.My.MyAccountActivity;
import com.example.electric.My.MyDeviceActivity;
import com.example.electric.My.MyFamilyActivity;
import com.example.electric.My.MyMessageActivity;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.MyNoticeAdapter;
import com.example.electric.databinding.ActivityMainBinding;
import com.example.electric.entity.Notice;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_my).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //NotificationsFragment 适配器设置
        CommonVariables.noticeAdapter = new MyNoticeAdapter(this, 0);
        CommonVariables.noticeList = new ArrayList<>();
        CommonVariables.noticedAdapter = new MyNoticeAdapter(this, 1);
        CommonVariables.noticedList = new ArrayList<>();
        //初始化noticeList数据
        for (int i = 0; i < 10; i++) {
            CommonVariables.noticeList.add(new Notice("姓名" + i, "时间：" + i, "类型" + i, 0));
        }
        for (int i = 100; i < 110; i++) {
            CommonVariables.noticedList.add(new Notice("姓名" + i, "时间：" + i, "类型" + i, 1));
        }
    }

    public void onClick_my_account(View view) {
        Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
        startActivity(intent);
    }

    public void onClick_my_family(View view) {
        Intent intent = new Intent(MainActivity.this, MyFamilyActivity.class);
        startActivity(intent);
    }

    public void onClick_my_device(View view) {
        Intent intent = new Intent(MainActivity.this, MyDeviceActivity.class);
        startActivity(intent);
    }
    public void onClick_message(View view) {
        Intent intent = new Intent(MainActivity.this, MyMessageActivity.class);
        startActivity(intent);
    }
}