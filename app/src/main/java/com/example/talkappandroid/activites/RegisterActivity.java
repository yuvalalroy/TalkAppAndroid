package com.example.talkappandroid.activites;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talkappandroid.R;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.repositories.UserRepository;
import com.example.talkappandroid.utils.FirebaseToken;
import com.example.talkappandroid.viewModels.UserViewModel;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText eUsername, ePassword, eVerifyPassword, eDisplayName;
    private TextView usernameError, passwordError, verifyPasswordError, displayNameError;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Button btnSignUp, btnRegister;
    private ImageView addPhotoImage;
    private String image = null;
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
        addPhotoImage = findViewById(R.id.addPhoto);
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
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RegisterActivity.this, instanceIdResult -> {
                            String token = instanceIdResult.getToken();
                            FirebaseToken notificationToken = new FirebaseToken(token);
                            userViewModel.notifyToken(notificationToken);
                        });
                        clearText();
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Either user exists or server is not responsive.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        initLauncher();

        addPhotoImage.setOnClickListener( view -> {
                    Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickImageIntent.setType("image/*");
                    resultLauncher.launch(pickImageIntent);
                }
        );

        removeErrors();

    }

    private void initLauncher() {
        this.resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData()!= null) {
                Intent data = result.getData();
                if(data.getData() != null) {
                    Uri uri = (Uri) data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] b = byteArrayOutputStream.toByteArray();
                        this.image = Base64.encodeToString(b, Base64.DEFAULT);
                    }
                    catch(IOException e) {
                        Toast.makeText(this,"Unable uploading image", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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

    private void clearText() {
        eUsername.setText("");
        ePassword.setText("");
        eVerifyPassword.setText("");
        eDisplayName.setText("");
    }
}