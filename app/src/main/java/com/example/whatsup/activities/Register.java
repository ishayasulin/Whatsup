package com.example.whatsup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.whatsup.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton register;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (AppCompatButton) findViewById(R.id.registerBtn);
        register.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v == register) {
            EditText passwordEditText = findViewById(R.id.passwordRegister);
            EditText repeatPasswordEditText = findViewById(R.id.repeatPassword);

            String password = passwordEditText.getText().toString().trim();
            String repeatPassword = repeatPasswordEditText.getText().toString().trim();

            if (password.length() >= 8 && password.length() <= 16) {
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
        }
    }
}