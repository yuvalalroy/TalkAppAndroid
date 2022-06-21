package com.example.talkappandroid.api;

import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServiceAPI {

    @GET("contacts/{id}/Messages")
    Call<List<MessageItem>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("contacts/{id}/Messages")
    Call<Void> createMessage(@Path("id") String id, @Body MessageItem message, @Header("Authorization") String token);

    @POST("transfer")
    Call<Void> postTransfer(@Body Transfer transfer, @Header("Authorization") String token);
}
