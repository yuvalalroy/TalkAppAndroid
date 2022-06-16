package com.example.talkappandroid.activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talkappandroid.R;
import com.example.talkappandroid.adapters.MessagesListAdapter;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.viewModels.MessageItemViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private ContactItemDao contactItemDao;
    private MessageItemDao messageItemDao;
    private ImageView goBackArrow, sendBtn;
    private CircleImageView profilePic;
    private TextView contactName, lastSeen;
    private EditText messageInput;
    private RecyclerView lstMessages;
    private MessagesListAdapter adapter;
    private MessageItemViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setDB();
        bindViews();
        setListeners();
        setTextView();
        setAdapter();
    }

    private void bindViews(){
        goBackArrow = findViewById(R.id.go_back);
        sendBtn = findViewById(R.id.send_msg);
        messageInput = findViewById(R.id.msg_input);
        contactName = (TextView) findViewById(R.id.tvchatName);
        lastSeen = (TextView) findViewById(R.id.tvlastSeen);
        viewModel = new MessageItemViewModel();
        lstMessages = findViewById(R.id.lstMessages);
    }


    private void setListeners() {
        sendBtn.setOnClickListener(v -> {
            if(!messageInput.getText().toString().isEmpty()) {
                DateFormat time = new SimpleDateFormat("HH:mm");
                MessageItem messageItem = new MessageItem(0, messageInput.getText().toString(),
                        time.format(time.format(new Date())), true);
                messageItemDao.insert(messageItem);
                loadMessageItems();
            }
        });

        goBackArrow.setOnClickListener(v -> {
            Intent i = new Intent(this, ContactsActivity.class);
            startActivity(i);
        });
    }

    public  void setDB(){
        AppDB contactDB = AppDB.getContactDBInstance();
        contactItemDao = contactDB.contactItemDao();
        AppDB messagesDB = AppDB.getMessageDBInstance();
        messageItemDao = messagesDB.messageItemDao();
    }

    public void setTextView() {
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("Position");
        ContactItem currentContact = contactItemDao.get(position);
        contactName.setText(currentContact.getName());
        lastSeen.setText(currentContact.getLastdate());
    }

    private void setAdapter(){
        lstMessages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        viewModel.get().observe(this, messageItems -> {
            adapter = new MessagesListAdapter(messageItems);
        });
        lstMessages.setAdapter(adapter);
        loadMessageItems();
    }

    private void loadMessageItems(){
        viewModel.updateContactList(messageItemDao.index());
        viewModel.get().observe(this, messageItems -> {
            adapter.setMessageItems(messageItems);
        });
        adapter.notifyDataSetChanged();
    }
}