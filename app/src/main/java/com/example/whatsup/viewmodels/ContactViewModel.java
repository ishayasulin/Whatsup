package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.repositories.ContactRepository;
import com.example.whatsup.repositories.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    private UserRepository repository2;

    private final LiveData<List<Contact>> allContacts;

    public ContactViewModel(Application application) {
        super(application);
        repository = new ContactRepository(application);
        repository2 = UserRepository.getInstance(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void addContact(String username, Callback<Contact> callback) {
        repository.addContact(username).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                callback.onResponse(call, response);
                repository2.loadUser(State.currentUser);
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);
            }
        });
    }
}