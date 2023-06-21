package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;
import com.example.whatsup.repositories.MessageRepository;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    public MessageRepository repository;
    // LiveData<List<Message>> messages;

    public MessageViewModel(Application application) {
        super(application);
        repository = new MessageRepository(application);
    }

    public LiveData<List<Message>> getMessages(Contact currentContact) {
        return repository.getMessages(currentContact.getId());
    }

    public void addMessage(String from, Contact to, String content) {
        repository.addMessage(to.getId(), content);
    }
}