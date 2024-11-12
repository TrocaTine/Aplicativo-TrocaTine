package com.example.trocatine.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trocatine.R;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.requestDTO.user.CreateUserRequestDTO;
import com.example.trocatine.ui.home.HomeNavBar;
import com.example.trocatine.util.UserUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register3 extends AppCompatActivity {
    private EditText complement, houseNumber, state, street, city, cep;
    private TextView errorTextComplement, errorTextHouseNumber, errorTextState, errorTextStreet, errorTextCity, errorTextCep;
    private Retrofit retrofit;
    private String token;
    private int number;
    private Bundle dadosParaHome = new Bundle();
    private String firstName, lastName, emailBundle, passwordBundle, nameBundle, cpfBundle, birthDateBundle, phoneBundle, complementBundle, houseNumberBundle, stateBundle, streetBundle, cityBundle, cepBundle, userNameBundle;

    private ImageView backSet;

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
        errorTextCep = findViewById(R.id.BirthDate);

        backSet = findViewById(R.id.imgBack);
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register3.this, Register2.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public void onClickNext(View view) {
        // Verificações de input do usuário
        boolean hasError = false;

        // Validando os campos e exibindo os erros, se necessário
        if (complement.getText().toString().equals("")) {
            complement.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextComplement);
        }

        if (houseNumber.getText().toString().equals("")) {
            houseNumber.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextHouseNumber);
        }

        if (state.getText().toString().equals("")) {
            state.setError("Digite a informação necessária");
            hasError = true;
        } else {
            String stateText = state.getText().toString();
            if (stateText.length() != 2) {
                state.setError("O estado deve ser escrito como SP ou BA");
                hasError = true;
            } else {
                hideError(errorTextState);
            }
        }

        if (street.getText().toString().equals("")) {
            street.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextStreet);
        }

        if (city.getText().toString().equals("")) {
            city.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextCity);
        }

        if (cep.getText().toString().equals("")) {
            cep.setError("Digite a informação necessária");
            hasError = true;
        } else if (cep.getText().toString().length() != 8) {
            cep.setError("O CEP precisa ter 8 dígitos (exemplo: 05120060)");
            hasError = true;
        } else {
            hideError(errorTextCep);
        }

        if (!hasError) {
            Bundle dados = getIntent().getExtras();
            dadosParaHome = new Bundle();


            if (dados != null) {
                emailBundle = dados.getString("email");
                Log.e("Register3", "emailbundle: "+emailBundle);
                phoneBundle = dados.getString("phone");
                passwordBundle = dados.getString("password");

                String fullNameBundle = dados.getString("fullName");
                String[] partes = fullNameBundle.split(" ");
                firstName = partes[0];
                lastName = partes[partes.length - 1];
                userNameBundle = dados.getString("userName");
                cpfBundle = dados.getString("cpf");

                try {
                    number = Integer.parseInt(houseNumber.getText().toString());
                } catch (NumberFormatException e) {
                    showError("Número da casa inválido", errorTextHouseNumber);
                    return;
                }

                complementBundle = complement.getText().toString();
                stateBundle = state.getText().toString();
                streetBundle = street.getText().toString();
                cityBundle = city.getText().toString();
                cepBundle = cep.getText().toString();

                UserUtil.state = state.getText().toString();
                UserUtil.street = street.getText().toString();
                UserUtil.city = city.getText().toString();
                UserUtil.cep = cep.getText().toString();

                birthDateBundle = dados.getString("birthDate");

                assert birthDateBundle != null;
                Log.e("Register3", birthDateBundle);
                Log.e("Register3", birthDateBundle.getClass().getName());
                createNewUserApi(firstName, passwordBundle, phoneBundle, lastName, userNameBundle, cpfBundle, birthDateBundle,
                        complementBundle, number, stateBundle, streetBundle, cityBundle, cepBundle, emailBundle);
                login(emailBundle, passwordBundle);
                UserUtil.email = emailBundle;
                UserUtil.userName = userNameBundle;
                UserUtil.fullName = fullNameBundle;
                UserUtil.phone = phoneBundle;
                UserUtil.birthDate = birthDateBundle;
                UserUtil.cpf = cpfBundle;
            } else {
                Log.e("Register3", "Nenhum dado foi passado do Bundle");
            }
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

    private void createNewUserApi(String firstNameRequest, String passwordRequest, String phoneRequest,
                                  String lastNameRequest, String userNameRequest, String cpfRequest,
                                  String birthDateRequest, String complementRequest,
                                  int houseNumberRequest, String stateRequest, String streetRequest,
                                  String cityRequest, String cepRequest, String emailRequest) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersRepository userAPI = retrofit.create(UsersRepository.class);
        Log.e("Data de nascimento:", birthDateRequest);
        CreateUserRequestDTO request = new CreateUserRequestDTO(firstNameRequest, lastNameRequest, emailRequest,
                cpfRequest, birthDateRequest, false, userNameRequest, passwordRequest,
                streetRequest, houseNumberRequest, cityRequest, stateRequest,
                "bairro", complementRequest, cepRequest, phoneRequest);

        Call<StandardResponseDTO> call = userAPI.createUser(request);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    login(emailRequest, passwordRequest);
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no create user: " + response.code() + " - " + response.errorBody().string());
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
                    Object responseDTO = response.body().getData();
                    token = responseDTO.toString();
                    token = token.replace("{token=", "").replace("}", "");
                    Intent intent = new Intent(Register3.this, HomeNavBar.class);
                    dadosParaHome.putString("usuario", email);
                    dadosParaHome.putString("token", token);
                    UserUtil.token = token;
                    UserUtil.password = password;
                    Log.e("token:", token);
                    saveFirebaseRegister(UserUtil.password, UserUtil.email, UserUtil.password);
                    intent.putExtras(dadosParaHome);
                    finish();
                    startActivity(intent);
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no token: " + response.code() + " - " + response.errorBody().string());
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
    private void saveFirebaseRegister(String nomeStr, String emailStr, String senhaStr) {
        //fazer o cadastro no firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailStr, senhaStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Register3.this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();

                    //Atualizar o nome do usuario e foto
                    FirebaseUser user = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                            .setDisplayName(nomeStr)
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
}