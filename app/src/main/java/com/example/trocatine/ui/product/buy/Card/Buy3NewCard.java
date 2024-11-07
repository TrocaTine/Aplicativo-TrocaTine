package com.example.trocatine.ui.product.buy.Card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterCard;
import com.example.trocatine.adapter.RecycleViewModels.Card;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buy3NewCard extends AppCompatActivity {
    RecyclerView cardRv;
    ImageView imgLoading;
    List<Card> listCard = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy3_new_card);
        imgLoading = findViewById(R.id.imgLoading);
        cardRv = findViewById(R.id.cardRv);
        AdapterCard adapterCard = new AdapterCard(listCard);
        cardRv.setAdapter(adapterCard);
        cardRv.setLayoutManager(new GridLayoutManager(this, 1));
        listCards(cardRv);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);
//        listCard.add(new Card(1, 1, "asdfghj", LocalDate.of(2000, Month.AUGUST, 9), 1, "asadasd"));
    }

    public void onClickAddNewCard(View view) {
        Intent intent = new Intent(this, Buy3CardMethod.class);
        finish();
        startActivity(intent);
    }
    private void listCards(RecyclerView recyclerView) {
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
        Call<StandardResponseDTO> call = productApi.findCardByUser(UserUtil.email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    List<Card> cards = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<Card>>(){}.getType());
                    recyclerView.setAdapter(new AdapterCard(cards));
                    Log.e("dados resgatados", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no list card: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure card", throwable.getMessage());
            }
        });
    }
}