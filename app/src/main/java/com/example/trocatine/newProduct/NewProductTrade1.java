package com.example.trocatine.newProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.fragments.HomeFragment;
import com.example.trocatine.util.ProductUtil;
import com.google.android.material.textfield.TextInputEditText;

public class NewProductTrade1 extends AppCompatActivity {
    private TextInputEditText inputTitle, inputDescription, inputTradeFor;
    private TextView errorTextTitle, errorTextDescription, errorTextTradeFor;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_trade1);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        inputTradeFor = findViewById(R.id.inputTradeFor);
        errorTextTitle = findViewById(R.id.errorTextTitle);
        errorTextDescription = findViewById(R.id.errorTextDescription);
        errorTextTradeFor = findViewById(R.id.errorTextTradeFor);
    }

    public void onClickNext(View view) {
        // Verificações de input do usuário
        boolean hasError = false;

        if (inputTitle.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextTitle);
            hasError = true;
        } else {
            hideError(errorTextTitle);
        }

        if (inputDescription.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextDescription);
            hasError = true;
        } else {
            hideError(errorTextDescription);
        }

        if (inputTradeFor.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextTradeFor);
            hasError = true;
        } else {
            hideError(errorTextTradeFor);
        }

        if (!hasError) {
            spinnerCategory = findViewById(R.id.spinnerCategory);

            ProductUtil.categories.add(spinnerCategory.getSelectedItem().toString());
            Intent intent = new Intent(NewProductTrade1.this, NewProductCamera2.class);
            ProductUtil.name = inputTitle.getText().toString();
            ProductUtil.description = inputDescription.getText().toString();
            startActivity(intent);
            finish();
        }
    }

    //Método que quando acionado, deixa a mensagem de erro do input visível
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }

    //Método que quando acionado, deixa a mensagem de erro do input invisível
    public void hideError(TextView erro) {
        erro.setVisibility(View.GONE);
    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProductTrade1.this, HomeFragment.class);
        startActivity(intent);
    }
}