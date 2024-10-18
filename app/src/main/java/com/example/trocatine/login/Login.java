package com.example.trocatine.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.beginning.MainActivity;
import com.example.trocatine.home.Home;
import com.example.trocatine.util.AndroidUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private TextInputEditText loginEmail, loginPassword;
    private TextView errorTextLoginEmail, errorTextLoginPassword;
    private ImageView backSet;
    private Retrofit retrofit;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        errorTextLoginEmail = findViewById(R.id.errorTextLoginEmail);
        errorTextLoginPassword = findViewById(R.id.errorTextLoginPassword);

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if(auth.getCurrentUser() != null){
//            Intent main = new Intent(Login.this, Home.class);
//            startActivity(main);
//        }

        Button logar = findViewById(R.id.button_next);
        backSet = findViewById(R.id.backSet);
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    public void onClickNext(View view) {
        //Verificações de input do usuário
        boolean hasError = false;

        if (loginEmail.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextLoginEmail);
            hasError = true;
//        } else if (!loginEmail.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$")) {
//                showError("Digite um e-mail válido", errorTextLoginEmail);
//                hasError = true;
        } else {
            hideError(errorTextLoginEmail);
        }

        if (loginPassword.getText().toString().equals("")) {
            showError("Digite a informação necessária", errorTextLoginPassword);
            hasError = true;
        } else {
            hideError(errorTextLoginPassword);
        }
        if (!hasError) {
            String email = loginEmail.getText().toString();
            String senha = loginPassword.getText().toString();

            //fazer login no firebase
//            FirebaseAuth autenticar = FirebaseAuth.getInstance();
//            autenticar.signInWithEmailAndPassword(email, senha)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            String msg="Você esqueceu...";
//                            if (task.isSuccessful()) {
//                                //redirecionar para a proxima tela
//                                Intent main = new Intent(Login.this, Home.class);
//                                startActivity(main);
//                                finish();
//                            }else{
//                                try{
//                                    throw task.getException();
//                                }catch (FirebaseAuthInvalidUserException faiue){
//                                    msg = "Usuário não encontrado";
//                                }catch (FirebaseAuthInvalidCredentialsException e) {
//                                    msg = "Senha invalidos";
//                                }catch (Exception e){
//                                    Log.e("ERRO",e.getMessage());
//                                }
//                                Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
            login(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
    }
    //Método que quando acionado, deixa a mensagem de erro do input visivel
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }
    private void login(String email, String password) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersRepository userAPI = retrofit.create(UsersRepository.class);
        LoginDTO request = new LoginDTO(email, password);
        Call<StandardResponseDTO> call = userAPI.login(request);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Bundle dadosParaHome = new Bundle();
                    Object responseDTO = response.body().getData();
                    token = responseDTO.toString();
                    token = token.replace("{token=", "").replace("}", "");

                    Intent intent = new Intent(Login.this, Home.class);

                    dadosParaHome.putString("usuario", email);
                    AndroidUtil.email = email;
                    dadosParaHome.putString("token", token);
                    AndroidUtil.token = token;
                    intent.putExtras(dadosParaHome);
                    Log.e("LOGIIIJNN", "Funfou deu green");

                    finish();
                    startActivity(intent);
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO", "Falha na requisição: " + throwable.getMessage(), throwable);
            }
        });
    }

    //Método que quando acionado, deixa a mensagem de erro do input invisivel
    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }
}