package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.repositories.ContactRepository;

import java.util.List;


public class ContactItemViewModel extends ViewModel {
    private ContactRepository mRepository;
    private LiveData<List<ContactItem>> contactItems;

    public ContactItemViewModel() {
        mRepository = new ContactRepository();
        contactItems = mRepository.getAll();
    }

    public LiveData<List<ContactItem>> get() { return contactItems;}

    public void add(ContactItem contact) { mRepository.add(contact);}

    public void delete(ContactItem contact) { mRepository.delete(contact);}

    public void reload() { mRepository.reload();}

    public void updateContactList(List<ContactItem> items) { contactItems = (LiveData<List<ContactItem>>) items; }
}
