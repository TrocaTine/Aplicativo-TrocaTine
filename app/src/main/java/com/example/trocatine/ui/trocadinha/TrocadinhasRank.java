package com.example.trocatine.ui.trocadinha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterTrocadinhas;
import com.example.trocatine.api.repository.TrocadinhaRepository;
import com.example.trocatine.api.responseDTO.trocadinha.FindRankingTrocadinhaResponseDTO;
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

public class TrocadinhasRank extends AppCompatActivity {
    RecyclerView trocadinhasRv;
    ImageView imgLoading, trocadinhasInfo;
    TextView silverUserName, goldenUserName, bronzeUserName, silverTrocadinhas, goldenTrocadinhas, bronzeTrocadinhas;
    List<FindRankingTrocadinhaResponseDTO> listTrocadinhas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocadinhas_rank);
        imgLoading = findViewById(R.id.imgLoading);
        trocadinhasInfo = findViewById(R.id.trocadinhasInfo);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);
        silverTrocadinhas = findViewById(R.id.silverTrocadinhas);
        silverUserName = findViewById(R.id.silverUserName);
        goldenTrocadinhas = findViewById(R.id.goldenTrocadinhas);
        goldenUserName = findViewById(R.id.goldenUserName);
        bronzeTrocadinhas = findViewById(R.id.bronzeTrocadinhas);
        bronzeUserName = findViewById(R.id.bronzeUserName);

        trocadinhasRv = findViewById(R.id.trocadinhasRv);
        AdapterTrocadinhas adapterTrocadinhas = new AdapterTrocadinhas(listTrocadinhas);
        trocadinhasRv.setAdapter(adapterTrocadinhas);
        trocadinhasRv.setLayoutManager(new GridLayoutManager(this, 1));
        listTrocadinhasRank(trocadinhasRv, UserUtil.token);
        trocadinhasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrocadinhasRank.this, TrocadinhasMoreDetails.class);
                finish();
                startActivity(intent);
            }
        });

    }
    private void listTrocadinhasRank(RecyclerView recyclerView, String token) {
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

        TrocadinhaRepository trocadinhaApi = retrofit.create(TrocadinhaRepository.class);
        Call<StandardResponseDTO> call = trocadinhaApi.findRankingTrocadinha();
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    List<FindRankingTrocadinhaResponseDTO> trocadinhas = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<FindRankingTrocadinhaResponseDTO>>(){}.getType());
                    goldenUserName.setText(trocadinhas.get(0).getNickname());
//                    goldenTrocadinhas.setText(trocadinhas.get(0).getCountTrocadinha());
                    silverTrocadinhas.setText(trocadinhas.get(1).getNickname());
                    silverUserName.setText(trocadinhas.get(1).getNickname());
//                    bronzeTrocadinhas.setText(trocadinhas.get(2).getCountTrocadinha());
                    bronzeUserName.setText(trocadinhas.get(2).getNickname());
                    recyclerView.setAdapter(new AdapterTrocadinhas(trocadinhas));
                    Log.e("rank", "deu certo");
                } else {
                    try {
                        Log.e("Erro rank", "Resposta não foi sucesso no list rank trocadinhas: " + response.code() + " - " + response.errorBody().string()+"token: "+token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure rank", throwable.getMessage());
            }
        });
    }

    public void onClickBack(View view) {
        finish();
    }
}