package com.example.trocatine.ui.register;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        backSet = findViewById(R.id.imgBack);
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
            fullname.setError("Digite a informação necessária");
            hasError = true;
        } else {
            String fullNameText = fullname.getText().toString();
            String[] nameParts = fullNameText.split(" ");
            if (nameParts.length < 2) {
                fullname.setError("O nome deve conter nome e sobrenome");
                hasError = true;
            } else {
                hideError(errorTextFullname);
            }
        }

        if (username.getText().toString().equals("")) {
            username.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextUsername);
        }

        if (cpf.getText().toString().equals("")) {
            cpf.setError("Digite a informação necessária");
            hasError = true;
        } else if (cpf.getText().toString().length() != 11) {
            username.setError("O CPF precisa ter 11 dígitos");
            hasError = true;
        } else {
            hideError(errorTextCpf);
        }

        if (birthdate.getText().toString().equals("")) {
            birthdate.setError("Digite a informação necessária");
            hasError = true;
        } else {
            String birthdateText = birthdate.getText().toString();
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher matcher = pattern.matcher(birthdateText);
            if (!matcher.matches()) {
                birthdate.setError("A data precisa estar no formato yyyy-MM-dd (2000-12-05)");
                hasError = true;
            } else {
                hideError(errorTextBirthdate);
            }
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