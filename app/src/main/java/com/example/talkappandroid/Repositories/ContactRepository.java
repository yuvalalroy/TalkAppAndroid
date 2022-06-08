package com.example.talkappandroid.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.talkappandroid.AppDB;
import com.example.talkappandroid.ContactItem;
import com.example.talkappandroid.ContactItemDao;
import com.example.talkappandroid.ContactItemDao_Impl;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private AppDB db;
    private ContactItemDao dao;
    private ContactListData contactListData;

    public ContactRepository() {
        contactListData = new ContactListData();
        dao = db.contactItemDao();
    }

    class ContactListData extends MutableLiveData<List<ContactItem>> {
        public ContactListData() {
            super();
            setValue((new LinkedList<>()));
        }

        @Override
        public void onActive() {
            super.onActive();

            new Thread(() -> {
                contactListData.postValue(dao.index());
            }).start();
        }
    }

    public LiveData<List<ContactItem>> getAll() { return contactListData;}

    public void add(final ContactItem contactItem){
        //api.add(contactItem);
    }

    public void delete(final ContactItem contactItem){
        //api.delete(contactItem);
    }

    public void reload(){
        //api.get();
    }


}
