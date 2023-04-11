package com.example.electric.My.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.electric.R;
import com.example.electric.Util.CommonVariables;

public class ManageFamily extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_family);
        int i = this.getIntent().getExtras().getInt("id");
//        Log.d("ManageFamily", String.valueOf(CommonVariables.roomList.get(i).getRoomName()));
        TextView familyName = findViewById(R.id.familyName);
        TextView familyAddress = findViewById(R.id.familyAddress);
        familyName.setText(CommonVariables.roomList.get(i).getRoomName());
        familyAddress.setText(CommonVariables.roomList.get(i).getRoomAddress());
    }
}