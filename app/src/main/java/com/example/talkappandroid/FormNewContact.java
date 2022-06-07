package com.example.talkappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormNewContact extends AppCompatActivity {

    private Button btnGoBack, btnAddContact;
    private AppDB _db;
    private ContactItemDao _contactItem;
    private EditText etContactServer;
    private EditText etContactName;
    private EditText etContactDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new_contact);
        bindViews();
        setListeners();

        _db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "FooDB")
                .allowMainThreadQueries()
                .build();
        _contactItem = _db.contactItemDao();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        btnAddContact = findViewById(R.id.btnAddContact);
        btnGoBack = findViewById(R.id.btnGoBack);
        etContactName = findViewById(R.id.editTextContactName);
        etContactDisplayName = findViewById(R.id.editTextContactDisplayname);
        etContactServer = findViewById(R.id.editTextContactServer);
    }

    private void setListeners() {

        btnAddContact.setOnClickListener(v -> {
            ContactItem contactItem = new ContactItem(0, etContactName.getText().toString(), etContactName.getText().toString());
//            Intent i = new Intent(this, Contacts.class);
//            startActivity(i);
            _contactItem.insert(contactItem);
            finish();
        });

        btnGoBack.setOnClickListener(v -> {
            Intent i = new Intent(this, Contacts.class);
            startActivity(i);
        });
    }
}