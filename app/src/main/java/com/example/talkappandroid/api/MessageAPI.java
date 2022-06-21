package com.example.talkappandroid.api;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;
import com.example.talkappandroid.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private MutableLiveData<List<MessageItem>> messageListData;
    Retrofit retrofit;
    MessageServiceAPI messageServiceAPI;
    private static MessageAPI messageAPI;

    private MessageAPI() {
        this.messageListData = new MutableLiveData<>();
        retrofit = new Retrofit.Builder()
                .baseUrl(UserTokenDB.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageServiceAPI = retrofit.create(MessageServiceAPI.class);
    }

    public static MessageAPI getInstance() {
        if(messageAPI == null)
            messageAPI = new MessageAPI();
        return messageAPI;
    }


    public void getMessages(String contactID, MessageItemDao dao, MessageRepository messageRepository) {
        Call<List<MessageItem>> call = messageServiceAPI.getMessages(contactID, "Bearer " + UserTokenDB.getToken());
        call.enqueue(new Callback<List<MessageItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<MessageItem>> call, Response<List<MessageItem>> response) {
                if (response.code() == 200){
                    List<MessageItem> messageItems;
                    if (response.body() == null)
                        messageItems = new ArrayList<>();
                    else
                        messageItems = response.body();
                    messageRepository.handleGetMessages(messageItems, contactID);
                }
            }

            @Override
            public void onFailure(Call<List<MessageItem>> call, Throwable t) {
                dao.clear(contactID);
            }});
    }

    public void postMessage(String contactID, MessageItemDao dao, MessageItem message, MutableLiveData<Boolean> messageResponse, MessageRepository messageRepository) {
        Call<Void> messageCall = messageServiceAPI.createMessage(contactID, message, "Bearer " + UserTokenDB.getToken());
        messageCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                //messageRepository.handlePostMessage(messageResponse, message);
                messageResponse.postValue(true);
                getMessages(contactID,dao, messageRepository);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                messageResponse.postValue(false);
            }
        });
    }

    public void postTransferMessage(String contactID, Transfer transfer, MessageRepository repository, MessageItemDao dao, MessageItem messageItem, MutableLiveData<Boolean> msgResponse) {
        Call<Void> transferCall = messageServiceAPI.postTransfer(transfer,"Bearer " + UserTokenDB.getToken());
        transferCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                System.out.println(response.code());
                if (response.code() == 201){
                    postMessage(contactID,dao, messageItem,msgResponse, repository);
                } else
                    msgResponse.postValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                msgResponse.postValue(false);
            }
        });
    }
}
