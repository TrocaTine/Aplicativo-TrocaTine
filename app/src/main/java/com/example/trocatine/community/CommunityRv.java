package com.example.trocatine.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.trocatine.R;
import com.example.trocatine.RecycleViewModels.Community;
import com.example.trocatine.RecycleViewModels.Product;

import java.util.ArrayList;
import java.util.List;

public class CommunityRv extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_rv);
    }
}