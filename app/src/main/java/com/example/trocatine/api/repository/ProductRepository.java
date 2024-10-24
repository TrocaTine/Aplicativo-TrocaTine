package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.EditProductRequestDTO;
import com.example.trocatine.api.requestDTO.FindProductCardNameRequestDTO;
import com.example.trocatine.api.requestDTO.FindTagByTypeRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.requestDTO.SaveProductRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductRepository {
    @POST("/products/find-product-card")
    Call<StandardResponseDTO> findProductCard();
    @POST("/products/save-product")
    Call<StandardResponseDTO> saveProduct(@Body SaveProductRequestDTO request);

    @POST("/products/edit-product")
    Call<StandardResponseDTO> editProduct(@Body EditProductRequestDTO request);
//    findProductByNameIsContainingIgnoreCase
    @POST("/products/find-product-card-name")
    Call<StandardResponseDTO> findProductByNameIsContainingIgnoreCase(@Body FindProductCardNameRequestDTO request);
    @POST("/tag/find-tag-type")
    Call<StandardResponseDTO> findTagByType(@Body FindTagByTypeRequestDTO request);
    @GET("/category/find-all-category")
    Call<StandardResponseDTO> findAllCategory();

}
