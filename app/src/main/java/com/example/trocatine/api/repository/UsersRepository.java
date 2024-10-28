package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.user.EditPersonalInformationRequestDTO;
import com.example.trocatine.api.requestDTO.user.FindPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;
import com.example.trocatine.api.requestDTO.user.CheckingEmailAlreadyRegisteredRequestDTO;
import com.example.trocatine.api.requestDTO.user.CreateUserRequestDTO;

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
    @POST("/users/find-personal-information")
    Call<StandardResponseDTO> findPersonalInformation(@Body FindPersonalInformationRequestDTO request);
    @POST("/users/edit-personal-information")
    Call<StandardResponseDTO> editPersonalInformation (@Body EditPersonalInformationRequestDTO request);
}
