package com.example.electric.My;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.MyDeviceAdapter;
import com.example.electric.adapter.MyFamilyAdapter;

@SuppressLint("MissingInflatedId")
public class MyDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);
        ListView listView = findViewById(R.id.liner);
        TextView textTip = findViewById(R.id.textTip);
        if(CommonVariables.deviceList == null){
            listView.setEmptyView(textTip);
        }else {
            MyDeviceAdapter adapter = new MyDeviceAdapter(this);
//            Log.i("MyDeviceActivity", "onCreate: " + CommonVariables.deviceList.size());
            listView.setAdapter(adapter);
        }
    }
}