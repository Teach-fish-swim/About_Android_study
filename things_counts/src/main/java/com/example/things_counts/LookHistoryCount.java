package com.example.things_counts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;
public class LookHistoryCount extends AppCompatActivity {
    private ListView listView;
    private Map<String,?> map;
    private  TextView count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_count);
        init();
        operate();
    }

    private void init() {
        listView = findViewById(R.id.counts_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.count_list,new CounFile(this).getData("键值对"));
        listView.setAdapter(arrayAdapter);

    }
    private void operate(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                count= (TextView) view;

                AlertDialog.Builder dialog=new AlertDialog.Builder(LookHistoryCount.this);
                dialog
                        .setTitle("是否确定删除")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.e("您取消删除了","！！！");
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean isRemove =new CounFile(LookHistoryCount.this).removeCount(count.getText().toString());
                                if(isRemove){
                                    Toast.makeText(LookHistoryCount.this,count.getText().toString()+"删除成功",0).show();
                                    finish();
                                }else{
                                    Toast.makeText(LookHistoryCount.this,"删除失败",0).show();
                                }
                            }
                        })
                        .show();
                return false;
            }
        });

    }

}
