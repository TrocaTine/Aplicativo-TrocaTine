package com.example.trocatine.ui.beginning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.responseDTO.user.FindPersonalInformationResponseDTO;
import com.example.trocatine.ui.home.HomeNavBar;
import com.example.trocatine.ui.login.Login;
import com.example.trocatine.ui.register.Register1;
import com.example.trocatine.ui.register.Register3;
import com.example.trocatine.util.UserUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MainActivity extends AppCompatActivity {
    public String token;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    Intent intent = new Intent(MainActivity.this, HomeNavBar.class);
                    dadosParaHome.putString("usuario", email);
                    UserUtil.email = email;
                    dadosParaHome.putString("token", token);
                    UserUtil.token = token;
                    UserUtil.password = password;
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
                    Log.e("dados do usuario", response.body().getData().toString());
                    Log.e("user profile", "aaaa");
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

                    UserUtil.city = personalInfo.getAddresses().get(0).getCity();
                    UserUtil.cep = personalInfo.getAddresses().get(0).getCep();
                    UserUtil.state = personalInfo.getAddresses().get(0).getState();
                    UserUtil.houseNumber = String.valueOf(personalInfo.getAddresses().get(0).getNumber());
                    UserUtil.street = personalInfo.getAddresses().get(0).getStreet();

                    UserUtil.cpf = personalInfo.getCpf();
                    UserUtil.userName = personalInfo.getNickname();

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

    public void openRegister(View view) {
        Intent intent = new Intent(MainActivity.this, Register1.class);
        startActivity(intent);
    }

    public void openLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(intent);
    }
}