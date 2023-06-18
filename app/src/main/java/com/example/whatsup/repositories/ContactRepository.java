package com.example.whatsup.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.whatsup.State;
import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {
    private WebServiceAPI api;
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAll();

        api = RetrofitService.getAPI(State.server);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void addContact(String username) {
        api.addContact("Bearer " + State.token, new WebServiceAPI.ContactPayload(username)).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.code() == 200) {
//                    Contact contact = response.body();
//                    contactDao.insert(contact);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}