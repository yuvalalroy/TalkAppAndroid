package com.example.talkappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private AppDB _db;
    private ContactItemDao _contactItem;
    private FloatingActionButton btnAdd;
    private List<ContactItem> _contactItems;
    private ArrayAdapter<ContactItem> adapter;
    private ListView lvContactsItems;

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

        _contactItems = new ArrayList<>();
        adapter = new ArrayAdapter<ContactItem>(this, android.R.layout.simple_gallery_item, _contactItems);

        lvContactsItems.setAdapter(adapter);
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
        lvContactsItems = findViewById(R.id.lvContactsList);
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContact.class);
            startActivity(i);
        });

    }

    private void loadContactItems(){
        _contactItems.clear();
        _contactItems.addAll(_contactItem.index());
        adapter.notifyDataSetChanged();
    }
}