package com.example.trocatine.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trocatine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register1 extends AppCompatActivity {
    private EditText email, password, phone;
    private TextView errorTextEmail, errorTextPassword, errorTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.expirationDate);
        errorTextEmail = findViewById(R.id.errorTextEmail);
        errorTextPassword = findViewById(R.id.errorTextPassword);
        errorTextPhone = findViewById(R.id.errorTextPhone);
    }

    public void onClickNext(View view) {
        //Verificações de input do usuário
        boolean hasError = false;
        if (email.getText().toString().equals("")) {
            showError("Digite a informação necessária ", errorTextEmail);
            hasError = false;
        } else {
            hideError(errorTextEmail);
        }
        if (email.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextEmail);
            hasError = true;
//        } else if (!email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//            showError("Digite um e-mail válido", errorTextEmail);
//            hasError = true;
        } else {
            hideError(errorTextEmail);
        }


        if (password.getText().toString().equals("")) {
            showError("Digite a informação necessária ", errorTextPassword);
            hasError = true;
        } else {
            hideError(errorTextPassword);
        }

        if (phone.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextPhone);
            hasError = true;
        } else if (phone.getText().toString().length() != 11) {
            showError("O telefone precisa ter 11 dígitos (inclue DDD)", errorTextPhone);
            hasError = true;
        } else {
            hideError(errorTextPhone);
        }

        if (!hasError) {
            Intent intent = new Intent(Register1.this, Register2.class);
            salvarLogin(email.getText().toString(), password.getText().toString());
            finish();
            startActivity(intent);
        }

    }
    private void salvarLogin(String emailStr, String senhaStr) {
        //fazer o cadastro no firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailStr, senhaStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Register1.this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();

                    //Atualizar o nome do usuario e foto
                    FirebaseUser user = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                            .setDisplayName("nome provisório")
                            .setPhotoUri(Uri.parse("https://www.vagalume.com.br/bruno-mars/images/bruno-mars.webp"))
                            .build();
                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
    //Método que quando acionado, deixa a mensagem de erro do input visivel
    public void showError(String mensagem, TextView texto){
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }
    //Método que quando acionado, deixa a mensagem de erro do input invisivel

    public void hideError(TextView erro){
        erro.setVisibility(View.INVISIBLE);
    }
}