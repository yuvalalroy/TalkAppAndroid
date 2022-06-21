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

    public MessageRepository(MessageAPI messageAPI) {
        db = AppDB.getMessageDBInstance();
        dao = db.messageItemDao();
        this.messageAPI = messageAPI;
    }

    public void getMessagesFromAPI(String contactId) { messageAPI.getMessages(contactId,dao, this);}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handleGetMessages(List<MessageItem> messageItemsList, String contactID) {
        messageItemsList.forEach((messageItem -> {
            messageItem.setContactID(contactID);
        }));
        new Thread(() -> {
            dao.clear(contactID);
            dao.insertAll(messageItemsList);
        }).start();
    }

    public void postTransferMessage(String contactID, final Transfer transfer,MessageItem messageItem, MutableLiveData<Boolean> response){
        messageAPI.postTransferMessage(contactID, transfer,this, dao, messageItem, response);
    }

    public LiveData<List<MessageItem>> getAll(String contactID) { return dao.getAllMessages(contactID);}


    public void pushMessageToDAO(MessageItem message) {
        new Thread(() -> {
            dao.insert(message);
        }).start();
    }
}
