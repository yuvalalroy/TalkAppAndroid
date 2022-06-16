package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;

public class FormNewContactActivity extends AppCompatActivity {

    private Button btnGoBack, btnAddContact;
    private AppDB db;
    private ContactItemDao contactItemDao;
    private EditText etContactServer, etContactName, etContactDisplayName;
    private TextView contactNameError, contactDNError, contactServerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new_contact);
        bindViews();
        setListeners();
        db = AppDB.getContactDBInstance();
        contactItemDao = db.contactItemDao();
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
            String id = etContactName.getText().toString();
            String name = etContactDisplayName.getText().toString();
            String server = etContactServer.getText().toString();

            if(validate(id, name, server)) {
                ContactItem contactItem = new ContactItem(0, id, name, null, null, server);
                contactItemDao.insert(contactItem);
                finish();
            }

            ContactItem contactItem = new ContactItem(0, etContactName.getText().toString(),
                    etContactDisplayName.getText().toString(), null, null, etContactServer.getText().toString());
            this.contactItemDao.insert(contactItem);
            finish();
        });

        btnGoBack.setOnClickListener(v -> {
            Intent i = new Intent(this, ContactsActivity.class);
            startActivity(i);
        });

        etContactName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });

        etContactDisplayName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });

        etContactServer.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });
    }

    private boolean validate(String contactName, String contactDN, String contactServer) {
        if (contactName.isEmpty() || contactDN.isEmpty() || contactServer.isEmpty()) {
            if (contactName.isEmpty()) {
                contactNameError = (TextView) findViewById(R.id.textViewContactNameError);
                contactNameError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }

            if (contactDN.isEmpty()) {
                contactDNError = (TextView) findViewById(R.id.textViewContactsDisplayNameError);
                contactDNError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }

            if (contactServer.isEmpty()) {
                contactServerError = (TextView) findViewById(R.id.textViewContactsServerError);
                contactServerError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else if (!contactName.isEmpty()) {
            //check if contact already exists in firebase
            return false;
        }

        return true;
    }

    private void clearErrors(){
        if(!etContactName.getText().toString().isEmpty() ||
                !etContactDisplayName.getText().toString().isEmpty() ||
                !etContactServer.getText().toString().isEmpty()){
            contactNameError = (TextView) findViewById(R.id.textViewContactNameError);
            contactDNError = (TextView) findViewById(R.id.textViewContactsDisplayNameError);
            contactServerError = (TextView) findViewById(R.id.textViewContactsServerError);
            contactNameError.setVisibility(TextView.INVISIBLE);
            contactDNError.setVisibility(TextView.INVISIBLE);
            contactServerError.setVisibility(TextView.INVISIBLE);
        }
    }
}