package com.example.trocatine.newProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.fragments.HomeFragment;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewProductTrade1 extends AppCompatActivity {
    private TextInputEditText inputTitle, inputDescription, inputTradeFor;
    Retrofit retrofit;
    private TextView errorTextTitle, errorTextDescription, errorTextTradeFor;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_trade1);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        inputTradeFor = findViewById(R.id.inputTradeFor);
        errorTextTitle = findViewById(R.id.errorTextTitle);
        errorTextDescription = findViewById(R.id.errorTextDescription);
        errorTextTradeFor = findViewById(R.id.errorTextTradeFor);

        findCategory();
    }

    public void onClickNext(View view) {
        // Verificações de input do usuário
        boolean hasError = false;

        if (inputTitle.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextTitle);
            hasError = true;
        } else {
            hideError(errorTextTitle);
        }

        if (inputDescription.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextDescription);
            hasError = true;
        } else {
            hideError(errorTextDescription);
        }

        if (inputTradeFor.getText().toString().trim().equals("")) {
            showError("Digite a informação necessária", errorTextTradeFor);
            hasError = true;
        } else {
            hideError(errorTextTradeFor);
        }

        if (!hasError) {
            spinnerCategory = findViewById(R.id.spinnerCategory);

            ProductUtil.categories.add(spinnerCategory.getSelectedItem().toString());
            Intent intent = new Intent(NewProductTrade1.this, NewProductCamera2.class);
            ProductUtil.name = inputTitle.getText().toString();
            ProductUtil.description = inputDescription.getText().toString();
            startActivity(intent);
            finish();
        }
    }

    //Método que quando acionado, deixa a mensagem de erro do input visível
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }

    //Método que quando acionado, deixa a mensagem de erro do input invisível
    public void hideError(TextView erro) {
        erro.setVisibility(View.GONE);
    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProductTrade1.this, HomeFragment.class);
        startActivity(intent);
    }
    private void findCategory() {
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
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductRepository productAPI = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productAPI.findAllCategory();
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("categria encontrada", "deu green ao encontrar categoria");
                } else {
                    try {
                        Log.e("Erro ao encontrar categoria", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
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
}