package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talkappandroid.R;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.repositories.UserRepository;
import com.example.talkappandroid.viewModels.UserViewModel;

public class SettingsActivity extends AppCompatActivity {

    private EditText server;
    private TextView serverError;
    private Button btnGoBack, btnApply, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bindViews();
        setListeners();
    }

    private void bindViews(){
        btnGoBack = findViewById(R.id.btn_go_back_main);
        btnLogout = findViewById(R.id.btnLogout);
        btnApply = findViewById(R.id.btnApply);
        server = findViewById(R.id.etServer);
    }

    private void setListeners(){
        btnGoBack.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            clearErrors();
        });

        btnLogout.setOnClickListener(v -> {
            UserTokenDB.setToken(null);
            UserTokenDB.SERVER_URL = "http://10.0.2.2:7201/api/";
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            clearErrors();
        });

        btnApply.setOnClickListener(v -> {
            if(validate(server.getText().toString())){
                UserTokenDB.SERVER_URL = server.getText().toString();
                Toast.makeText(SettingsActivity.this, "Changed server-url successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                server.setText("");
                startActivity(i);
            }
            clearErrors();
        });

        server.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearErrors();
            }
        });
    }

    private boolean validate(String server) {
        if (server.isEmpty()) {
            serverError = findViewById(R.id.tvServer);
            serverError.setVisibility(TextView.VISIBLE);
            return false;
        }
        if (server.contains("http")) {
            Toast.makeText(SettingsActivity.this, "Server should not contain http/s", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!server.contains(":")) {
            Toast.makeText(SettingsActivity.this, "Server should provide a port!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearErrors(){
        if(!server.getText().toString().isEmpty()){
            serverError = findViewById(R.id.tvServer);
            serverError.setVisibility(TextView.INVISIBLE);
        }
    }
}