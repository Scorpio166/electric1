package com.example.electric.My.Family;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.electric.Add.AddDeviceInRoomActivity;
import com.example.electric.My.Device.DeviceInRoomActivity;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.FragmentDeviceAdapter;


public class ManageDeviceInRoomActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    private  int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_device_in_room);
        position = this.getIntent().getExtras().getInt("position");
//        Log.d("ManageDeviceInRoomActivity", "onCreate: position = " + position);
//        Log.d("ManageDeviceInRoomActivity", "onCreate: roomId = " + CommonVariables.roomList.get(position).getRoomId());
        TextView room = findViewById(R.id.room);
        ListView listView = findViewById(R.id.liner);
        TextView textTip = findViewById(R.id.textTip);
        room.setText(CommonVariables.roomList.get(position).getRoomName());
        listView.setEmptyView(textTip);
        FragmentDeviceAdapter adapters = new FragmentDeviceAdapter(this,CommonVariables.deviceList);
        listView.setAdapter(adapters);
        listView.setOnItemClickListener(this);
        findViewById(R.id.addNewButton).setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DeviceInRoomActivity.class);
        intent.putExtra("id", i);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ManageDeviceInRoomActivity.this, AddDeviceInRoomActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}