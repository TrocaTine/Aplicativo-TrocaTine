package com.example.trocatine.ui.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.repository.TrocadinhaRepository;
import com.example.trocatine.api.requestDTO.order.FinishedOrderRequestDTO;
import com.example.trocatine.api.requestDTO.trocadinha.FindTrocadinhaCountRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.responseDTO.trocadinha.FindTrocadinhaCountResponseDTO;
import com.example.trocatine.util.CardUtil;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buy4PurchaseMade extends AppCompatActivity {
    String numberCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy4_purchase_made);
    }

    public void onClickEnd(View view) {
        Log.e("entrou no finished", "deu green");

        Log.e("id product finished", "id do produto"+Long.valueOf(ProductUtil.idProduct));
        finishedOrder(UserUtil.email, Long.valueOf(ProductUtil.idProduct), CardUtil.cardNumber, CardUtil.method, ProductUtil.value);
        finish();
    }
    private void finishedOrder(String email, Long idProduct, String numberCard, String paymentType, BigDecimal value) {
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
        Call<StandardResponseDTO> call = productApi.finishedOrder(new FinishedOrderRequestDTO(email, idProduct, numberCard, paymentType, value));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("finished order", "aaaaaaaaaaaaaaaaaaa");
                } else {
                    try {
                        Log.e("Erro no finished order", "Resposta não foi sucesso: " + response.code() + " - " + (response.errorBody() != null ? response.errorBody().string() : "Corpo de erro vazio") + " token: " + UserUtil.token);
                    } catch (IOException e) {
                        Log.e("Erro no finished order", "Erro ao ler corpo de erro: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure", throwable.getMessage() != null ? throwable.getMessage() : "Erro desconhecido");
            }
        });
    }
}