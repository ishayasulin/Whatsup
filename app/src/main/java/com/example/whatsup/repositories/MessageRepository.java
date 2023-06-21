package com.example.whatsup.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Message;
import com.example.whatsup.entities.Sender;

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

    public LiveData<List<Message>> getMessages(String with) {
        return messageDao.getMessages(new Sender(with));
    }

    public void addMessage(String id, String content) {
        api.sendMessage("Bearer " + State.token, id, new WebServiceAPI.MessagePayload(content)).enqueue(new Callback<Message>() {
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