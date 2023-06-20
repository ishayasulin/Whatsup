package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.adapters.ContactAdapter;
import com.example.whatsup.databinding.ActivityChatsBinding;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.viewmodels.ContactViewModel;

import java.util.List;

public class Chats extends AppCompatActivity {
    private ActivityChatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // assigning ID of the toolbar to a variable

        ContactAdapter adapter = new ContactAdapter(new ContactAdapter.ContactDiff());
        binding.contactList.setAdapter(adapter);
        binding.contactList.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setItemClickListener((v, contact) -> {
////            Intent intent = new Intent(this, VerticalMessagesActivity.class);
////            intent.putExtra("contact", contact);
////            startActivity(intent);
////        });

        //String token = FirebaseService.getToken(this);
        //sendRegistrationToServer(token);

//        Intent serviceIntent = new Intent();
////        serviceIntent.setAction("com.google.firebase.MESSAGING_EVENT");
//        startService(serviceIntent);

        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, (List<Contact> list) -> {
            adapter.submitList(list);
            adapter.notifyDataSetChanged();
        });


        binding.btnAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, AddContact.class);
            startActivity(intent);
        });
        binding.btnLogout.setOnClickListener(view1 -> {
            State.currentUser = "";
            State.token = "";
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
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
//    private void sendRegistrationToServer(String token) {
//        WebServiceAPI api = RetrofitService.getAPI(State.server);
//        api.registerToken(new WebServiceAPI.RegisterTokenPayload(State.currentUser, token)).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Log.d("custom:", "sendRegistrationToServer: " + token);
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                int x;
//            }
//        });
//    }
}

