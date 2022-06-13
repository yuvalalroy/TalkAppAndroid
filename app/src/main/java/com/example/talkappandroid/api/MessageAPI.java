package com.example.talkappandroid.api;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private MutableLiveData<List<MessageItem>> messageListData;
    private MessageItemDao dao;
    Retrofit retrofit;
    webServiceAPI webServiceAPI;

    public MessageAPI(MutableLiveData<List<MessageItem>> postListData, MessageItemDao dao) {
        this.messageListData = postListData;
        this.dao = dao;
        retrofit = new Retrofit.Builder()
            .baseUrl(TalkAppApplication.context.getString(R.string.BaseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        webServiceAPI = retrofit.create(webServiceAPI.class);
    }

    public MessageAPI() {

    }

    public void get(MutableLiveData<List<MessageItem>> messages) {
        Call<List<MessageItem>> call = webServiceAPI.getMessages();
        call.enqueue(new Callback<List<MessageItem>>() {
            @Override
            public void onResponse(Call<List<MessageItem>> call, Response<List<MessageItem>> response) {

                messages.setValue(response.body());

                new Thread(() -> {
                    dao.delete();
                    dao.insert(response.body().toArray(new MessageItem[0]));
                    messageListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<MessageItem>> call, Throwable t) {}
            });
    }
}
