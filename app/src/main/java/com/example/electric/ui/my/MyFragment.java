package com.example.electric.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.electric.MainActivity;
import com.example.electric.My.MyDeviceActivity;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.Util.User;
import com.example.electric.Util.ViewUtil;
import com.example.electric.databinding.FragmentMyBinding;
import java.util.Objects;

public class MyFragment extends Fragment {
    private FragmentMyBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding = FragmentMyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textMy;
//        myViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        ImageView iv_avatar = binding.MyAvatar;
        TextView user_name = binding.UserName;
        TextView my_family_number = binding.MyFamilyNumber;
        TextView my_equip_number = binding.MyEquipNumber;

        if (!Objects.equals(User.getAvatar(), "")) {//显示网络上的图片
            Log.i("MyActivity", "Avatar:" + User.getAvatar());
            String image_url = User.getAvatar();
            iv_avatar.setImageBitmap(ViewUtil.ReturnBitmap(image_url));
        } else {
            iv_avatar.setImageResource(R.drawable.unlogin);
        }
        user_name.setText(User.getUser_name());
        my_family_number.setText(CommonVariables.getFamilyNumber());
        my_equip_number.setText(CommonVariables.getDeviceNumber());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
