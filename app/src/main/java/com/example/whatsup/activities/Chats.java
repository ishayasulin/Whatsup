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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.adapters.ContactAdapter;
import com.example.whatsup.databinding.ActivityChatsBinding;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.repositories.UserRepository;
import com.example.whatsup.viewmodels.ContactViewModel;

import java.util.List;

public class Chats extends AppCompatActivity {
    private ActivityChatsBinding binding;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        userRepository = UserRepository.getInstance(getApplication());
        // assigning ID of the toolbar to a variable

        ContactAdapter adapter = new ContactAdapter(new ContactAdapter.ContactDiff());
        binding.contactList.setAdapter(adapter);
        binding.contactList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemClickListener((v, contact) -> {
            Intent intent = new Intent(this, MessagesActivity.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        });

        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, (List<Contact> list) -> {
            adapter.submitList(list);
            adapter.notifyDataSetChanged();
            userRepository.loadUser(State.currentUser); // Call the loadUser() function to refresh the data
        });

        binding.btnAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, AddContact.class);
            startActivity(intent);
        });
        binding.btnLogout.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userRepository.loadUser(State.currentUser); // Call the loadUser() function to refresh the data
                swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation
            }
        });
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

