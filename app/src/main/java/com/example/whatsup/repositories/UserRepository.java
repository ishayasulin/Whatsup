package com.example.whatsup.repositories;

import android.app.Application;

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

        api.getContacts("Bearer " + State.token).enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    List<Contact> contacts = response.body();
                    if (contacts != null) {
                        // Clear the existing contacts in the database
                        contactDao.deleteAll();

                        for (Contact contact : contacts) {
                            String profilePic = contact.getUser().getProfilePic();
                            if(profilePic.charAt(13) == 'e'){
                                String updatedProfilePic = profilePic.substring(23);
                                contact.getUser().setProfilePic(updatedProfilePic);
                            }
                            else {
                                String updatedProfilePic = profilePic.substring(22);
                                contact.getUser().setProfilePic(updatedProfilePic);
                            }
                        }

                        // Insert the new contacts
                        contactDao.insertAll(contacts);
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
        api = RetrofitService.getAPI(State.server);
        db = AppDB.getDatabase(application);
        contactDao = db.contactDao();
    }
    public void updateServerUrl(String url) {
        // Update the server URL in RetrofitService
        RetrofitService.updateServerUrl(url);
        // Get the updated API instance
        api = RetrofitService.getAPI(url);
    }
    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }
        return instance;
    }


}