package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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

