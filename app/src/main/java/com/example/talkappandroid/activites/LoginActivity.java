package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talkappandroid.R;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.repositories.UserRepository;
import com.example.talkappandroid.viewModels.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText eUsername, ePassword;
    private TextView usernameError, passwordError;
    private Button btnSignIn, btnLogin;
    private String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        setServer();
        setListeners();

    }

    private void setServer() {
        Bundle bundle = getIntent().getExtras();
        server = bundle.getString("defaultServer");
    }

    private void bindViews(){
        btnSignIn = findViewById(R.id.btnSignIn);
        btnLogin = findViewById(R.id.btnLogin);
        eUsername = findViewById(R.id.editTextTextPersonName);
        ePassword = findViewById(R.id.editTextTextPassword);
    }

    private void validate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                usernameError = (TextView) findViewById(R.id.textViewUsernameError);
                usernameError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Username is required!", Toast.LENGTH_SHORT).show();
            }

            if (password.isEmpty()) {
                passwordError = (TextView) findViewById(R.id.textViewPasswordError);
                passwordError.setVisibility(TextView.VISIBLE);
//                Toast.makeText(Login.this, "Password is required!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        UserRepository userRepository = new UserRepository();
        UserViewModel userViewModel = new UserViewModel(userRepository);
        UserLogin userLogin = new UserLogin(username, password);
        userViewModel.login(userLogin);
        userViewModel.checkIfLoggedIn().observe(this, answer -> {
            if (answer) {
                Intent i = new Intent(this, ContactsActivity.class);
                i.putExtra("Token", UserTokenDB.getToken());
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "User does not exists or server is not responsive.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners(){
        btnSignIn.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v -> {
            validate(eUsername.getText().toString(), ePassword.getText().toString());
        });


        eUsername.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });

        ePassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });

    }

    private void clearErrors(){
        if(!eUsername.getText().toString().isEmpty() || !ePassword.getText().toString().isEmpty()){
            usernameError = findViewById(R.id.textViewUsernameError);
            passwordError = findViewById(R.id.textViewPasswordError);
            passwordError.setVisibility(TextView.INVISIBLE);
            usernameError.setVisibility(TextView.INVISIBLE);
        }
    }
}