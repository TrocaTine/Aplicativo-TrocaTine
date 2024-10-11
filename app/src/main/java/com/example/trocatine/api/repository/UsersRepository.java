package com.example.trocatine.api.repository;

import com.example.trocatine.api.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.requestDTO.CheckingEmailAlreadyRegisteredRequestDTO;
import com.example.trocatine.api.requestDTO.CreateUserRequestDTO;
import com.example.trocatine.api.responseDTO.CheckingEmailAlreadyRegisteredResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsersRepository {
    @POST("/users/checking-email-already-registered")
    Call<StandardResponseDTO> checkEmail(@Body CheckingEmailAlreadyRegisteredRequestDTO request);

    @POST("/users/create-user")
    Call<StandardResponseDTO> createUser(@Body CreateUserRequestDTO request);
    @POST("/api/auth/login")
    Call<StandardResponseDTO> login(@Body LoginDTO request);
}
