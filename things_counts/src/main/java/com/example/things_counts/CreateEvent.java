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

import java.util.Map;
import java.util.Set;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener {
    private  Button cancel;
    private Button confirm;
    private EditText title;
    private EditText context;
    private String fileName="user_events";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        init();

    }
    private void init() {
        cancel=findViewById(R.id.cancel_button);
        confirm=findViewById(R.id.confirm_button);
        title=findViewById(R.id.title);
        context=findViewById(R.id.context);
        operate();
    }

    private void operate() {
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view==cancel){
            //返回上一层
            finish();
        }else if(view == confirm){
            String Title=title.getText().toString();
            String context1=context.getText().toString();
            if(Title.length()>0&&context1.length()>0){
                //存储数据
                saveData(Title,context1);
            }else{
                Toast.makeText(CreateEvent.this,"事件名或者内容没有填写",0).show();
            }
        }else{
            new RuntimeException();
        }
    }

    private void saveData(String myTitle,String myContext) {
       SharedPreferences sp=getSharedPreferences(fileName,Context.MODE_PRIVATE);
       SharedPreferences.Editor editor=sp.edit();
       boolean isSave= editor.putString(myTitle,myContext).commit();
       if(isSave)
           Toast.makeText(CreateEvent.this,"添加成功",0).show();
       else
           Toast.makeText(CreateEvent.this,"添加失败",0).show();
    }
}
