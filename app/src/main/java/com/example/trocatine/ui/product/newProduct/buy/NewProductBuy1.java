package com.example.trocatine.ui.product.newProduct.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.util.ProductUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class NewProductBuy1 extends AppCompatActivity {
    private TextInputEditText inputTitle, inputDescription, inputTradeFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_buy1);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        inputTradeFor = findViewById(R.id.inputValue);

    }
    public void onClickNext(View view) {
        // Verificações de input do usuário
        boolean hasError = false;

        if (inputTitle.getText().toString().trim().equals("")) {
            inputTitle.setError("Digite a informação necessária");
            hasError = true;
        }

        if (inputDescription.getText().toString().trim().equals("")) {
            inputDescription.setError("Digite a informação necessária");
            hasError = true;
        }

        if (inputTradeFor.getText().toString().trim().equals("")) {
            inputTradeFor.setError("Digite a informação necessária");
            hasError = true;
        }

        if (!hasError) {

            Intent intent = new Intent(NewProductBuy1.this, NewProductBuy2.class);
            ProductUtil.name = inputTitle.getText().toString();
            ProductUtil.description = inputDescription.getText().toString();
            ProductUtil.value = BigDecimal.valueOf(Double.parseDouble(inputTradeFor.getText().toString()));
            startActivity(intent);
            finish();
        }
    }
}