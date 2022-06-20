package com.example.talkappandroid.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.MessageAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;

import java.util.List;

public class MessageRepository {

    private MessageAPI messageAPI;
    private MessageItemDao dao;
    private AppDB db;
    private String currentContactId;

    public MessageRepository(String id, MessageAPI messageAPI) {
        db = AppDB.getMessageDBInstance();
        dao = db.messageItemDao();
        this.messageAPI = messageAPI;
        this.currentContactId = id;

    }

    public void getMessagesFromAPI() {
        messageAPI.getMessages(currentContactId,dao, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handleGetMessages(List<MessageItem> messageItemsList) {
        messageItemsList.forEach((messageItem -> {
            messageItem.setContactID(currentContactId);
        }));
        new Thread(() -> {
            dao.clear(currentContactId);
            dao.insertAll(messageItemsList);
        }).start();
    }

    public void postTransferMessage(final Transfer transfer,MessageItem messageItem, MutableLiveData<Boolean> response){
        messageAPI.postTransferMessage(currentContactId, transfer,this, dao, messageItem, response);
    }

    public LiveData<List<MessageItem>> getAll() { return dao.getAllMessages(currentContactId);}

    public void add(final MessageItem messageItem){
        //api.add(contactItem);
    }

    public void delete(final MessageItem messageItem){
        //api.delete(contactItem);
    }

    public void reload(){
        //api.get();
    }


}
