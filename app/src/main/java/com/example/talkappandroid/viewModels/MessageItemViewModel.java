package com.example.talkappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;
import com.example.talkappandroid.repositories.MessageRepository;

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

    public void postTransferMessage(Transfer transfer, MessageItem messageItem) { mRepository.postTransferMessage(transfer, messageItem, messageResponse);  }

    public MutableLiveData<Boolean> getMessageResponse() {
        return messageResponse;
    }
}
