package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.repositories.UserRepository;
import com.example.talkappandroid.utils.FirebaseToken;


public class UserViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoggedIn;
    private final MutableLiveData<UserItem> currentUser;
    private final UserRepository userRepository;
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

    public void notifyToken(FirebaseToken firebaseToken){
        userRepository.notifyToken(firebaseToken);
    }
}
