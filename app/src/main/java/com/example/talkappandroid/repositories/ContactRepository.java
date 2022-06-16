package com.example.talkappandroid.repositories;

import android.view.ContextThemeWrapper;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.api.UserAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private final ContactAPI contactsApi;
    private final ContactItemDao dao;
    private final ContactListData contactListData;

    public ContactRepository(ContactAPI contactsApi) {
        AppDB db = AppDB.getContactDBInstance();
        this.dao = db.contactItemDao();
        this.contactsApi = contactsApi;
        this.contactListData = new ContactListData();
    }

    class ContactListData extends MutableLiveData<List<ContactItem>> {
        public ContactListData() {
            super();
            contactsApi.getContacts(dao);
            new Thread(() -> {
                postValue(dao.getAllContacts());
            }).start();
            setValue(new LinkedList<>());
        }

        @Override
        public void onActive() {
            super.onActive();
            new Thread(() -> {
                contactListData.postValue(dao.getAllContacts());
            }).start();
        }
    }

    public ContactListData getContacts() { return contactListData; }

    public void add(final ContactItem contactItem){
        //api.add(contactItem);
    }

    public void delete(final ContactItem contactItem){
        //api.delete(contactItem);
    }
}
