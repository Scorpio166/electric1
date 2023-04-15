package com.example.electric.My.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;

public class ManageDeviceInRoomActivity extends AppCompatActivity {
    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_device_in_room);
        position = this.getIntent().getExtras().getInt("position");
        Log.d("ManageDeviceInRoomActivity", "onCreate: position = " + position);
        Log.d("ManageDeviceInRoomActivity", "onCreate: roomId = " + CommonVariables.roomList.get(position).getRoomId());

    }
}