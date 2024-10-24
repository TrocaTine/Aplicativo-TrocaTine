package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.SaveProductRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TrocadinhaRepository {
    @POST("/trocadinha/trocadinha-count")
    Call<StandardResponseDTO> findTrocadinhaCount(@Body SaveProductRequestDTO request);

}
