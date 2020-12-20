package com.example.things_counts;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Adapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Page1 extends Fragment  {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.current_page1,container,false);
         return view;
    }



}
