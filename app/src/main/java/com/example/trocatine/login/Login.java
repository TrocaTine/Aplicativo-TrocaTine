package com.example.trocatine.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.RecycleViewModels.Product;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.FindPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.FindPersonalInformationResponseDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.beginning.MainActivity;
import com.example.trocatine.home.Home;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
                    findPersonalInformation(email);
                    Log.e("Login", "passou do metodo");

                    Intent intent = new Intent(Login.this, Home.class);
                    dadosParaHome.putString("usuario", email);
                    UserUtil.email = email;
                    dadosParaHome.putString("token", token);
                    UserUtil.token = token;
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
                                .header("Authorization", UserUtil.token)
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
        Call<StandardResponseDTO> call = usersApi.findPersonalInformation(new FindPersonalInformationRequestDTO(email));
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