package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talkappandroid.R;
import com.example.talkappandroid.activites.LoginActivity;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserRegister;
import com.example.talkappandroid.repositories.UserRepository;
import com.example.talkappandroid.viewModels.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText eUsername, ePassword, eVerifyPassword, eDisplayName;
    private TextView usernameError, passwordError, verifyPasswordError, displayNameError;
    private Button btnSignUp, btnRegister;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setDB();
        bindViews();
        setListeners();
    }


    public  void setDB(){
        AppDB db = AppDB.getUsersDBInstance();
        userDao = db.userDao();
    }

    private void bindViews(){
        btnSignUp = findViewById(R.id.btnSignUp);
        btnRegister = findViewById(R.id.btnRegister);
        eUsername = findViewById(R.id.editTextTextPersonName);
        ePassword = findViewById(R.id.editTextTextPassword);
        eVerifyPassword = findViewById(R.id.editTextTextVerifyPassword);
        eDisplayName = findViewById(R.id.editTextTextDisplayname);
    }

    private void setListeners(){
        btnSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        btnRegister.setOnClickListener(v -> {
            String username = eUsername.getText().toString();
            String password = ePassword.getText().toString();
            String verifyPassword = eVerifyPassword.getText().toString();
            String displayName = eDisplayName.getText().toString();

            if(validate(username, password, verifyPassword, displayName)){
                UserRepository userRepository = new UserRepository();
                UserViewModel userViewModel = new UserViewModel(userRepository);
                UserItem userItem = new UserItem(username, password, displayName);
                userViewModel.register(userItem);
                userViewModel.checkIfLoggedIn().observe(this, answer -> {
                    if (answer) {
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Either user exists or server is not responsive.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        removeErrors();
    }

    private boolean validatePassword(String passwordField) {
        if(passwordField.length() < 5)
            return false;

        int countDigits = 0 , countLetters = 0;

        for(int i = 0; i < passwordField.length(); i++){
            char c = passwordField.charAt(i);
            if(Character.isAlphabetic(c))
                countLetters++;
            if(Character.isDigit(c))
                countDigits++;
            if(countDigits > 1 && countLetters > 1)
                return true;
        }
        return false;
    }

    private boolean validate(String usernameField, String passwordField, String verifyPasswordField,
                             String displayNameField) {

        if (usernameField.isEmpty() || passwordField.isEmpty() || verifyPasswordField.isEmpty()
                || displayNameField.isEmpty()) {
            if (usernameField.isEmpty()) {
                usernameError = findViewById(R.id.UsernameError);
                usernameError.setTextColor(getResources().getColor(R.color.red));
                usernameError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }

            if (passwordField.isEmpty()) {
                passwordError = findViewById(R.id.PasswordError);
                passwordError.setText(getResources().getString(R.string.login_PasswordError));
                passwordError.setTextColor(getResources().getColor(R.color.red));
                passwordError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Password is required!", Toast.LENGTH_SHORT).show();
            }

            if (verifyPasswordField.isEmpty()) {
                verifyPasswordError = (TextView) findViewById(R.id.VerifyPasswordError);
                passwordError.setText(getResources().getString(R.string.register_verifyPasswordError));
                verifyPasswordError.setTextColor(getResources().getColor(R.color.red));
                verifyPasswordError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Password is required!", Toast.LENGTH_SHORT).show();
            }

            if (displayNameField.isEmpty()) {
                displayNameError = (TextView) findViewById(R.id.DisplayNameError);
                displayNameError.setTextColor(getResources().getColor(R.color.red));
                displayNameError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Password is required!", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            if(!validatePassword(passwordField)){
                passwordError = (TextView) findViewById(R.id.PasswordError);
                passwordError.setTextColor(getResources().getColor(R.color.black));
                passwordError.setText(getResources().getString(R.string.register_validPasswordError));
                passwordError.setVisibility(TextView.VISIBLE);
                return false;
            }

            if(!passwordField.equals(verifyPasswordField)){
                verifyPasswordError = (TextView) findViewById(R.id.VerifyPasswordError);
                verifyPasswordError.setTextColor(getResources().getColor(R.color.red));
                verifyPasswordError.setText(getResources().getString(R.string.register_validVerifyPasswordError));
                verifyPasswordError.setVisibility(TextView.VISIBLE);
                return false;
            }

            String token = UserTokenDB.getToken();
            if(UserTokenDB.getFromEditor(token) != null){
                Toast.makeText(RegisterActivity.this, "User already exists. Please login", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void removeErrors() {
        eUsername.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfFieldsEmpty();
            }
        });

        ePassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfFieldsEmpty();
            }
        });

        eVerifyPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfFieldsEmpty();
            }
        });

        eDisplayName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfFieldsEmpty();
            }
        });
    }

    private void checkIfFieldsEmpty(){
        if(!eUsername.getText().toString().isEmpty() ||
                !ePassword.getText().toString().isEmpty() || !eVerifyPassword.getText().toString().isEmpty() || !eDisplayName.getText().toString().isEmpty()){
            usernameError = findViewById(R.id.UsernameError);
            passwordError = findViewById(R.id.PasswordError);
            verifyPasswordError = findViewById(R.id.VerifyPasswordError);
            displayNameError = findViewById(R.id.DisplayNameError);
            passwordError.setVisibility(TextView.INVISIBLE);
            usernameError.setVisibility(TextView.INVISIBLE);
            displayNameError.setVisibility(TextView.INVISIBLE);
            verifyPasswordError.setVisibility(TextView.INVISIBLE);
        }
    }
}