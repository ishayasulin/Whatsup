package com.example.whatsup.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {
    private WebServiceAPI api;
    private MessageDao messageDao;
    private ContactDao contactDao;

    public MessageRepository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        messageDao = db.messageDao();
        contactDao = db.contactDao();

        api = RetrofitService.getAPI(State.server);
    }
    public void updateDao(String id){
        api.getMessages("Bearer " + State.token,id).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    messageDao.deleteAll();
                    List<Message> messages = response.body();
                    messageDao.insertAll(messages);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public LiveData<List<Message>> getMessages() {
        return messageDao.getAll();
    }

    public void addMessage(Contact to, String content) {
        api.sendMessage("Bearer " + State.token, to.getId(), new WebServiceAPI.MessagePayload(content)).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 200) {
                    Message message = response.body();
                    messageDao.insert(message);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}