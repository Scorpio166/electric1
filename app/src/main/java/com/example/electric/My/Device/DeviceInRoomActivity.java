package com.example.electric.My.Device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.electric.R;

public class DeviceInRoomActivity extends AppCompatActivity {
    private  int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_in_room);
        id = this.getIntent().getExtras().getInt("id");
    }
}