package com.example.trocatine.ui.buy_or_trade.buy.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.util.CardUtil;

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
            fullName.setError("Digite a informação necessário");
            hasError = true;
        } else {
            hideError(errorTextFullNameCard);
        }

        if (cardNumber.getText().toString().isEmpty()) {
            cardNumber.setError("Digite a informação necessária");
            hasError = true;
        } else if (cardNumber.getText().toString().length() != 16) {
            cardNumber.setError("O número do cartão precisa ter 16 dígitos");
            hasError = true;
        } else {
            hideError(errorTextCardNumber);
        }
        if (expirationDate.getText().toString().isEmpty()) {
            expirationDate.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextExpirationDate);
        }

        if (cvvNumber.getText().toString().isEmpty()) {
            cvvNumber.setError("Digite a informação necessária");
            hasError = true;
        } else if (cvvNumber.getText().toString().length() != 3) {
            cvvNumber.setError("O CVV precisa de 3 dígitos");
            hasError = true;
        } else {
            hideError(errorTextCvvNumber);
        }

        if (!hasError) {
            Intent intent = new Intent(Buy3CardMethod.this, Buy4PurchaseMade.class);
            finish();
            startActivity(intent);
        }
        CardUtil.cardNumber = cardNumber.getText().toString();
        CardUtil.expirationDate = expirationDate.getText().toString();
        CardUtil.cvvNumber = cvvNumber.getText().toString();
        CardUtil.fullName = fullName.getText().toString();

    }
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }

    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }
}