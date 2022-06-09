package com.example.talkappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.talkappandroid.adapters.ContactsListAdapter;
import com.example.talkappandroid.viewModels.ContactItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private AppDB db;
    private ContactItemDao contactItemDao;
    private List<ContactItem> contacts;
    private FloatingActionButton btnAdd;
    private RecyclerView lstContacts;
    private ContactsListAdapter contactsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

/*        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();*/
        db = AppDB.getInstance();
        contactItemDao = db.contactItemDao();
        contacts = contactItemDao.index();

        lstContacts = findViewById(R.id.lstContacts);
        lstContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        contactsListAdapter = new ContactsListAdapter(this, contacts);
        lstContacts.setAdapter(contactsListAdapter);

        btnAdd = findViewById(R.id.btn_contacts_addContact);
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContactActivity.class);
            startActivity(i);
        });

//        bindViews();
//        setListeners();
//        setAdapter();

//        _contactItems = new ArrayList<>();
//        adapter = new ArrayAdapter<ContactItem>(this, android.R.layout.simple_gallery_item, _contactItems);
//
 //       lvContactsItems.setAdapter(adapter);
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
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, FormNewContactActivity.class);
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
        contacts.clear();
        contacts.addAll(contactItemDao.index());
        contactsListAdapter.setContactItems(contacts);
        contactsListAdapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        /*viewModel = new ContactItemViewModel(getApplicationContext());*/
        ContactItemViewModel viewModel = new ViewModelProvider(this).get(ContactItemViewModel.class);
        //lstContacts.setAdapter(adapter);
        //lstContacts.setLayoutManager(new LinearLayoutManager(this));
        //viewModel.get().observe(this, adapter::setContactItems);
    }

}