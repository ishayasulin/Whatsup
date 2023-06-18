package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.repositories.ContactRepository;
import com.example.whatsup.repositories.UserRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private String currentUser = State.currentUser;
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

    public String addContact(String username) {
        if(username.equals(currentUser)){
            return "cant add yourself";
        }

        repository.addContact(username);
        repository2.loadUser(State.currentUser);
        return null;
    }
}