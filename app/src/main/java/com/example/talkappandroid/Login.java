package com.example.talkappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText eUsername, ePassword;
    private TextView usernameError, passwordError;
    private Button btnSignIn, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();
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
        btnSignIn = findViewById(R.id.btnSignIn);
        btnLogin = findViewById(R.id.btnLogin);
        eUsername = findViewById(R.id.editTextTextPersonName);
        ePassword = findViewById(R.id.editTextTextPassword);
    }

    private boolean validate(String usernameField, String passwordField) {
        if (usernameField.isEmpty() || passwordField.isEmpty()) {
            if (usernameField.isEmpty()) {
                usernameError = (TextView) findViewById(R.id.textViewUsernameError);
                usernameError.setTextColor(getResources().getColor(R.color.red));
                usernameError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }

            if (passwordField.isEmpty()) {
                passwordError = (TextView) findViewById(R.id.textViewPasswordError);
                passwordError.setTextColor(getResources().getColor(R.color.red));
                passwordError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Password is required!", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else if (!usernameField.isEmpty()) {
            //check if user exists in firebase
            return false;
        } else if (!passwordField.isEmpty()) {
            // check if password is valid using firebase
            return false;
        }

        return true;
    }

    private void setListeners(){
        btnSignIn.setOnClickListener(v -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v -> {
//            if(validate(eUsername.getText().toString(), ePassword.getText().toString())){
//                Intent i = new Intent(this, Contacts.class);
//                startActivity(i);
//            }

            Intent i = new Intent(this, Contacts.class);
            startActivity(i);
        });


        eUsername.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!eUsername.getText().toString().isEmpty() || !ePassword.getText().toString().isEmpty()){
                    usernameError = (TextView) findViewById(R.id.textViewUsernameError);
                    passwordError = (TextView) findViewById(R.id.textViewPasswordError);
                    passwordError.setVisibility(TextView.INVISIBLE);
                    usernameError.setVisibility(TextView.INVISIBLE);
                }
            }
        });

        ePassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!eUsername.getText().toString().isEmpty() || !ePassword.getText().toString().isEmpty()){
                    usernameError = (TextView) findViewById(R.id.textViewUsernameError);
                    passwordError = (TextView) findViewById(R.id.textViewPasswordError);
                    passwordError.setVisibility(TextView.INVISIBLE);
                    usernameError.setVisibility(TextView.INVISIBLE);
                }
            }
        });

    }
}