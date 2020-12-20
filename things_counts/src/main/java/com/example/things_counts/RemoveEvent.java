package com.example.things_counts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RemoveEvent extends AppCompatActivity implements View.OnClickListener {
    private EditText title;
    private EditText context;
    private Button update,remove;
    private String fileName="user_events";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_event);
        init();
        listen();
    }

    private void listen() {
        update.setOnClickListener(this);
        remove.setOnClickListener(this);
    }

    private void init() {
        title=findViewById(R.id.remove_title);
        context=findViewById(R.id.remove_context);
        update=findViewById(R.id.event_update);
        remove=findViewById(R.id.event_remove);
    }

    @Override
    public void onClick(View view) {
        String myTitle=title.getText().toString();
        String myContext=context.getText().toString();
        if(myTitle.length()<=0){
            Toast.makeText(RemoveEvent.this,"请输入事件名",0).show();
            return ;
        }
        SharedPreferences sp=getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String result=sp.getString(myTitle,null);
        if(result==null){
            Toast.makeText(RemoveEvent.this,"该事件不存在",0).show();
            return ;
        }
        SharedPreferences.Editor editor=sp.edit();
        if(view==update){
            boolean isUpdated=editor.putString(myTitle,myContext).commit();
            if(isUpdated)
                Toast.makeText(RemoveEvent.this,"修改成功",0).show();
            else
                Toast.makeText(RemoveEvent.this,"修改失败",0).show();
        }else if(view==remove){
            boolean isRemoved=editor.remove(myTitle).commit();
            if(isRemoved)
                Toast.makeText(RemoveEvent.this,"删除成功",0).show();
            else
                Toast.makeText(RemoveEvent.this,"删除失败",0).show();
        }else{
            Toast.makeText(RemoveEvent.this,"操作失败",0).show();
        }
    }


}
