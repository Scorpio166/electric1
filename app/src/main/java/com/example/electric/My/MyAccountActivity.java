package com.example.electric.My;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.electric.LoginMainActivity;
import com.example.electric.MainActivity;
import com.example.electric.R;
import com.example.electric.Util.User;
import com.example.electric.Util.ViewUtil;

import java.util.Objects;

@SuppressLint("MissingInflatedId")

public class MyAccountActivity extends AppCompatActivity {
    private String[] sexArray = {"男","保密","女"};
    private Button unload;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        unload = findViewById(R.id.unload);
        unload.setOnClickListener(v -> {
            // 先清空缓存
            SharedPreferences userSettings = getSharedPreferences("cache", MODE_PRIVATE);
            SharedPreferences.Editor editor = userSettings.edit();
            editor.clear();
            editor.apply();

            finishAffinity();
            Intent intent = new Intent(this, LoginMainActivity.class);
            startActivity(intent);
        });
        ImageView MyHeadAvatar = findViewById(R.id.MyHeadAvatar);
        if (!Objects.equals(User.getAvatar(), "")) {//显示网络上的图片
            Log.i("MyActivity", "Avatar:" + User.getAvatar());
            String image_url = User.getAvatar();
            MyHeadAvatar.setImageBitmap(ViewUtil.ReturnBitmap(image_url));
        } else {
            MyHeadAvatar.setImageResource(R.drawable.unlogin);
        }

        Log.i("MyAccountActivity", "onCreate: " + "User.phonenumber:" + User.getPhonenumber());
        TextView information_phoneNumber = findViewById(R.id.information_phoneNumber);
        information_phoneNumber.setText(User.getPhonenumber());
        information_phoneNumber.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            builder.setMessage("暂未开通修改手机号功能");
            builder.show();// 让弹出框显示
        });

        TextView information_address = findViewById(R.id.information_address);
        information_address.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            builder.setMessage("尚未完善");
            builder.show();// 让弹出框显示
        });

        TextView information_sex = findViewById(R.id.information_sex);
        LinearLayout choose_sex = findViewById(R.id.choose_sex);
        choose_sex.setOnClickListener(v -> {//性别点击后弹出性别选择框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
            // checkedItem默认的选中 setSingleChoiceItems设置单选按钮组
            builder.setSingleChoiceItems(sexArray, 1, (dialog, which) -> {// which是被选中的位置
                // showToast(which+"");
                information_sex.setText(sexArray[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            });
            builder.show();// 让弹出框显示
        });

    }
}