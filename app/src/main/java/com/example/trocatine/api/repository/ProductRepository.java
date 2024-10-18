package com.example.trocatine.api.repository;

import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.requestDTO.SaveProductRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductRepository {
    @POST("/products/find-product-card")
    Call<StandardResponseDTO> findProductCard();
    @POST("/products/save-product")
    Call<StandardResponseDTO> saveProduct(@Body SaveProductRequestDTO request);
}
