package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.repositories.MessageRepository;

import java.text.Annotation;
import java.util.List;


public class MessageItemViewModel extends ViewModel {
    private MessageRepository mRepository;
    private final MutableLiveData<Boolean> transferred;
    private final MutableLiveData<Boolean> messageResponse;

    public MessageItemViewModel(MessageRepository messageRepository) {
        mRepository = messageRepository;
        transferred = new MutableLiveData<>();
        messageResponse = new MutableLiveData<>();
    }

    public LiveData<List<MessageItem>> getMessages() { return mRepository.getAll();}

    public void delete(MessageItem message) { mRepository.delete(message);}

    public void getMessagesFromAPI() { mRepository.getMessagesFromAPI(); }

    public void postTransfer(Transfer transfer) { mRepository.postTransfer(transfer, transferred);  }

    public MutableLiveData<Boolean> getTransferred() { return transferred; }

    public void postMessage(MessageItem messageItem) { mRepository.postMessage(messageItem, messageResponse); }

    public MutableLiveData<Boolean> getMessageResponse() {
        return messageResponse;
    }
}
