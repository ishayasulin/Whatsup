package com.example.whatsup.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.databinding.ActivityRegisterBinding;
import com.example.whatsup.viewmodels.RegisterViewModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private boolean changedPic = false;

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
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
        binding.registerBtn.setOnClickListener(v -> {
            String completed = "";
            EditText usernameEditText = findViewById(R.id.usernameRegister);
            EditText passwordEditText = findViewById(R.id.passwordRegister);
            EditText repeatPasswordEditText = findViewById(R.id.repeatPassword);
            EditText displayNameEditText = findViewById(R.id.displayNameRegister);

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String repeatPassword = repeatPasswordEditText.getText().toString().trim();
            String displayName = displayNameEditText.getText().toString().trim();
            String picture = "";


            // Get the drawable from the ImageView
            Drawable drawable = binding.pictureRegister.getDrawable();

            if (drawable instanceof BitmapDrawable) {
                // Get the bitmap from the drawable
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                // Convert the bitmap to a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                // Convert the byte array to a base64-encoded string
                picture = Base64.encodeToString(byteArray, Base64.DEFAULT);
                completed = "data:image/png;base64," + picture;
                // Use the base64String as needed
            }
            if (!changedPic) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.man);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
                completed = "data:image/png;base64," + base64Image;
            }




            if (username.isEmpty()) {
                // Username is empty, show an error message
                usernameEditText.setError("Please enter a username");
            } else if (displayName.isEmpty()) {
                // Display name is empty, show an error message
                displayNameEditText.setError("Please enter a display name");
            } else if (password.length() >= 8 && password.length() <= 16) {
                if (password.equals(repeatPassword)) {
                    // Password is valid, proceed with registration
                } else {
                    // Passwords do not match, show an error message
                    repeatPasswordEditText.setError("Passwords do not match");
                }
            } else {
                // Invalid password length, show an error message
                passwordEditText.setError("Password length should be between 8 and 16 characters");
            }
            viewModel.register(username, password, displayName, completed, new Callback<Void>() {
                //todo check why with image its crushes
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200) {
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        return;
                    }
                    else if(response.code() == 409){
                        binding.usernameRegister.setError("username already taken!");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            binding.usernameRegister.setError("username already taken!");
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
            rootLayout.setBackgroundResource(R.drawable.wallpaper2);
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

            // Check the size of the selected image
            long imageSize = getImageSize(selectedImageUri);

            if (imageSize > 5 * 1024 * 1024) {
                // Image size is greater than 5MB, show an error message or handle it as needed
                // For example, you can display a Toast message
                Toast.makeText(this, "Image size exceeds 5MB limit", Toast.LENGTH_SHORT).show();
            } else {
                ImageButton imageButton = findViewById(R.id.pictureRegister);
                imageButton.setImageURI(selectedImageUri);
                changedPic = true;
            }
        }
    }

    private long getImageSize(Uri imageUri) {
        String[] filePathColumn = { MediaStore.Images.Media.SIZE };
        Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
        long size = -1;

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            size = cursor.getLong(columnIndex);
            cursor.close();
        }

        return size;
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