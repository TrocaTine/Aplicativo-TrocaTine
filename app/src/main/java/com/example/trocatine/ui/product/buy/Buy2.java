package com.example.trocatine.ui.product.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.ui.product.buy.Card.Buy3NewCard;
import com.example.trocatine.ui.product.buy.PicPay.Buy3PicpayMethod;
import com.example.trocatine.util.CardUtil;

public class Buy2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy2);
    }

    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(Buy2.this, Buy1.class);
        finish();
        startActivity(intent);
    }

    public void onClickPicPayMethod(View view) {
        Intent intent = new Intent(Buy2.this, Buy3PicpayMethod.class);
        finish();
        startActivity(intent);
        CardUtil.method = "PicPay";
    }

    public void onClickCardMethod(View view) {
        Intent intent = new Intent(Buy2.this, Buy3NewCard.class);
        finish();
        startActivity(intent);
        CardUtil.method = "Cartão";
    }
}