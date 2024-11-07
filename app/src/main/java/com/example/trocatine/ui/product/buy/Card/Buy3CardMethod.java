package com.example.trocatine.ui.product.buy.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.product.SaveInformactionCardRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.util.CardUtil;
import com.example.trocatine.util.UserUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buy3CardMethod extends AppCompatActivity {
    private EditText fullName, cardNumber, expirationDate, cvvNumber;
    private TextView errorTextFullNameCard, errorTextCardNumber, errorTextExpirationDate, errorTextCvvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy3_card_method);
        fullName = findViewById(R.id.fullName);
        cardNumber = findViewById(R.id.cardNumber);
        expirationDate = findViewById(R.id.password);
        cvvNumber = findViewById(R.id.cvvNumber);

        errorTextFullNameCard = findViewById(R.id.errorTextFullNameCard);
        errorTextCardNumber = findViewById(R.id.errorTextCardNumber);
        errorTextExpirationDate = findViewById(R.id.errorTextExpirationDate);
        errorTextCvvNumber = findViewById(R.id.errorTextCvvNumber);
    }

    public void onClickEnd(View view) {
        boolean hasError = false;

        if (fullName.getText().toString().isEmpty()) {
            fullName.setError("Digite a informação necessário");
            hasError = true;
        } else {
            hideError(errorTextFullNameCard);
        }

        if (cardNumber.getText().toString().isEmpty()) {
            cardNumber.setError("Digite a informação necessária");
            hasError = true;
        } else if (cardNumber.getText().toString().length() != 16) {
            cardNumber.setError("O número do cartão precisa ter 16 dígitos");
            hasError = true;
        } else {
            hideError(errorTextCardNumber);
        }
        if (expirationDate.getText().toString().isEmpty()) {
            expirationDate.setError("Digite a informação necessária");
            hasError = true;
        } else {
            hideError(errorTextExpirationDate);
        }

        if (cvvNumber.getText().toString().isEmpty()) {
            cvvNumber.setError("Digite a informação necessária");
            hasError = true;
        } else if (cvvNumber.getText().toString().length() != 3) {
            cvvNumber.setError("O CVV precisa de 3 dígitos");
            hasError = true;
        } else {
            hideError(errorTextCvvNumber);
        }

        if (!hasError) {
            saveCard(new SaveInformactionCardRequestDTO(UserUtil.email, cardNumber.getText().toString(),expirationDate.getText().toString(),Integer.parseInt(cvvNumber.getText().toString())));
            Intent intent = new Intent(Buy3CardMethod.this, Buy4PurchaseMade.class);
            finish();
            startActivity(intent);
        }
        CardUtil.cardNumber = cardNumber.getText().toString();
        CardUtil.expirationDate = expirationDate.getText().toString();
        CardUtil.cvvNumber = cvvNumber.getText().toString();
        CardUtil.fullName = fullName.getText().toString();

    }
    public void showError(String mensagem, TextView texto) {
        texto.setText(mensagem);
        texto.setVisibility(View.VISIBLE);
    }
    private void saveCard(SaveInformactionCardRequestDTO saveInformactionCardRequestDTO) {
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

        ProductRepository productApi = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productApi.saveInformationCard(saveInformactionCardRequestDTO);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("deu certo no save card", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no save card: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure save card", throwable.getMessage());
            }
        });
    }

    public void hideError(TextView erro) {
        erro.setVisibility(View.INVISIBLE);
    }
}