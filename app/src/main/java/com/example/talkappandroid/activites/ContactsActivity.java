package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.adapters.ContactsListAdapter;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.viewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements ContactsListAdapter.OnContactClicked{

    private ContactItemDao contactItemDao;
    private ContactsViewModel viewModel;
    private FloatingActionButton btnAdd;
    private RecyclerView lstContacts;
    private ContactsListAdapter adapter;
    private int lastContactPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setDB();
        bindViews();
        setListeners();
        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContactItems();
    }


    private void bindViews(){
        this.lastContactPressed = -1;
        btnAdd = findViewById(R.id.btn_contacts_addContact);
        lstContacts = findViewById(R.id.lstContacts);
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContactActivity.class);
            startActivity(i);
        });
    }

    private void loadContactItems(){
        adapter.clear();
        viewModel.getContactsFromAPI();
        adapter.setContactItems(viewModel.getContacts().getValue());
        adapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        viewModel = new ContactsViewModel(new ContactRepository(TalkAppApplication.contactsApi));
        List<ContactItem> contactItems = viewModel.getContacts().getValue();

        adapter = new ContactsListAdapter(contactItems);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter.setListener(this);

        viewModel.getContacts().observe(this, contacts -> {
            adapter.clear();
            adapter.setContactItems(contacts);
        });
    }

    @Override
    public void onContactClicked(int position) {
        Intent i = new Intent(this, ChatActivity.class);
        ContactItem temp = adapter.getContactItems().get(position);
        i.putExtra("Contact_name", temp.getName());
        i.putExtra("Contact_lastdate", temp.getLastdate());
        i.putExtra("Contact_id", temp.getId());
        startActivity(i);
    }

    public  void setDB(){
        AppDB db = AppDB.getContactDBInstance();
        contactItemDao = db.contactItemDao();
    }
}