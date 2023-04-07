package com.example.electric.My;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.electric.R;
import com.example.electric.adapter.MyDeviceAdapter;
import com.example.electric.adapter.MyFamilyAdapter;

@SuppressLint("MissingInflatedId")
public class MyDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);
        ListView listView = findViewById(R.id.liner);
        MyDeviceAdapter adapter = new MyDeviceAdapter(this);
        listView.setAdapter(adapter);
    }
}