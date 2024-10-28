package com.example.trocatine.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.buy.Buy1;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.home.HomeNavBar;
import com.example.trocatine.userProfile.OthersUserProfile;

public class ProductBuy extends AppCompatActivity {
    TextView productName, productDescription, productCreatedAt, productValue;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);
        DatabaseCamera databaseCamera = new DatabaseCamera();

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productCreatedAt = findViewById(R.id.productCreated);
        productValue = findViewById(R.id.userPhone);

        Bundle dados = getIntent().getExtras();
        productValue.setText("R$ "+String.valueOf(dados.getDouble("value")));
        productCreatedAt.setText(dados.getString("createdAt"));
        productDescription.setText(dados.getString("description"));
        productName.setText(dados.getString("name"));
        id = dados.getString("id");
        databaseCamera.downloadGaleriaProduct(this, findViewById(R.id.photoProduct), id);

    }


    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(ProductBuy.this, HomeNavBar.class);
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

    public void onClickOtherUserProfile(View view) {
        Intent intent = new Intent(ProductBuy.this, OthersUserProfile.class);
        finish();
        startActivity(intent);
    }
}