package com.example.trocatine.util;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.requestDTO.FindPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.FindPersonalInformationResponseDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.home.Home;
import com.example.trocatine.login.Login;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserUtil {
    public static String token;
    public static String email;
    public static String userName;
    public static String phone;
    public static String cpf;
    public static String birthDate;
    public static String address;
    public static Uri imageProfile;
    public static String fullName;
    public static int countTrocadinha;

//    public static void findPersonalInformation(String email) {
//        String API = "https://api-spring-boot-trocatine.onrender.com/";
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request originalRequest = chain.request();
//                        Request newRequest = originalRequest.newBuilder()
//                                .header("Authorization", token)
//                                .build();
//                        return chain.proceed(newRequest);
//                    }
//                })
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UsersRepository usersApi = retrofit.create(UsersRepository.class);
//        Call<StandardResponseDTO> call = usersApi.findPersonalInformation(new FindPersonalInformationRequestDTO(email));
//        call.enqueue(new Callback<StandardResponseDTO>() {
//            @Override
//            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
//                if (response.isSuccessful()) {
//                    Log.e("dados resgatados", response.body().getData().toString());
//                    StandardResponseDTO responseBody = response.body();
//
//                    Gson gson = new Gson();
//                    FindPersonalInformationResponseDTO personalInfo = gson.fromJson(
//                            gson.toJson(responseBody.getData()),
//                            FindPersonalInformationResponseDTO.class);
//
//                    UserUtil.fullName = personalInfo.getFullName();
//                    UserUtil.birthDate = String.valueOf(personalInfo.getBirthDate());
//                    UserUtil.email = personalInfo.getEmail();
//                    UserUtil.phone = personalInfo.getPhone().toString();
//                    UserUtil.address = personalInfo.getAddresses().toString();
//                    UserUtil.cpf = personalInfo.getCpf();
//
//                    UserUtil.token = token;
//                    Log.e("token", token);
//
//
//                    Log.e("userutil", UserUtil.fullName+" "+UserUtil.birthDate+" "+UserUtil.cpf+" "+UserUtil.email+" "+UserUtil.phone+" "+UserUtil.address);
//                } else {
//                    try {
//                        Log.e("Erro no find personal", "Resposta não foi successosoo: " + response.code() + " - " + response.errorBody().string()+"token: "+token);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
//                Log.e("ERRO no onFailure", throwable.getMessage());
//            }
//        });
//    }
}
