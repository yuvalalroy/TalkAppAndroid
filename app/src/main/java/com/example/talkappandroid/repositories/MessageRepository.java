package com.example.talkappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.MessageAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.model.MessageItem;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {

    private MessageAPI api;
    private MessageItemDao dao;
    private MessageListData messageListData;

    public MessageRepository() {
        AppDB db = AppDB.getMessageDBInstance();
        dao = db.messageItemDao();
        messageListData = new MessageListData();
        api = new MessageAPI(messageListData, dao);
    }

    class MessageListData extends MutableLiveData<List<MessageItem>> {
        public MessageListData() {
            super();
            setValue((new LinkedList<>()));
        }

        @Override
        public void onActive() {
            super.onActive();

            new Thread(() -> {
                messageListData.postValue(dao.index());
            }).start();

        }
    }

    public LiveData<List<MessageItem>> getAll() { return messageListData;}

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
