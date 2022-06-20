package com.example.talkappandroid.api;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ContactServiceAPI {
    @GET("contacts")
    Call<List<ContactItem>> getContacts(@Header("Authorization") String token);

    @POST("contacts")
    Call<Void> createContact(@Body ContactItem contactItem, @Header("Authorization") String token);

    @GET("contacts/{id}")
    Call<ContactItem> getContactById(@Path("id") String id, @Header("Authorization") String token);

    @POST("invitations")
    Call<Void> postInvitation(@Body Invitation invitation);


}
