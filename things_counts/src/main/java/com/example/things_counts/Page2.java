package com.example.things_counts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Page2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.current_page2,container,false);
        return view;
    }
    public void onclick(View view){
        Intent intent=new Intent(getContext(),CreateEvent.class);
        startActivity(intent);
    }
}
