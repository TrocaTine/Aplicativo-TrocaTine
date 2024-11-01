package com.example.trocatine.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.api.requestDTO.user.FindPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.user.FindPersonalInformationResponseDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.ui.beginning.MainActivity;
import com.example.trocatine.ui.home.HomeNavBar;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private TextInputEditText loginEmail, loginPassword;
    private TextView errorTextLoginEmail, errorTextLoginPassword;
    private ImageView backSet;
    public String token;
    private Retrofit retrofit;
    private Dialog dialog;

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
//            Intent main = new Intent(Login.this, HomeNavBar.class);
//            startActivity(main);
//        }

        Button logar = findViewById(R.id.button_save);
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
            loginEmail.setError("Digite o e-mail necessário");
            hasError = true;
        } else {
            hideError(errorTextLoginEmail);
        }

        if (loginPassword.getText().toString().equals("")) {
            loginPassword.setError("Digite a senha necessária");
            hasError = true;
        } else {
            hideError(errorTextLoginPassword);
        }
        if (!hasError) {
            String email = loginEmail.getText().toString();
            String senha = loginPassword.getText().toString();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, senha).addOnSuccessListener(task -> {
                Intent main = new Intent(Login.this, HomeNavBar.class);
                finish();
                startActivity(main);
            });
            login(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
    }
    //Método que quando acionado, deixa a mensagem de erro do input visivel
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }

    public void login(String email, String password) {

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
                    findPersonalInformation(email);
                    Log.e("Login", "passou do metodo");

                    Intent intent = new Intent(Login.this, HomeNavBar.class);
                    dadosParaHome.putString("usuario", email);
                    UserUtil.email = email;
                    dadosParaHome.putString("token", token);
                    UserUtil.token = token;
                    Log.e("token", token);
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

    private void findPersonalInformation(String email) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersRepository usersApi = retrofit.create(UsersRepository.class);
        Call<StandardResponseDTO> call = usersApi.findPersonalInformation(email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados", response.body().getData().toString());
                    StandardResponseDTO responseBody = response.body();

                    Gson gson = new Gson();
                    FindPersonalInformationResponseDTO personalInfo = gson.fromJson(
                            gson.toJson(responseBody.getData()),
                            FindPersonalInformationResponseDTO.class);

                    UserUtil.fullName = personalInfo.getFullName();
                    UserUtil.birthDate = String.valueOf(personalInfo.getBirthDate());
                    UserUtil.email = personalInfo.getEmail();
                    UserUtil.phone = personalInfo.getPhone().toString();
                    UserUtil.address = personalInfo.getAddresses().toString();
                    UserUtil.cpf = personalInfo.getCpf();

                    UserUtil.token = token;
                    Log.e("token", token);


                    Log.e("userutil", UserUtil.fullName+" "+UserUtil.birthDate+" "+UserUtil.cpf+" "+UserUtil.email+" "+UserUtil.phone+" "+UserUtil.address);
                } else {
                    try {
                        Log.e("Erro no find personal", "Resposta não foi successosoo: " + response.code() + " - " + response.errorBody().string()+"token: "+token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure", throwable.getMessage());
            }
        });
    }

    //Método que quando acionado, deixa a mensagem de erro do input invisivel
    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }
}