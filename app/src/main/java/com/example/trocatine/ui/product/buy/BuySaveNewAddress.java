package com.example.trocatine.ui.product.buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.trocatine.R;
import com.example.trocatine.api.models.AdressDTO;
import com.example.trocatine.api.repository.AddressRepository;
import com.example.trocatine.api.requestDTO.address.SaveAddressRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
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

public class BuySaveNewAddress extends AppCompatActivity {
    EditText complement, houseNumber, state, street, city, cep;
    Button addNewAddress;
    ImageView backSet;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_save_new_address);
        complement = findViewById(R.id.complement);
        houseNumber = findViewById(R.id.houseNumber);
        state = findViewById(R.id.state);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        cep = findViewById(R.id.cep);
        backSet = findViewById(R.id.backSet);

        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addNewAddress = findViewById(R.id.button_save);
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdressDTO adressDTO = new AdressDTO(street.getText().toString(), Integer.parseInt(houseNumber.getText().toString()), city.getText().toString(), state.getText().toString(), complement.getText().toString(), cep.getText().toString());
                Log.e("address", adressDTO.toString());
                saveNewAddress(new SaveAddressRequestDTO(UserUtil.email, adressDTO));
                Intent intent = new Intent(BuySaveNewAddress.this, Buy2.class);
                finish();
                startActivity(intent);
            }
        });
    }
    public void saveNewAddress(SaveAddressRequestDTO saveAddressRequestDTO) {

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

        AddressRepository addressRepository = retrofit.create(AddressRepository.class);
        Call<StandardResponseDTO> call = addressRepository.saveAddress(saveAddressRequestDTO);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("address", "address save foi");
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no save address: " + response.code() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO", "Falha na requisição no save address: " + throwable.getMessage(), throwable);
            }
        });
    }
}