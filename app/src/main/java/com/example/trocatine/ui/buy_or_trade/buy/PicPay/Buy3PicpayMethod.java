package com.example.trocatine.ui.buy_or_trade.buy.PicPay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.ui.buy_or_trade.buy.Card.Buy4PurchaseMade;

public class Buy3PicpayMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy3_picpay_method);
    }

    public void onClickEnd(View view) {
        finish();
        Intent intent = new Intent(Buy3PicpayMethod.this, Buy4PurchaseMade.class);
        startActivity(intent);
    }
}