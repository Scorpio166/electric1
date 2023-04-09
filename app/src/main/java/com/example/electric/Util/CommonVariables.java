package com.example.electric.Util;
import android.app.Application;

import com.example.electric.adapter.MyNoticeAdapter;
import com.example.electric.entity.Device;
import com.example.electric.entity.Family;
import com.example.electric.entity.Notice;
import com.example.electric.entity.Scene;

import java.util.List;

public class CommonVariables extends Application {

    public static List<Family> roomList;
    public static List<Device> deviceList;
    public static List<Scene> sceneList;
    public static List<Notice> noticeList;
    public static List<Notice> noticedList;

    public static MyNoticeAdapter noticeAdapter;
    public static MyNoticeAdapter noticedAdapter;

    public void onCreate() {
        super.onCreate();
    }
    public static String getDeviceNumber() {
        if(CommonVariables.deviceList == null)
            return "0";
        else return String.valueOf(CommonVariables.deviceList.size());
    }

    public static String getFamilyNumber() {
        if(CommonVariables.roomList == null)
            return "0";
        else return String.valueOf(CommonVariables.roomList.size());
    }

    public static String getSceneNumber() {
        if(CommonVariables.sceneList == null)
            return "0";
        else return String.valueOf(CommonVariables.deviceList.size());
    }
}
