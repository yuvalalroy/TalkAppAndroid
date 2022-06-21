package com.example.talkappandroid.activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.adapters.MessagesListAdapter;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.MessageItemDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.Transfer;
import com.example.talkappandroid.repositories.MessageRepository;
import com.example.talkappandroid.viewModels.MessageItemViewModel;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import androidx.core.widget.NestedScrollView;

public class ChatActivity extends AppCompatActivity {

    private ContactItemDao contactItemDao;
    private MessageItemDao messageItemDao;
    private ImageView goBackArrow, sendBtn;
    private CircleImageView profilePic;
    private TextView contactName, lastSeen;
    private EditText messageInput;
    private RecyclerView lstMessages;
    private MessagesListAdapter adapter;
    private MessageItemViewModel messageViewModel;
    private NestedScrollView scrollView;
    private String contactID;


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
        contactID = getIntent().getExtras().getString("Contact_id");
        scrollView = findViewById(R.id.refreshLayout);
        goBackArrow = findViewById(R.id.go_back);
        sendBtn = findViewById(R.id.send_msg);
        messageInput = findViewById(R.id.msg_input);
        contactName = findViewById(R.id.tvchatName);
        lastSeen = findViewById(R.id.tvlastSeen);
        lstMessages = findViewById(R.id.lstMessages);
    }



    private void transferPostMessage(MessageItem messageItem) {
        String from = UserTokenDB.getFromEditor(UserTokenDB.getToken()).getUserName();
        String to = contactID;
        String content = messageInput.getText().toString();

        Transfer transfer = new Transfer(from, to, content);
        messageViewModel.postTransferMessage(contactID, transfer, messageItem);

        messageViewModel.getMessageResponse().observe(this, res -> {
            if(res){
                adapter.clear();
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                scrollView.postDelayed(() -> scrollView.fullScroll(NestedScrollView.FOCUS_DOWN), 100);
            }
            messageInput.setText("");
        });
    }

    private void setListeners() {
        sendBtn.setOnClickListener(v -> {
            if(!messageInput.getText().toString().isEmpty()) {
                MessageItem messageItem = new MessageItem(messageInput.getText().toString(),
                        "12:30", false);
                transferPostMessage(messageItem);
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
        String contact_lastdate = getIntent().getExtras().getString("Contact_lastdate");
        contactName.setText(getIntent().getExtras().getString("Contact_name"));
        lastSeen.setText("Active now");
    }

    private void setAdapter(){
        scrollView.postDelayed(()->scrollView.fullScroll(NestedScrollView.FOCUS_DOWN),100);
        messageViewModel = new MessageItemViewModel(new MessageRepository(TalkAppApplication.messageApi));
        messageViewModel.getMessagesFromAPI(contactID);
        messageViewModel.getMessages(contactID).observe(this, messages -> {
            Collections.sort(messages, (m1, m2) -> m1.getId() - m2.getId());
            adapter = new MessagesListAdapter(this, messages);
            lstMessages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            lstMessages.setAdapter(adapter);
            scrollView.postDelayed(() -> scrollView.fullScroll(NestedScrollView.FOCUS_DOWN), 100);
        });
    }

}