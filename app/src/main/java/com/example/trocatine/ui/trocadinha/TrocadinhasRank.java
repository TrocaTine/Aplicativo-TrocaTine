package com.example.trocatine.ui.trocadinha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    List<FindRankingTrocadinhaResponseDTO> listTrocadinhas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocadinhas_rank);

        trocadinhasRv = findViewById(R.id.trocadinhasRv);
        AdapterTrocadinhas adapterTrocadinhas = new AdapterTrocadinhas(listTrocadinhas);
        trocadinhasRv.setAdapter(adapterTrocadinhas);
        trocadinhasRv.setLayoutManager(new GridLayoutManager(this, 1));
        listTrocadinhas.add(new FindRankingTrocadinhaResponseDTO("Usuário 1", 5));
        listTrocadinhasRank(trocadinhasRv, UserUtil.token);
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
                    List<FindRankingTrocadinhaResponseDTO> trocadinhas = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<FindRankingTrocadinhaResponseDTO>>(){}.getType());
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