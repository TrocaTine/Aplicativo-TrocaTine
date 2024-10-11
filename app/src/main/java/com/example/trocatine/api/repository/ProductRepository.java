package com.example.trocatine.api.repository;

import com.example.trocatine.api.StandardResponseDTO;
import com.example.trocatine.api.models.LoginDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProductRepository {
    @POST("/api/auth/login")
    Call<StandardResponseDTO> findProductCard(
            @Header("Authorization") String token
    );
}
