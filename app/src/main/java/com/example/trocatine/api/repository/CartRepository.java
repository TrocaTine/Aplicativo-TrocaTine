package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.cart.AddProductShoppingCartResquestDTO;
import com.example.trocatine.api.requestDTO.notification.SavePushRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartRepository {
    @GET("/shopping-cart/find-product/{email}")
    Call<StandardResponseDTO> findShoppingCart(@Path("email") String request);
    @POST("/shopping-cart/add-product")
    Call<StandardResponseDTO> addProduct(@Body AddProductShoppingCartResquestDTO request);
    @DELETE("/delete-product-cart/{email}/{idProduct}")
    Call<StandardResponseDTO> deleteProductFromCart(@Path("email") String email, @Path("idProduct") String idProduct);

}
