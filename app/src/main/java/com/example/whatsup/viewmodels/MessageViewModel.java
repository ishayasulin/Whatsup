package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;
import com.example.whatsup.repositories.MessageRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends AndroidViewModel {
    public MessageRepository repository;
    private MessageDao messageDao;

    public MessageViewModel(Application application) {
        super(application);
        repository = new MessageRepository(application);
        AppDB db = AppDB.getDatabase(application);
        messageDao = db.messageDao();
    }
    public void updateDao(String id, Callback<List<Message>> callback){
        repository.updateDao(id).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messageDao.deleteAll();
                messageDao.insertAll(response.body());
                callback.onResponse(call, response);

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);
            }
        });
    }

    public LiveData<List<Message>> getMessages() {
        return repository.getMessages();
    }

    public void addMessage(Contact to, String content, Callback<Message> callback) {
        repository.addMessage(to, content).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                messageDao.insert(response.body());
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