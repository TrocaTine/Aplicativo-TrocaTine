package com.example.trocatine.api.repository;

import com.example.trocatine.api.requestDTO.notification.SavePushRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindTagByTypeRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationRepository {

    @GET("/notification/find-notification-user/{email}")
    Call<StandardResponseDTO> findNotification(@Path("email") String request);
    @POST("/notification/save-notification")
    Call<StandardResponseDTO> saveNotification(@Body SavePushRequestDTO request);
}
