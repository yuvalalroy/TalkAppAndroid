package com.example.talkappandroid.api;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactAPI {
    private MutableLiveData<List<ContactItem>> contactListData;
    private static ContactAPI contactAPI;
    private static ContactServiceAPI webServiceAPI;
    private Retrofit retrofit;


    private ContactAPI() {
        this.contactListData = new MutableLiveData<List<ContactItem>>();
        retrofit = new Retrofit.Builder()
            .baseUrl(TalkAppApplication.context.getString(R.string.BaseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        webServiceAPI = retrofit.create(ContactServiceAPI.class);
    }

    public static ContactAPI getInstance() {
        if(contactAPI == null)
            contactAPI = new ContactAPI();
        return contactAPI;
    }

    public void getContacts(ContactItemDao dao) {
        Call<List<ContactItem>> call = webServiceAPI.getContacts("Bearer " + UserTokenDB.getToken());
        call.enqueue(new Callback<List<ContactItem>>() {
            @Override
            public void onResponse(Call<List<ContactItem>> call, Response<List<ContactItem>> response) {
                if (response.code() == 200){
                    System.out.println("enteredddddd");
                    List<ContactItem> contactItems;
                    if (response.body() == null){
                        contactItems = new ArrayList<>();
                    } else {
                        contactItems = response.body();
                    }
                    System.out.println(contactItems);
                    new Thread(() -> {
                        System.out.println("contact api thread before insert");
                        dao.delete();
                        dao.insertAll(contactItems);
                        System.out.println("contact api thread after insert");
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<ContactItem>> call, Throwable t) {
                System.out.println("entered fail");
                dao.delete();
            }});
    }
}
