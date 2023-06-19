package com.example.whatsup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {
    private AppCompatButton register;
    private ActivityRegisterBinding binding;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.toLogin);

        tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
        binding.registerBtn.setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.usernameRegister);
            EditText passwordEditText = findViewById(R.id.passwordRegister);
            EditText repeatPasswordEditText = findViewById(R.id.repeatPassword);
            EditText displayNameEditText = findViewById(R.id.displayNameRegister);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String repeatPassword = repeatPasswordEditText.getText().toString().trim();
            String displayName = displayNameEditText.getText().toString().trim();

            if (username.isEmpty()) {
                // Username is empty, show an error message
                usernameEditText.setError("Please enter a username");
            } else if (displayName.isEmpty()) {
                // Display name is empty, show an error message
                displayNameEditText.setError("Please enter a display name");
            } else if (password.length() >= 8 && password.length() <= 16) {
                if (password.equals(repeatPassword)) {
                    // Password is valid, proceed with registration
                    Intent intent = new Intent(this, Chats.class);
                    startActivity(intent);
                } else {
                    // Passwords do not match, show an error message
                    repeatPasswordEditText.setError("Passwords do not match");
                }
            } else {
                // Invalid password length, show an error message
                passwordEditText.setError("Password length should be between 8 and 16 characters");
            }
        });


        final EditText usernameEditText = binding.usernameRegister;
        final EditText passwordEditText = binding.passwordRegister;
        final EditText repeatPasswordEditText = binding.repeatPassword;
        final EditText displayNameEditText = binding.displayNameRegister;

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    usernameEditText.setHint("");
                } else {
                    usernameEditText.setHint(R.string.username);
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordEditText.setHint("");
                } else {
                    passwordEditText.setHint(R.string.password);
                }
            }
        });

        repeatPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    repeatPasswordEditText.setHint("");
                } else {
                    repeatPasswordEditText.setHint(R.string.repeat_password);
                }
            }
        });

        displayNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    displayNameEditText.setHint("");
                } else {
                    displayNameEditText.setHint(R.string.display_name);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConstraintLayout rootLayout = binding.registerLayout;
        if(State.isNight) {
            rootLayout.setBackgroundResource(R.drawable.wallpaper);
        }
        else{
            rootLayout.setBackgroundResource(R.drawable.wallpaper2);
        }

    }

    public void onImageButtonClick(View view) {
        // Launch the file picker or image picker intent here
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ImageButton imageButton = findViewById(R.id.pictureRegister);
            imageButton.setImageURI(selectedImageUri);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}