package com.example.electric.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.example.electric.My.Device.DeviceInRoomActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ViewUtil {

    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View v : viewList) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static void showAToast(Context context, String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);// 自定义对话框
        builder.setMessage(mes);
        builder.show();// 让弹出框显示
    }

    public static Bitmap ReturnBitmap(String image_url) {//将url转化为bitmap
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(image_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert myFileUrl != null;
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}