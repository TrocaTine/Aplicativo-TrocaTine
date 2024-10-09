package com.example.trocatine.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.home.Home;

public class Buy3CardMethod extends AppCompatActivity {
    private EditText fullName, cardNumber, expirationDate, cvvNumber;
    private TextView errorTextFullNameCard, errorTextCardNumber, errorTextExpirationDate, errorTextCvvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy3_card_method);
        fullName = findViewById(R.id.fullName);
        cardNumber = findViewById(R.id.cardNumber);
        expirationDate = findViewById(R.id.password);
        cvvNumber = findViewById(R.id.cvvNumber);

        errorTextFullNameCard = findViewById(R.id.errorTextFullNameCard);
        errorTextCardNumber = findViewById(R.id.errorTextCardNumber);
        errorTextExpirationDate = findViewById(R.id.errorTextExpirationDate);
        errorTextCvvNumber = findViewById(R.id.errorTextCvvNumber);
    }

    public void onClickEnd(View view) {
        boolean hasError = false;

        if (fullName.getText().toString().isEmpty()) {
            showError("Digite a informação necessária", errorTextFullNameCard);
            hasError = true;
        } else {
            hideError(errorTextFullNameCard);
        }

        if (cardNumber.getText().toString().isEmpty()) {
            showError("Digite a informação necessária", errorTextCardNumber);
            hasError = true;
        } else if (cardNumber.getText().toString().length() != 16) {
            showError("O número do cartão precisa ter 16 dígitos", errorTextCardNumber);
            hasError = true;
        } else {
            hideError(errorTextCardNumber);
        }

        if (expirationDate.getText().toString().isEmpty()) {
            showError("Digite a informação necessária", errorTextExpirationDate);
            hasError = true;
        } else {
            hideError(errorTextExpirationDate);
        }

        if (cvvNumber.getText().toString().isEmpty()) {
            showError("Digite a informação necessária", errorTextCvvNumber);
            hasError = true;
        } else if (cvvNumber.getText().toString().length() != 3) {
            showError("O CVV precisa ter 3 dígitos", errorTextCvvNumber);
            hasError = true;
        } else {
            hideError(errorTextCvvNumber);
        }

        if (!hasError) {
            Intent intent = new Intent(Buy3CardMethod.this, Buy4PurchaseMade.class);
            finish();
            startActivity(intent);
        }
    }
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }

    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }
}