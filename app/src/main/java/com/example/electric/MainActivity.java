package com.example.electric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.electric.My.MyAccountActivity;
import com.example.electric.My.MyDeviceActivity;
import com.example.electric.My.MyFamilyActivity;
import com.example.electric.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



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
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_notifications, R.id.navigation_my)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);







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
}