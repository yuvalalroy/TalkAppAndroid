package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.adapters.ContactsListAdapter;
import com.example.talkappandroid.viewModels.ContactItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsActivity extends AppCompatActivity implements ContactsListAdapter.OnContactClicked{

    private ContactItemDao contactItemDao;
    private ContactItemViewModel viewModel;
    private FloatingActionButton btnAdd;
    private RecyclerView lstContacts;
    private ContactsListAdapter adapter;


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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContactItems();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void bindViews(){
        btnAdd = findViewById(R.id.btn_contacts_addContact);
        lstContacts = findViewById(R.id.lstContacts);
        viewModel = new ContactItemViewModel();
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContactActivity.class);
            startActivity(i);
        });
    }

    private void loadContactItems(){
        viewModel.updateContactList(contactItemDao.index());
        viewModel.get().observe(this, contactItems -> {
            adapter.setContactItems(contactItems);
        });
        adapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        lstContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        viewModel.get().observe(this, contactItems -> {
            adapter = new ContactsListAdapter(contactItems);
        });

        lstContacts.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onContactClicked(int position) {
        /*contacts.get(position);*/
        Intent i = new Intent(this, ChatActivity.class);
        startActivity(i);
    }

    public  void setDB(){
        AppDB db = AppDB.getContactDBInstance();
        contactItemDao = db.contactItemDao();
    }
}