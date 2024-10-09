package com.example.trocatine.api;

import com.example.trocatine.api.requestDTO.CheckingEmailAlreadyRegisteredRequestDTO;
import com.example.trocatine.api.requestDTO.CreateUserRequestDTO;
import com.example.trocatine.api.responseDTO.CheckingEmailAlreadyRegisteredResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsersRepository {
    @POST("/users/checking-email-already-registered")
    Call<StandardResponseDTO> checkEmail(@Body CheckingEmailAlreadyRegisteredRequestDTO request);

    @POST("/users/create-user")
    Call<StandardResponseDTO> createUser(@Body CreateUserRequestDTO request);
}
