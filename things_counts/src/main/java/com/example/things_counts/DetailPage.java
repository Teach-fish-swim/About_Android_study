package com.example.things_counts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailPage extends AppCompatActivity {
    private String fileName="user_events";
    private TextView textView;
    private String Title="DETAIL";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        textView=findViewById(R.id.event_context);
        receive();
    }

    private void receive() {
        //bug2已解决，没有获得发过来的参数---------，为什么新创建一个Intend会接收不到参数？？？
        String title=getIntent().getStringExtra(Title);
        String realTitle=title.substring(2);
        SharedPreferences sp=getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String context=sp.getString(realTitle,"none");
        if(!context.equals("none")) {
            textView.setText(context);
        }else{
            Toast.makeText(this,"数据查看失败",0).show();
        }
    }
}
