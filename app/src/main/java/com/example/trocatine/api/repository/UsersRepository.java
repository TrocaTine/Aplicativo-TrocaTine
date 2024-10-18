package com.example.trocatine.api.repository;

import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.requestDTO.CheckingEmailAlreadyRegisteredRequestDTO;
import com.example.trocatine.api.requestDTO.CreateUserRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersRepository {
    @POST("/users/checking-email-already-registered")
    Call<StandardResponseDTO> checkEmail(@Body CheckingEmailAlreadyRegisteredRequestDTO request);

    @POST("/users/create-user")
    Call<StandardResponseDTO> createUser(@Body CreateUserRequestDTO request);
    @POST("/api/auth/login")
    Call<StandardResponseDTO> login(@Body LoginDTO request);
}
