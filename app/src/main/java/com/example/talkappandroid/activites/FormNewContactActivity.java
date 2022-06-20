package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.R;
import com.example.talkappandroid.model.Invitation;
import com.example.talkappandroid.repositories.ContactRepository;
import com.example.talkappandroid.viewModels.ContactsViewModel;

import java.util.Objects;

public class FormNewContactActivity extends AppCompatActivity {
    private ContactsViewModel contactsViewModel;
    private Button btnGoBack, btnAddContact;
    private AppDB db;
    private ContactItemDao contactItemDao;
    private EditText etContactServer, etContactName, etContactDisplayName;
    private TextView contactNameError, contactDNError, contactServerError, newContactError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new_contact);
        bindViews();
        setListeners();
        db = AppDB.getContactDBInstance();
        contactItemDao = db.contactItemDao();
    }

    private void bindViews(){
        btnAddContact = findViewById(R.id.btnAddContact);
        btnGoBack = findViewById(R.id.btnGoBack);
        etContactName = findViewById(R.id.editTextContactName);
        etContactDisplayName = findViewById(R.id.editTextContactDisplayname);
        etContactServer = findViewById(R.id.editTextContactServer);
    }

    private void postContact(ContactItem contactItem) {
        contactsViewModel.postContact(contactItem);
        contactsViewModel.getContactResponse().observe(this, res -> {
            if (Objects.equals(res, "ok")) {
                Intent i = new Intent(this, ContactsActivity.class);
                startActivity(i);
                finish();
            } else {
                newContactError = findViewById(R.id.newContactError);
                newContactError.setText(res);
                newContactError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void invitation(ContactItem contactItem) {
        String from = UserTokenDB.getFromEditor(UserTokenDB.getToken()).getUserName();
        String to = etContactName.getText().toString();
        String server = etContactServer.getText().toString();

        Invitation invitation = new Invitation(from, to, server);
        contactsViewModel.postInvitation(invitation);

        contactsViewModel.getInvited().observe(this, res -> {
            if (res) {
                postContact(contactItem);
            } else {
                newContactError = findViewById(R.id.newContactError);
                newContactError.setText("Something went wrong. try again");
                newContactError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setListeners() {
        btnAddContact.setOnClickListener(v -> {
            String id = etContactName.getText().toString();
            String name = etContactDisplayName.getText().toString();
            String server = etContactServer.getText().toString();

            if(validate(id, name, server)) {
                ContactItem contactItem = new ContactItem(id, name, null, null, server);
                contactsViewModel = new ContactsViewModel(new ContactRepository(TalkAppApplication.contactsApi));
                invitation(contactItem);
            }
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
        }
        return true;
    }

    private void clearErrors(){
        if(!etContactName.getText().toString().isEmpty() ||
                !etContactDisplayName.getText().toString().isEmpty() ||
                !etContactServer.getText().toString().isEmpty()){
            contactNameError = findViewById(R.id.textViewContactNameError);
            contactDNError = findViewById(R.id.textViewContactsDisplayNameError);
            contactServerError = findViewById(R.id.textViewContactsServerError);
            newContactError = findViewById(R.id.newContactError);
            contactNameError.setVisibility(TextView.INVISIBLE);
            contactDNError.setVisibility(TextView.INVISIBLE);
            contactServerError.setVisibility(TextView.INVISIBLE);
            newContactError.setVisibility(TextView.INVISIBLE);
        }
    }
}