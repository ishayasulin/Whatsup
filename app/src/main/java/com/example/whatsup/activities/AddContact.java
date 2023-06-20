package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.databinding.ActivityAddContactBinding;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.viewmodels.ContactViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContact extends AppCompatActivity {
    private ActivityAddContactBinding binding;
    private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        binding.btnSave2.setOnClickListener(v -> {
            String username = binding.etContent.getText().toString();
            if(username.equals(State.currentUser)){
                binding.etContent.setError("Cant add yourself!");
                return;
            }

            viewModel.addContact(username, new Callback<Contact>() {
                @Override
                public void onResponse(Call<Contact> call, Response<Contact> response) {
                    if (response.code() == 200) {
                        finish();
                    }
                    else {
                        binding.etContent.setError("Username not found");
                    }
                }

                @Override
                public void onFailure(Call<Contact> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        binding.returnBtn.setOnClickListener(v -> finish());
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