package com.example.electric.mvc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electric.mvc.Controller.LookOneDeviceController;

import android.os.Bundle;

public class LookOneDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_one_device);

        LookOneDeviceController mvcController = new LookOneDeviceController();

    }

    public void flashDevice(){

    }

}