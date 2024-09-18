package com.example.trocatine.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.buy.Buy1;
import com.example.trocatine.home.Home;
import com.example.trocatine.login.Login;

public class ProductBuy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);
    }


    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(ProductBuy.this, Home.class);
        finish();
        startActivity(intent);
    }

    public void onClickBuyNow(View view) {
        Intent intent = new Intent(ProductBuy.this, Buy1.class);
        finish();
        startActivity(intent);
    }

    public void onClickAddToCart(View view) {
    }
}