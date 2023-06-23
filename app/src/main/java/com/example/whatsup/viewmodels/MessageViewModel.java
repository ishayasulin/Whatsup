package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;
import com.example.whatsup.repositories.MessageRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends AndroidViewModel {
    public MessageRepository repository;

    public MessageViewModel(Application application) {
        super(application);
        repository = new MessageRepository(application);
    }
    public void updateDao(String id, Callback<List<Message>> callback){
        repository.updateDao(id).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);
            }
        });
    }

    public LiveData<List<Message>> getMessages(Contact currentContact) {
        return repository.getMessages();
    }

    public void addMessage(Contact to, String content, Callback<Message> callback) {
        repository.addMessage(to, content).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);
            }
        });
    }
}