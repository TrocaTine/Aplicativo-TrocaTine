package com.example.trocatine.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.home.Home;

public class Register3 extends AppCompatActivity {
    private EditText complement, houseNumber, state, street, city, cep;
    private TextView errorTextComplement, errorTextHouseNumber, errorTextState, errorTextStreet, errorTextCity, errorTextCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        complement = findViewById(R.id.complement);
        houseNumber = findViewById(R.id.houseNumber);
        state = findViewById(R.id.state);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        cep = findViewById(R.id.cep);

        errorTextComplement = findViewById(R.id.errorTextComplement);
        errorTextHouseNumber = findViewById(R.id.textView8errorTextHouseNumber);
        errorTextState = findViewById(R.id.errorTextState);
        errorTextStreet = findViewById(R.id.errorTextStreet);
        errorTextCity = findViewById(R.id.errorTextCity);
        errorTextCep = findViewById(R.id.textView3);
    }
    public void onClickNext(View view) {
        //Verificações de input do usuário
        boolean hasError = false;

        // Validando os campos e exibindo os erros, se necessário
        if (complement.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextComplement);
            hasError = true;
        } else {
            hideError(errorTextComplement);
        }

        if (houseNumber.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextHouseNumber);
            hasError = true;
        } else {
            hideError(errorTextHouseNumber);
        }

        if (state.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextState);
            hasError = true;
        } else {
            hideError(errorTextState);
        }

        if (street.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextStreet);
            hasError = true;
        } else {
            hideError(errorTextStreet);
        }

        if (city.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextCity);
            hasError = true;
        } else {
            hideError(errorTextCity);
        }

        if (cep.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextCep);
            hasError = true;
        }else if (cep.getText().toString().length() != 8) {
            showError("O CEP precisa ter 8 dígitos (exemplo: 05120060)", errorTextCep);
            hasError = true;
        } else {
            hideError(errorTextCep);
        }

        // Se não houver erros, avança para a próxima tela
        if (!hasError) {
            Intent intent = new Intent(Register3.this, Home.class);
            finish();
            startActivity(intent);
        }
    }
    //Método que quando acionado, deixa a mensagem de erro do input visivel
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }
    //Método que quando acionado, deixa a mensagem de erro do input invisivel
    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }

}