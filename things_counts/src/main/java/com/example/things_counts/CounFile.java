package com.example.things_counts;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CounFile extends ContextWrapper {
    private final String count_file="user_counts";
    private  SharedPreferences sp=getSharedPreferences(count_file,Context.MODE_PRIVATE);
    public CounFile(Context base) {
        super(base);
    }
    private boolean isHad(String current){
       String isHad= sp.getString(current,"none");
       if(isHad.equals("none")){
           return false;
       }else{
           return true;
       }
    }
    private int getAddCounts(String time){
        int i=1;
        while(!sp.getString(time+"("+i+")","none").equals("none")){
            i++;
        }
        return i;
    }
    public boolean addCount(int money){
       boolean isHad =isHad(getNowDate());
        SharedPreferences.Editor editor = sp.edit();
        boolean isSuccess;
       if(!isHad) {

            isSuccess = editor
                   .putString(getNowDate(), money + "")
                   .commit();
           return isSuccess;
       }
       String time=getNowDate()+"("+getAddCounts(getNowDate())+")";
       isSuccess = editor
                .putString(time, money + "")
                .commit();
       return isSuccess;
    }
    private String getNowDate(){
        String date="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(date);
       // System.out.println(sdf.format(new Date()));
        String currentTime=sdf.format(new Date());
        return currentTime;
    }
    public Map<String, ?> looCount(){
        return sp.getAll();
    }
    //删除的bug已经解决，误将事件值当作了事件名
    public boolean removeCount(String countMessage){
        String[] m1=countMessage.split(":");
        if(m1.length<2){
            Toast.makeText(CounFile.this,"不够2"+countMessage,Toast.LENGTH_SHORT).show();
            return false;
        }
        String time=m1[0].substring(4);
        SharedPreferences.Editor editor=sp.edit();
        boolean isRemove=editor.remove(time).commit();
        return isRemove;
    }
    /*
    what:1"键值对"获取消费记录
        2.“值”获取总消费
     */
    public String[] getData(String what) {
        //从文件中获得数据
        Map<String,?> map;
        map= new CounFile(this).looCount();
        //对数据进行转化
        Set<? extends Map.Entry<String, ?>> set= map.entrySet();
        String[] moneyMessage=new String [map.size()];
        Iterator<? extends Map.Entry<String, ?>> iterator= set.iterator();
        int i=0;
        while(iterator.hasNext()){
            Map.Entry<String,String>entry= (Map.Entry<String, String>) iterator.next();
            if(what.equals("键值对"))
                moneyMessage[i]=(i+1)+".  "+entry.getKey()+":    "+entry.getValue()+"元";
            else
                moneyMessage[i]=entry.getValue();
            i++;
        }
        return moneyMessage;
    }



}
