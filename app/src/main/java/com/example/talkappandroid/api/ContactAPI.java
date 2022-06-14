package com.example.talkappandroid.api;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactAPI {
    private MutableLiveData<List<ContactItem>> contactListData;
    private ContactItemDao dao;
    Retrofit retrofit;
    ContactServiceAPI webServiceAPI;

    public ContactAPI(MutableLiveData<List<ContactItem>> contactListData, ContactItemDao dao) {
        this.contactListData = contactListData;
        this.dao = dao;
        retrofit = new Retrofit.Builder()
            .baseUrl(TalkAppApplication.context.getString(R.string.BaseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        webServiceAPI = retrofit.create(ContactServiceAPI.class);
    }

    public ContactAPI() {

    }

    public void get(MutableLiveData<List<ContactItem>> contacts) {
        Call<List<ContactItem>> call = webServiceAPI.getContacts();
        call.enqueue(new Callback<List<ContactItem>>() {
            @Override
            public void onResponse(Call<List<ContactItem>> call, Response<List<ContactItem>> response) {

                contacts.setValue(response.body());

                new Thread(() -> {
                    dao.delete();
                    dao.insert(response.body().toArray(new ContactItem[0]));
                    contactListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<ContactItem>> call, Throwable t) {}
            });
    }
}
