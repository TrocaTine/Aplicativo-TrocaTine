package com.example.trocatine.ui.buy_or_trade.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.ui.product.ProductBuy;
import com.example.trocatine.util.UserUtil;

public class Buy1 extends AppCompatActivity {
    TextView addressCard;
    Button addNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy1);
        addressCard = findViewById(R.id.addressText);
        addressCard.setText(UserUtil.street+", "+UserUtil.houseNumber+", "+UserUtil.state+", "+UserUtil.city+", "+UserUtil.cep);
        addNewAddress = findViewById(R.id.buttonAddNewAddress);
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buy1.this, BuySaveNewAddress.class);
                finish();
                startActivity(intent);
            }
        });

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