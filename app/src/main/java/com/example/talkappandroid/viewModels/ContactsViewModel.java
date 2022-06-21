package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.repositories.ContactRepository;

import java.util.List;


public class ContactsViewModel extends ViewModel {
    private ContactRepository mRepository;
    private final MutableLiveData<String> contactResponse;
    private final MutableLiveData<Boolean> invited;
    private LiveData<List<ContactItem>> contacts;

    public ContactsViewModel(ContactRepository contactRepository) {
        mRepository = contactRepository;
        contactResponse = new MutableLiveData<>();
        invited = new MutableLiveData<>();
        this.contacts = mRepository.getAll();
    }

    public LiveData<List<ContactItem>> getContacts(){
        return this.contacts;
    }

    public void getContactsFromAPI(){ mRepository.getContactsFromAPI();}

    public void postContact(ContactItem contact) { mRepository.postContact(contact, contactResponse);}

    public void postInvitation(Invitation invitation) { mRepository.postInvitation(invitation, invited);}

    public MutableLiveData<Boolean> getInvited() { return invited; }

    public MutableLiveData<String> getContactResponse() { return contactResponse; }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.clear();
    }

}
