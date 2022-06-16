package com.example.talkappandroid.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.talkappandroid.R;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister, btnLogin;
    private ImageView btnSettings;
    private String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettings = findViewById(R.id.go_to_settings);
        btnRegister = findViewById(R.id.btn_go_to_register);
        btnLogin = findViewById(R.id.btn_go_to_login);
        server = "http://10.0.2.2:7201/api/";

        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            i.putExtra("defaultServer", server);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("defaultServer", server);
            startActivity(i);
        });

        btnSettings.setOnClickListener(v -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            server = bundle.getString("server");
    }
}