package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.repositories.UserRepository;

import java.util.List;


public class UserViewModel extends ViewModel {
    private UserRepository mRepository;
    private UserItem user;

    public UserViewModel() {
        mRepository = new UserRepository();
    }

    public UserItem get() { return user;}

    public void add(UserItem user) { mRepository.add(user);}

    public void delete(UserItem user) { mRepository.delete(contact);}

    public void reload() { mRepository.reload();}

    public void updateContactList(UserItem user) { contactItems = (LiveData<List<ContactItem>>) items; }
}
