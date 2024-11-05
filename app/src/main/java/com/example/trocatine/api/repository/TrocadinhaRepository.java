package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.product.EditProductRequestDTO;
import com.example.trocatine.api.requestDTO.trocadinha.AddingTrocadinhaRequestDTO;
import com.example.trocatine.api.requestDTO.trocadinha.FindTrocadinhaCountRequestDTO;
import com.example.trocatine.api.requestDTO.trocadinha.RetiredTrocadinhaRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrocadinhaRepository {
    @GET("/trocadinha/trocadinha-count/{email}")
    Call<StandardResponseDTO> findTrocadinhaCount(@Path("email") String request);
    @GET("/trocadinha/ranking")
    Call<StandardResponseDTO> findRankingTrocadinha();
    @POST("/trocadinha/adding-trocadinha")
    Call<StandardResponseDTO> addTrocadinha(@Body AddingTrocadinhaRequestDTO request);

    @POST("/trocadinha/retired-trocadinha")
    Call<StandardResponseDTO> retiredTrocadinha(@Body RetiredTrocadinhaRequestDTO request);

}
