package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.repositories.ContactRepository;

import java.util.List;


public class ContactsViewModel extends ViewModel {
    private ContactRepository mRepository;
    private final MutableLiveData<List<ContactItem>> contactItems;

    public ContactsViewModel(ContactRepository contactRepository) {
        mRepository = contactRepository;
        contactItems = mRepository.getContacts();
    }

    public MutableLiveData<List<ContactItem>> getContacts() { return contactItems; }

    public void add(ContactItem contact) { mRepository.add(contact);}

    public void delete(ContactItem contact) { mRepository.delete(contact);}


    public void updateContactList(List<ContactItem> items) { contactItems.postValue(items); }
}
