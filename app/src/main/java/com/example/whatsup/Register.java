package com.example.whatsup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.registerBtn);
        register.setOnClickListener(this);
    }
    public void onImageButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // The user has selected an image
            Uri selectedImageUri = data.getData();

            // Do something with the selected image URI
            // For example, you can display the image in an ImageView
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == register){
            Intent intent = new Intent(this, Chats.class);
            startActivity(intent);
        }
    }
}