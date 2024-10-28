package com.example.trocatine.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.product.ProductBuy;

public class Buy1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy1);
    }

    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(Buy1.this, ProductBuy.class);
        finish();
        startActivity(intent);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(Buy1.this, Buy2.class);
        finish();
        startActivity(intent);
    }
}