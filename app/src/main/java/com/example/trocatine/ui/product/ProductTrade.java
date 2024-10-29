package com.example.trocatine.ui.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.trocatine.R;
import com.example.trocatine.ui.home.HomeNavBar;

public class ProductTrade extends AppCompatActivity {
    ImageView buttonFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_trade);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonFavorite.setImageDrawable(getResources().getDrawable(R.drawable.icon_heart_fill));
            }
        });
    }

    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(ProductTrade.this, HomeNavBar.class);
        finish();
        startActivity(intent);
    }
}