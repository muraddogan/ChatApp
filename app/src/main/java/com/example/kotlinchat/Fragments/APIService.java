package com.example.kotlinchat.Fragments;


import com.example.kotlinchat.Notifications.MyResponse;
import com.example.kotlinchat.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAv3M2Kvs:APA91bHLPuuHR4_PgXN3ZtGztJJKIxEnFNjPDDDFC35HalcTkhEsIqf5rVJVPcZvC428oZG-NvPBqUnSd07UsYRBgCXsnPaNKJ96-MmKfP_eOz_Zo-0pDqhRznlWraDPMEeNcQlQiUDr"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
