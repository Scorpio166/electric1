package com.example.electric.My.Family;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.electric.R;

public class ManageUserInRoomActivity extends AppCompatActivity {
    private String userName;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_in_room);
        userName = this.getIntent().getExtras().getString("userName");
        userId = this.getIntent().getExtras().getInt("userId");
    }
}