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
import com.example.trocatine.api.StandardResponseDTO;
import com.example.trocatine.api.UsersRepository;
import com.example.trocatine.api.requestDTO.CreateUserRequestDTO;
import com.example.trocatine.home.Home;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register3 extends AppCompatActivity {
    private EditText complement, houseNumber, state, street, city, cep;
    private TextView errorTextComplement, errorTextHouseNumber, errorTextState, errorTextStreet, errorTextCity, errorTextCep;
    private Retrofit retrofit;
    private LocalDate date;
    private int number;
    private Bundle dadosParaHome;
    private String firstName,lastName,emailBundle, passwordBundle, nameBundle, cpfBundle, birthDateBundle, phoneBundle, complementBundle, houseNumberBundle, stateBundle, streetBundle, cityBundle, cepBundle, userNameBundle;

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

        backSet = findViewById(R.id.backSet);
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
        } else if (cep.getText().toString().length() != 8) {
            showError("O CEP precisa ter 8 dígitos (exemplo: 05120060)", errorTextCep);
            hasError = true;
        } else {
            hideError(errorTextCep);
        }

        if (!hasError) {
            Bundle dados = getIntent().getExtras();
            dadosParaHome = new Bundle();


            if (dados != null) {
                emailBundle = dados.getString("email");
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

//                SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
//                try {
//                    Date dateObject = formatter.parse(dados.getString("birt hDate"));
//                    Instant instant = dateObject.toInstant();
//                    ZoneId zoneId = ZoneId.systemDefault();
//                    date = instant.atZone(zoneId).toLocalDate();
//                } catch (Exception e) {
//                    Log.e("Register3", "Erro ao converter data: " + e.getMessage());
//                    return;
//                }
                birthDateBundle = dados.getString("birthDate");
                assert birthDateBundle != null;
                Log.e("Register3", birthDateBundle);
                Log.e("Register3", birthDateBundle.getClass().getName());


                criarUsuarioApi(firstName, passwordBundle, phoneBundle, lastName, userNameBundle, cpfBundle, birthDateBundle,
                        complementBundle, number, stateBundle, streetBundle, cityBundle, cepBundle, emailBundle);

                Intent intent = new Intent(Register3.this, Home.class);
                finish();
                startActivity(intent);
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
    private void criarUsuarioApi(String firstNameRequest, String passwordRequest, String phoneRequest,
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
        CreateUserRequestDTO request = new CreateUserRequestDTO(firstNameRequest, lastNameRequest, emailRequest,
                cpfRequest, birthDateRequest, false, userNameRequest, passwordRequest,
                streetRequest, houseNumberRequest, cityRequest, stateRequest,
                "bairro", complementRequest, cepRequest, phoneRequest);
        Call<StandardResponseDTO> call = userAPI.createUser(request);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("Sucesso", "Usuario criado: " + response.body() + response.message() + response.toString());
                    Object responseDTO = response.body().getData();
//                    CreateUserRequestDTO request = new CreateUserRequestDTO(firstNameRequest, lastNameRequest, emailRequest,
//                            cpfRequest, birthDateRequest, false, userNameRequest, passwordRequest,
//                            streetRequest, houseNumberRequest, cityRequest, stateRequest,
//                            "bairro", complementRequest, cepRequest, phoneRequest);
//                    dadosParaHome.putString("usuario",request.toString());
//                    Log.e("informação no bundle: ", "usuario criado: " +request);

                    Log.e("retorno fofo: ", "usuario criado: " + responseDTO);

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
                Log.e("ERRO", throwable.getMessage());
            }
        });
    }


}