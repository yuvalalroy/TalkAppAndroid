package com.example.talkappandroid.API;

import com.example.talkappandroid.ContactItem;

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

}
