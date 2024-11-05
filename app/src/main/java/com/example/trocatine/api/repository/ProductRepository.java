package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.order.FinishedOrderRequestDTO;
import com.example.trocatine.api.requestDTO.product.DeleteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.EditProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindProductByUserRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindProductCardNameRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindProductFavoriteRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindQuestionsByProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindTagByTypeRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveFavoriteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveQuestionsProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.UnfavoriteProductRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.requestDTO.product.SaveProductRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductRepository {
    @GET("/products/find-product-card")
    Call<StandardResponseDTO> findProductCard();
    @POST("/products/save-product")
    Call<StandardResponseDTO> saveProduct(@Body SaveProductRequestDTO request);
    @POST("/products/edit-product")
    Call<StandardResponseDTO> editProduct(@Body EditProductRequestDTO request);
    @GET("/products/find-product-card-name/{name}")
    Call<StandardResponseDTO> findProductByNameIsContainingIgnoreCase(@Path("name") String request);
    @GET("/products/find-product-card-trade")
    Call<StandardResponseDTO> findProductTrade();
    @GET("/products/find-product-card-buy")
    Call<StandardResponseDTO> findProductBuy();
    @GET("/products/find-product-user/{email}")
    Call<StandardResponseDTO> findProductByUser(@Path("email") String request);
    @DELETE("/products/delete-product/{ID}")
    Call<StandardResponseDTO> deleteProduct(@Path("ID") long idProduct);

    @POST("/tag/find-tag-type")
    Call<StandardResponseDTO> findTagByType(@Body FindTagByTypeRequestDTO request);
    @GET("/category/find-all-category")
    Call<StandardResponseDTO> findAllCategory();

    @POST("/favorite/save-favorite-product")
    Call<StandardResponseDTO> saveFavoriteProduct(@Body SaveFavoriteProductRequestDTO request);
    @POST("/favorite/unfavorite-product")
    Call<StandardResponseDTO> unfavoriteProduct(@Body UnfavoriteProductRequestDTO request);
    @GET("/favorite/find-product-favorite/{email}")
    Call<StandardResponseDTO> findProductFavorite(@Path("email")  String request);

    @POST("/order/finished-order")
    Call<StandardResponseDTO> finishedOrder(@Body FinishedOrderRequestDTO request);

    @POST("/card/find-card-user")
    Call<StandardResponseDTO> findCardByUser(@Body String request);

    @POST("/questions/find-questions")
    Call<StandardResponseDTO> findQuestions(@Body FindQuestionsByProductRequestDTO request);
    @POST("/questions/save-questions")
    Call<StandardResponseDTO> saveQuestion(@Body SaveQuestionsProductRequestDTO request);
}
