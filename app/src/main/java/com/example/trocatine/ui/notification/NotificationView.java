package com.example.trocatine.ui.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterNotification;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.adapter.RecycleViewModels.Notification;
import com.example.trocatine.adapter.RecycleViewModels.Product;
import com.example.trocatine.api.repository.NotificationRepository;
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

public class NotificationView extends AppCompatActivity {
    RecyclerView notificationRv;
    ImageView imgLoading, backSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Notification> listNotification = new ArrayList<>();
        setContentView(R.layout.activity_notification_view);
        backSet = findViewById(R.id.backSet);

        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        notificationRv = findViewById(R.id.notificationRv);
        imgLoading = findViewById(R.id.imgLoading);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);


        AdapterNotification adapterNotification = new AdapterNotification(listNotification);
        notificationRv.setAdapter(adapterNotification);
        notificationRv.setLayoutManager(new GridLayoutManager(this, 1));

        listNotifications(notificationRv);

    }
    private void listNotifications(RecyclerView recyclerView) {
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

        NotificationRepository notification = retrofit.create(NotificationRepository.class);
        Call<StandardResponseDTO> call = notification.findNotification(UserUtil.email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    List<Notification> notifications = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<Notification>>(){}.getType());
                    recyclerView.setAdapter(new AdapterNotification(notifications));
                    Log.e("dados resgatados notifications", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no list notifications: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure notifications", throwable.getMessage());
            }
        });
    }
}