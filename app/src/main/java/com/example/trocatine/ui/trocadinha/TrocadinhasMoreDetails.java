package com.example.trocatine.ui.trocadinha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterTrocadinhas;
import com.example.trocatine.api.repository.TrocadinhaRepository;
import com.example.trocatine.api.requestDTO.trocadinha.AddingTrocadinhaRequestDTO;
import com.example.trocatine.api.requestDTO.trocadinha.RetiredTrocadinhaRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.responseDTO.trocadinha.FindRankingTrocadinhaResponseDTO;
import com.example.trocatine.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrocadinhasMoreDetails extends AppCompatActivity {
    Button buttonAddTrocadinha, buttonRemoveTrocadinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocadinhas_more_details);
        buttonAddTrocadinha = findViewById(R.id.buttonAddTrocadinha);
        buttonRemoveTrocadinha = findViewById(R.id.buttonRemoveTrocadinha);
        buttonAddTrocadinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTrocadinha();
            }
        });
        buttonRemoveTrocadinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTrocadinha();
            }
        });
    }
    private void addTrocadinha() {
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
        Call<StandardResponseDTO> call = trocadinhaApi.addTrocadinha(new AddingTrocadinhaRequestDTO(UserUtil.email,1));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("response trocadinhas", "deu certo");
                } else {
                    try {
                        Log.e("Erro trocadinha", "Resposta trocadinhas: " + response.code() + " - " + response.errorBody().string()+"token: ");
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
    private void removeTrocadinha() {
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
        Call<StandardResponseDTO> call = trocadinhaApi.retiredTrocadinha(new RetiredTrocadinhaRequestDTO(UserUtil.email,1));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("response remove trocadinhas", "deu certo");
                } else {
                    try {
                        Log.e("Erro remove trocadinha", "Resposta trocadinhas: " + response.code() + " - " + response.errorBody().string()+"token: ");
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
}