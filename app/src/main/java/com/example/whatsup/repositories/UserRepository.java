package com.example.whatsup.repositories;

import android.app.Application;

import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private WebServiceAPI api;
    private AppDB db;
    private ContactDao contactDao;

    public Call<String> login(String username, String password) {
        return api.login(new WebServiceAPI.UtilsPayload(username, password));
    }

//    public Call<String> register(String username, String password) {
//        //return api.register(new WebServiceAPI.UtilsPayload(username, password));
//    }
    public void loadUser(String username) {

        api.getContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                // if success
                if (response.code() == 200) {
                    // removing all
                    db.clearAllTables();

                    // getting all contacts
                    List<Contact> contacts = response.body();

                    // inserting all
                    contactDao.insertAll(contacts);

//                    // getting all messages
//                    for (Contact c : contacts) {
//
//                        api.getMessages(c.getId()).enqueue(new Callback<List<Message>>() {
//                            @Override
//                            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                                if (response.code() == 200) {
//                                    List<Message> messages = response.body();
//                                    for (Message m : messages) {
//                                        m.setContact(c.getId());
//                                    }
//                                    messagesDao.insertAll(messages);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<Message>> call, Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private static UserRepository instance;

    private UserRepository(Application application) {
        api = RetrofitService.getAPI("10.0.2.2:5000");
        db = AppDB.getDatabase(application);
    }

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }
        return instance;
    }


}