package com.example.things_counts;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.things_counts.R.layout.current_page2;
/*
三个bug：
1.总钱数的显示问题
2.删除问题
3.页面布局优化
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioButton button1;
    private RadioButton button2;
    private List<Fragment> list;
    private Page1 Page11=new Page1();
    private Page2 Page22=new Page2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        operate();

    }



    public void showTotals(View view) {
        String[] stringMoney=new CounFile(this).getData("值");
        int count=0;
        int[] intMoney=new int[stringMoney.length];
        for(int i=0;i<intMoney.length;i++){
            intMoney[i]=Integer.parseInt(stringMoney[i]);
            count+=intMoney[i];
        }
        TextView text=(TextView) view;
         text.setText(count+"元");
    }

    private void init() {
        list = new ArrayList<Fragment>();
        list.add(Page11);
        list.add(Page22);
        //1.获得视图
        viewPager = findViewById(R.id.nav_pager);
        button1 = findViewById(R.id.page1);
        button2 = findViewById(R.id.page2);
    }


    //此处bug，不能正确处理页面的id对象


    private void operate() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        button1.setChecked(true);
                        button2.setChecked(false);
                        break;
                    }
                    case 1: {
                        button1.setChecked(false);
                        button2.setChecked(true);

                        break;
                    }
                    default:
                        new RuntimeException();
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });

        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (button2.isChecked())
                    button2.setChecked(false);
                viewPager.setCurrentItem(0);

            }
        });
        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (button1.isChecked())
                    button1.setChecked(false);
                viewPager.setCurrentItem(1);

            }
        });


    }
    //question1：没有实现代码复用，多个点击事件
    //备忘录
    public void onclickCreate(View view){
            Intent intent = new Intent(MainActivity.this, CreateEvent.class);
            startActivityForResult(intent, 2);
    }
    public void onclickLook(View view){
        Intent intent = new Intent(MainActivity.this, HistoryEvent.class);
        startActivityForResult(intent, 2);
    }
    public void onclickRemove(View view){
        Intent intent = new Intent(MainActivity.this, RemoveEvent.class);
        startActivityForResult(intent, 2);
    }

    //账单
    public void lookHistoryCount(View view){
        Intent intent = new Intent(MainActivity.this, LookHistoryCount.class);
        startActivityForResult(intent, 2);
    }
    public void addCount(View view){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        final EditText editText=new EditText(this);
        editText.setHint("请输入金额（或者描述，单位元）");
        editText.setTextSize(30);
        dialog.setView(editText)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("您点击了取消","cancel");
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //处理数据
                        String money=editText.getText().toString();
                        //1.判断是否是数值类型
                        int Money=0;
                        try {
                            Money=Integer.parseInt(money);
                        }catch (Exception e){
                            Log.e("不是数值","error");
                            Toast.makeText(MainActivity.this,"请输入数字",0).show();
                            return ;
                        }
                        //2.通过则存储
                        boolean isAdd=new CounFile(MainActivity.this).addCount(Money);
                        if(isAdd){
                            Toast.makeText(MainActivity.this,"添加成功",0).show();
                        }
                        Log.e("您点击了确定","confirm");
                    }
                })
                .show();
    }

}
