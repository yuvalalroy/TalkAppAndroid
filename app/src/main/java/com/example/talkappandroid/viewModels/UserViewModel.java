package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;
import com.example.talkappandroid.repositories.UserRepository;


public class UserViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoggedIn;
    private final MutableLiveData<UserItem> currentUser;
    private UserRepository userRepository;
    private UserItem user;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.isLoggedIn = new MutableLiveData<>();
        this.currentUser = new MutableLiveData<>();
    }

    public void login(UserLogin loginUser) {
        userRepository.login(loginUser, isLoggedIn);
    }

    public void register(UserItem registerUser) {
        userRepository.register(registerUser, isLoggedIn);
    }

    public LiveData<Boolean> checkIfLoggedIn() {
        return isLoggedIn;
    }

//
//    public UserItem get() { return user;}
//
//    public void add(UserItem user) { userRepository.add(user);}
//
//    public void delete(UserItem user) { userRepository.delete(user);}
//
//    public void reload() { userRepository.reload();}

    //public void updateContactList(UserItem user) { contactItems = (LiveData<List<ContactItem>>) items; }
}
