package com.example.talkappandroid.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.MessageAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.sql.StatementEvent;

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

    public void postMessage(final MessageItem messageItem, MutableLiveData<Boolean> messageResponse){
        messageAPI.postMessage(dao, currentContactId, messageItem, messageResponse, this);
    }

    public void postTransfer(final Transfer transfer, MutableLiveData<Boolean> transferred){
        messageAPI.postTransfer(transfer, transferred);
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
