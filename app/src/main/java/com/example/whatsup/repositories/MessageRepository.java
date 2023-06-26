package com.example.whatsup.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;

import java.util.List;

import retrofit2.Call;

public class MessageRepository {
    private WebServiceAPI api;
    private MessageDao messageDao;

    public MessageRepository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        messageDao = db.messageDao();
        api = RetrofitService.getAPI(State.server);
    }
    public Call<List<Message>> updateDao(String id){
       return api.getMessages("Bearer " + State.token, id);
    }
    public LiveData<List<Message>> getMessages() {
        return messageDao.getAll();
    }

    public Call<Message> addMessage(Contact to, String content) {
        return api.sendMessage("Bearer " + State.token ,to.getId() ,new WebServiceAPI.MessagePayload(content));
    }
}