package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.repositories.MessageRepository;

import java.util.List;


public class MessageItemViewModel extends ViewModel {
    private MessageRepository mRepository;
    private LiveData<List<MessageItem>> messageItems;

    public MessageItemViewModel() {
        mRepository = new MessageRepository();
        messageItems = mRepository.getAll();
    }

    public LiveData<List<MessageItem>> get() { return messageItems;}

    public void add(MessageItem message) { mRepository.add(message);}

    public void delete(MessageItem message) { mRepository.delete(message);}

    public void reload() { mRepository.reload();}

    public void updateContactList(List<MessageItem> items) { messageItems = (LiveData<List<MessageItem>>) items; }

}
