package com.example.trocatine.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.util.UserUtil;

public class Register2 extends AppCompatActivity {

    private EditText fullname, username, cpf, birthdate;
    private TextView errorTextFullname, errorTextUsername, errorTextCpf, errorTextBirthdate;
    private ImageView backSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        fullname = findViewById(R.id.password);
        username = findViewById(R.id.username);
        cpf = findViewById(R.id.newFullName);
        birthdate = findViewById(R.id.birthdate);

        errorTextFullname = findViewById(R.id.errorTextFullName);
        errorTextUsername = findViewById(R.id.errorTextUserName);
        errorTextCpf = findViewById(R.id.errorTextCpf);
        errorTextBirthdate = findViewById(R.id.errorTextBirthDate);

        backSet = findViewById(R.id.backSet);
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2.this, Register1.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void onClickNext(View view) {
        //Verificações de input do usuário
        boolean hasError = false;
        if (fullname.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextFullname);
            hasError = true;
        } else {
            hideError(errorTextFullname);
        }

        if (username.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextUsername);
            hasError = true;
        } else {
            hideError(errorTextUsername);
        }

        if (cpf.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextCpf);
            hasError = true;
        } else if (cpf.getText().toString().length() != 11) {
            showError("O CPF precisa ter 11 dígitos (sem caracter especial)", errorTextCpf);
            hasError = true;
        } else {
            hideError(errorTextCpf);
        }

        if (birthdate.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextBirthdate);
            hasError = true;
        } else {
            hideError(errorTextBirthdate);
        }

        if (!hasError) {
            Bundle dados = getIntent().getExtras();

            assert dados != null;
            dados.putString("fullName",fullname.getText().toString());
            Log.e("register2", fullname.getText().toString());
            dados.putString("userName",username.getText().toString());
            dados.putString("cpf",cpf.getText().toString());
            dados.putString("birthDate",birthdate.getText().toString());

            UserUtil.userName = username.getText().toString();
            UserUtil.cpf = cpf.getText().toString();
            UserUtil.fullName = fullname.getText().toString();
            UserUtil.birthDate = birthdate.getText().toString();

            Intent intent = new Intent(this, Register3.class);

            intent.putExtras(dados);

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