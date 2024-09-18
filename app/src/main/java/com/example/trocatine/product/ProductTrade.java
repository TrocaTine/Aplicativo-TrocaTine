package com.example.trocatine.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.home.Home;

public class ProductTrade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_trade);
    }

    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(ProductTrade.this, Home.class);
        finish();
        startActivity(intent);
    }
}