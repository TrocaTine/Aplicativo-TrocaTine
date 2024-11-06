package com.example.trocatine.ui.buy_or_trade.buy.Card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.api.repository.NotificationRepository;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.notification.SavePushRequestDTO;
import com.example.trocatine.api.requestDTO.order.FinishedOrderRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.util.CardUtil;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        notification();
        List<String> emails = Arrays.asList(UserUtil.email);
        saveNotificationApi(emails, "Compra efetuada!", "Parabéns! Sua compra foi efetuada e já estara a caminho.");
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
    public void notification() {
        Intent intent = new Intent(getApplicationContext(), Buy4PurchaseMade.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Compra efetuada!")
                .setContentText("Parabéns! Sua compra foi efetuada e já estara a caminho.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        //disparar
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }
    private void saveNotificationApi(List<String> email, String title, String description) {
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

        NotificationRepository notificationRepository = retrofit.create(NotificationRepository.class);
        Call<StandardResponseDTO> call = notificationRepository.saveNotification(new SavePushRequestDTO(title, description, email));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("Notification", "notification saved");
                } else {
                    try {
                        Log.e("Erro no notification", "Resposta não foi sucesso: " + response.code() + " - " + (response.errorBody() != null ? response.errorBody().string() : "Corpo de erro vazio") + " token: " + UserUtil.token);
                    } catch (IOException e) {
                        Log.e("Erro no notification", "Erro ao ler corpo de erro: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure notification", throwable.getMessage() != null ? throwable.getMessage() : "Erro desconhecido");
            }
        });
    }
}