package com.example.talkappandroid.repositories;

import android.view.ContextThemeWrapper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.api.UserAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.utils.FirebaseNotification;

import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ContactRepository {

    private final ContactAPI contactsApi;
    private final ContactItemDao dao;
    private ContactListData contactListData;

    public ContactRepository(ContactAPI contactsApi) {
        AppDB db = AppDB.getContactDBInstance();
        this.dao = db.contactItemDao();
        this.contactsApi = contactsApi;
        this.contactListData = new ContactListData();
        this.contactsApi.getContacts(dao, this);
    }

    public void updateContactOnNewMessage(FirebaseNotification update) {
        new Thread(()-> {
            ContactItem contact = dao.get(update.getContactID());
            if (contact == null)
                return;

            contact.setLast(update.getContent());
            contact.setLastdate(update.getDate());
            dao.update(contact);
        }).start();
    }

    class ContactListData extends MutableLiveData<List<ContactItem>> {
        public ContactListData() {
            super();
            setValue(new LinkedList<>());
            new Thread(() -> {
                postValue(dao.getAllContacts(UserTokenDB.getFromEditor(UserTokenDB.getToken()).getUserName()));
            }).start();
        }
    }

    public LiveData<List<ContactItem>> getAll() {
        return this.contactListData;
    }

    public void getContactsFromAPI(){
        contactsApi.getContacts(dao, this);
    }


    public void handleGetContacts(List<ContactItem> contactItemList) {
        for(ContactItem contactItem : contactItemList)
            contactItem.setUsername(UserTokenDB.getFromEditor(UserTokenDB.getToken()).getUserName());
        this.contactListData.setValue(contactItemList);
        new Thread(() -> {
            dao.clear();
            dao.insertAll(contactItemList);
        }).start();
    }

    public void handlePostContacts(String response, MutableLiveData<String> contactResponse, ContactItem contact) {
        if (Objects.equals(response, "ok")){
            List<ContactItem> contactItems = this.contactListData.getValue();
            contactItems.add(contact);
            this.contactListData.setValue(contactItems);
            new Thread(() -> { dao.insert(contact); }).start();
        }
        contactResponse.postValue(response);
    }

    public void postContact(final ContactItem contactItem, MutableLiveData<String> contactResponse){
        contactsApi.postContact(contactItem, contactResponse, this);
    }

    public void postInvitation(final Invitation invitation, MutableLiveData<Boolean> invitationResponse){
        contactsApi.postInvitation(invitation, invitationResponse);
    }

    public void clear() {
        new Thread(dao::clear).start();
    }

}
