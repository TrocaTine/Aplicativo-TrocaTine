package com.example.trocatine.trocadinha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;

public class TrocadinhasRank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocadinhas_rank);
    }

    public void onClickBack(View view) {
        finish();
    }
}