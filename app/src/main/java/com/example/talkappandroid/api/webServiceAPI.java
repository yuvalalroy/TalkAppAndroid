package com.example.talkappandroid.api;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface webServiceAPI {
    @GET("contacts")
    Call<List<ContactItem>> getContacts();

    @POST("contacts")
    Call<Void> createContact(@Body ContactItem contactItem);

    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);

    @GET("{id}/Messages")
    Call<List<MessageItem>> getMessages();

    @POST("{id}/Messages")
    Call<Void> createMessage(@Body MessageItem messageItem);

    @DELETE("{id}/Messages/{id2}")
    Call<Void> deleteMessage(@Path("id") int id);

    @GET("Users/{id}")
    Call<UserItem> getUser();

    @POST("Users/register")
    Call<String> registerPostUser(@Body UserRegister user);

    @POST("Users/login")
    Call<Void> loginPostUser(@Body UserItem userItem);

    @DELETE("Users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

}
