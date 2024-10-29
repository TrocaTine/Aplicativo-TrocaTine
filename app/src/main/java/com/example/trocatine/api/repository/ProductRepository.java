package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.product.EditProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindProductCardNameRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindProductFavoriteRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindTagByTypeRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveFavoriteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.UnfavoriteProductRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.requestDTO.product.SaveProductRequestDTO;

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
    @POST("/favorite/save-favorite-product")
    Call<StandardResponseDTO> saveFavoriteProduct(@Body SaveFavoriteProductRequestDTO request);
    @POST("/favorite/unfavorite-product")
    Call<StandardResponseDTO> unfavoriteProduct(@Body UnfavoriteProductRequestDTO request);
    @POST("/favorite/find-product-favorite")
    Call<StandardResponseDTO> findProductFavorite(@Body FindProductFavoriteRequestDTO request);

}
