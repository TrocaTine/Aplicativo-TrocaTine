package com.example.trocatine.newProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.trocatine.R;
import com.example.trocatine.api.requestDTO.FindTagByTypeRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.TagDTO;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.SaveProductRequestDTO;
import com.example.trocatine.home.Home;
import com.example.trocatine.util.UserUtil;
import com.example.trocatine.util.ProductUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewProductTrade3 extends AppCompatActivity {
    private Retrofit retrofit;
    private Spinner spinnerQuality, spinnerSize, spinnerCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product3);
        spinnerQuality = findViewById(R.id.spinnerQuality);
        spinnerSize = findViewById(R.id.spinnerSize);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        List<String> list = new ArrayList<String>();
        findTagType("Quality");
        list.add("aaaaaa");
        setupUnitiesSelect(list);
    }


    private void setupUnitiesSelect(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        spinnerCategory.setAdapter(adapter);    }

    public void onClickNext(View view) {
        String selectedQuality = spinnerQuality.getSelectedItem().toString();
        String selectedSize = spinnerSize.getSelectedItem().toString();
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        List<TagDTO> tags = new ArrayList<>();
        tags.add(new TagDTO("Quality", selectedQuality));
        tags.add(new TagDTO("Size", selectedSize));

        Log.e("New Product 3", "Produto util"+ ProductUtil.name+ ProductUtil.description);

        saveProduct(UserUtil.email, ProductUtil.name, ProductUtil.description, BigDecimal.valueOf(5.99),
                Long.parseLong("1"),true, tags, Collections.singletonList(selectedCategory), UserUtil.token);

        Intent intent = new Intent(NewProductTrade3.this, Home.class);
        startActivity(intent);
        finish();

    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProductTrade3.this, NewProductCamera2.class);
        startActivity(intent);
    }
    private void saveProduct(String emailUser, String name, String description, BigDecimal value, long stock, boolean flagTrade, List<TagDTO> tags, List<String> categories, String token) {
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
        SaveProductRequestDTO request = new SaveProductRequestDTO(emailUser, name, description, value, stock, flagTrade, tags, ProductUtil.categories);
        Call<StandardResponseDTO> call = productAPI.saveProduct(request);
        Log.e("saveProduct", "entrou no metodo");
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("peudto registrado", "deu green ao registrar");
                } else {
                    try {
                        Log.e("Erro ao salvar produto", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
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
    private void findTagType(String type) {
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
        FindTagByTypeRequestDTO request = new FindTagByTypeRequestDTO(type);
        Call<StandardResponseDTO> call = productAPI.findTagByType(request);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("tag encontrada", "deu green ao encontrar tag");
                } else {
                    try {
                        Log.e("Erro ao encontrar tag", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
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
//    private void findCategory() {
//        String API = "https://api-spring-boot-trocatine.onrender.com/";
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request originalRequest = chain.request();
//                        Request newRequest = originalRequest.newBuilder()
//                                .header("Authorization", UserUtil.token)
//                                .build();
//                        return chain.proceed(newRequest);
//                    }
//                })
//                .build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl(API)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ProductRepository productAPI = retrofit.create(ProductRepository.class);
//        Call<StandardResponseDTO> call = productAPI.findAllCategory();
//        call.enqueue(new Callback<StandardResponseDTO>() {
//            @Override
//            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
//                if (response.isSuccessful()) {
//                    Log.e("categria encontrada", "deu green ao encontrar categoria");
//                } else {
//                    try {
//                        Log.e("Erro ao encontrar categoria", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
//                Log.e("ERRO", "Falha na requisição: " + throwable.getMessage(), throwable);
//            }
//        });
//    }
}