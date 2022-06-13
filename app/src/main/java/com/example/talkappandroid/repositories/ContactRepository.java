package com.example.talkappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private ContactAPI api;
    private ContactItemDao dao;
    private ContactListData contactListData;

    public ContactRepository() {
        AppDB db = AppDB.getContactDBInstance();
        dao = db.contactItemDao();
        contactListData = new ContactListData();
        api = new ContactAPI(contactListData, dao);
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
