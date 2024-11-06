package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.address.SaveAddressRequestDTO;
import com.example.trocatine.api.requestDTO.notification.SavePushRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddressRepository {
    @GET("/address/find-address/{email}")
    Call<StandardResponseDTO> findAddressUser(@Path("email") String request);
    @POST("/address/save-address")
    Call<StandardResponseDTO> saveAddress(@Body SaveAddressRequestDTO request);
}
