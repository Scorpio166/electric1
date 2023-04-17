package com.example.electric.My;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.electric.Add.AddFamilyActivity;
import com.example.electric.MainActivity;
import com.example.electric.My.Family.ManageFamily;
import com.example.electric.R;
import com.example.electric.Util.CommonVariables;
import com.example.electric.adapter.MyFamilyAdapter;
import com.example.electric.entity.Family;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

@SuppressLint("MissingInflatedId")
public class MyFamilyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        ListView listView = findViewById(R.id.liner);
        TextView textTip = findViewById(R.id.textTip);
        listView.setEmptyView(textTip);
        MyFamilyAdapter adapter = new MyFamilyAdapter(this);
        listView.setAdapter(adapter);
//        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(this);

        FloatingActionButton addNewButton = findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ManageFamily.class);
        intent.putExtra("id", i);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AddFamilyActivity.class);
        startActivity(intent);
    }
}