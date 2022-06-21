package com.example.talkappandroid.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.model.UserToken;
import com.example.talkappandroid.repositories.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactAPI {
    private MutableLiveData<List<ContactItem>> contactListData;
    private static ContactAPI contactAPI;
    private static ContactServiceAPI contactServiceAPI;
    private Retrofit retrofit;


    private ContactAPI() {
        this.contactListData = new MutableLiveData<>();
        retrofit = new Retrofit.Builder()
            .baseUrl(UserTokenDB.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        contactServiceAPI = retrofit.create(ContactServiceAPI.class);
    }

    public static ContactAPI getInstance() {
        if(contactAPI == null)
            contactAPI = new ContactAPI();
        return contactAPI;
    }

    public void getContacts(ContactItemDao dao, ContactRepository contactRepository) {
        Call<List<ContactItem>> call = contactServiceAPI.getContacts("Bearer " + UserTokenDB.getToken());
        call.enqueue(new Callback<List<ContactItem>>() {
            @Override
            public void onResponse(Call<List<ContactItem>> call, Response<List<ContactItem>> response) {
                if (response.code() == 200){
                    List<ContactItem> contactItems;
                    if (response.body() == null)
                        contactItems = new ArrayList<>();
                    else
                        contactItems = response.body();
                    contactRepository.handleGetContacts(contactItems);
                }
            }

            @Override
            public void onFailure(Call<List<ContactItem>> call, Throwable t) {
                dao.clear();
            }});
    }


    public void postInvitation(Invitation invitation, MutableLiveData<Boolean> invited) {
        Call<Void> invitationCall = contactServiceAPI.postInvitation(invitation);
        invitationCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 201) {
                    invited.postValue(true);
                }
                else
                    invited.postValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                invited.postValue(false);
            }
        });
    }

    public void postContact(ContactItem contact, MutableLiveData<String> contactResponse, ContactRepository contactRepository) {
        Call<Void> contactCall = contactServiceAPI.createContact(contact, "Bearer " + UserTokenDB.getToken());
        contactCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 201)
                    contactRepository.handlePostContacts("ok", contactResponse, contact);
                    //contactResponse.postValue("ok");
                else
                    contactRepository.handlePostContacts("You already have this contact. Please choose another", contactResponse, contact);
                    //contactResponse.postValue("You already have this contact. Please choose another");

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                contactResponse.postValue("Something went wrong");
            }
        });
    }

}
