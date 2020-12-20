package com.example.things_counts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Edits;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class HistoryEvent extends AppCompatActivity {
    private ListView listView;
    private Map<String,?> map;
    private String fileName="user_events";
    private String title="DETAIL";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_event);
        init();
    }

    private void init() {
        //设置事件列表展示
        listView = findViewById(R.id.history_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.history_list, getData());
        listView.setAdapter(arrayAdapter);
    }

    private String[] getData() {
        //从文件中获得数据
        SharedPreferences sp = getSharedPreferences(fileName,Context.MODE_PRIVATE);
        map= sp.getAll();
        //对数据进行转化
        System.out.println("??????????????????????????????????????????????");
        System.out.println(sp.getAll());
        Set<? extends Map.Entry<String, ?>> set= map.entrySet();
        String[] eventName=new String [map.size()];
        Iterator<? extends Map.Entry<String, ?>> iterator= set.iterator();
        int i=0;
        while(iterator.hasNext()){
          Map.Entry<String,String>entry= (Map.Entry<String, String>) iterator.next();
          eventName[i]=(i+1)+"."+entry.getKey();
          i++;
        }
        return eventName;
    }
    public void lookDetail(View view){
        Intent intent=new Intent(this,DetailPage.class);
        TextView textView= (TextView) view;
        //bug1(没有问题，能够得到数据)

        intent.putExtra(title,textView.getText().toString());
        startActivity(intent);
    }

}
