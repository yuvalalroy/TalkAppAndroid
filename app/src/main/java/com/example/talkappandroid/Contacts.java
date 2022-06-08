package com.example.talkappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.talkappandroid.adapters.ContactsListAdapter;
import com.example.talkappandroid.viewModels.ContactItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private AppDB _db;
    private ContactItemDao _contactItem;
    private FloatingActionButton btnAdd;
    private RecyclerView lstContacts;
    private ContactItemViewModel viewModel;
    private ContactsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        _db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "FooDB")
                  .allowMainThreadQueries()
                  .build();
        _contactItem = _db.contactItemDao();

        bindViews();
        setListeners();
        setAdapter();



//        _contactItems = new ArrayList<>();
//        adapter = new ArrayAdapter<ContactItem>(this, android.R.layout.simple_gallery_item, _contactItems);
//
//        lvContactsItems.setAdapter(adapter);
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
        btnAdd = findViewById(R.id.btnAddContactItem);
        lstContacts = findViewById(R.id.lstContacts);
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContact.class);
            startActivity(i);
        });

        // לא יודעת אם צריך את זה זה העריכה את של האיש קשר
//        lvContactsItems.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(this, FormNewContact.class);
//            intent.putExtra("id", _contactItems.get(i).get_id());
//            startActivity(intent);
//        });
    }

    private void loadContactItems(){
//        _contactItems.clear();
//        _contactItems.addAll(_contactItem.index());
//        adapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        viewModel = new ViewModelProvider(this).get(ContactItemViewModel.class);
        ContactsListAdapter adapter = new ContactsListAdapter(this);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        viewModel.get().observe(this, contactItems -> {
            adapter.setContactItems(contactItems);
        });
    }

}