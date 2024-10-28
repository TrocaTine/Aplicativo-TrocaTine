package com.example.trocatine.newProduct.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.trocatine.R;
import com.example.trocatine.api.requestDTO.product.FindTagByTypeRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.TagDTO;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.product.SaveProductRequestDTO;
import com.example.trocatine.util.UserUtil;
import com.example.trocatine.util.ProductUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewProductTrade2 extends AppCompatActivity {
    private Retrofit retrofit;
    private Spinner spinnerQuality, spinnerSize, spinnerCategory;
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    Uri selectedImageUri;
    String idProduto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product3);
        spinnerQuality = findViewById(R.id.spinnerQuality);
        spinnerSize = findViewById(R.id.spinnerSize);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        findCategory();
        findTagType("Quality");
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
        tags.add(new TagDTO("Size", "selectedSize"));

        List<String> category = new ArrayList<>();
        category.add(selectedCategory);
        Log.e("category", category.get(0));

        Log.e("New Product 3", "Produto util"+ ProductUtil.name+ ProductUtil.description);

        saveProduct(UserUtil.email, ProductUtil.name, ProductUtil.description, BigDecimal.valueOf(0.00),
                Long.parseLong("1"),true, tags, category, UserUtil.token);

        Intent intent = new Intent(NewProductTrade2.this, NewProductTrade3Camera.class);
        startActivity(intent);
        finish();

    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProductTrade2.this, NewProductTrade1.class);
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
        Log.e("tags", tags.toString());
        SaveProductRequestDTO request = new SaveProductRequestDTO(emailUser, name, description, value, stock, flagTrade, tags, categories);
        Call<StandardResponseDTO> call = productAPI.saveProduct(request);
        Log.e("saveProduct", "entrou no metodo");
        Log.e("saveProduct", request.toString());
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("peudto registrado", "deu green ao registrar");
                    Map<String,  Object> data = (Map<String, Object>) response.body().getData();
                    if (data.containsKey("idProduct")) {
                        double idProduct = (double) data.get("idProduct");
                        Log.d("saveProduct", "ID do produto: " + idProduct);
                        idProduto = String.valueOf(idProduct);
                        ProductUtil.idProduct = idProduto;
                    }                } else {
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
                    List<String> categories = (List<String>) response.body().getData();
                    Log.e("Categoria encontrada", "Categorias recebidas: " + categories);
                    setupUnitiesSelect(categories);

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