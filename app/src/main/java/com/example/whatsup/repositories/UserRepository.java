package com.example.whatsup.repositories;

import android.app.Application;

import com.example.whatsup.State;
import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private WebServiceAPI api;
    private AppDB db;
    private ContactDao contactDao;
    private MessageDao messagesDao;

    public Call<String> login(String username, String password) {
        return api.login(State.fireToken,new WebServiceAPI.UtilsPayload(username, password));
    }

    public Call<Void> register(String username, String password, String displayName,String profilePic) {
        return api.register(new WebServiceAPI.UtilsRegisterPayload(username, password,displayName, profilePic));
    }
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
                            if(profilePic.length() < 23) continue;
                            String updatedProfilePic;
                            if(profilePic.charAt(13) == 'e'){
                                updatedProfilePic = profilePic.substring(23);
                            }
                            else {
                                updatedProfilePic = profilePic.substring(22);
                            }
                            contact.getUser().setProfilePic(updatedProfilePic);
                        }

                        // Insert the new contacts


                        //sort
                        // sort
                        Collections.sort(contacts, new Comparator<Contact>() {
                            @Override
                            public int compare(Contact contact1, Contact contact2) {
                                Message lastMessage1 = contact1.getLastMessage();
                                Message lastMessage2 = contact2.getLastMessage();

                                // Check if either last message is null
                                if (lastMessage1 == null && lastMessage2 == null) {
                                    return 0; // Both contacts have no last message, consider them equal
                                } else if (lastMessage1 == null) {
                                    return 1; // contact1 has no last message, so it should be considered greater than contact2
                                } else if (lastMessage2 == null) {
                                    return -1; // contact2 has no last message, so it should be considered greater than contact1
                                }

                                // Both contacts have a last message, proceed with date comparison
                                String date1 = lastMessage1.getCreated();
                                String date2 = lastMessage2.getCreated();

                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                                    Date parsedDate1 = dateFormat.parse(date1);
                                    Date parsedDate2 = dateFormat.parse(date2);

                                    // Compare the parsed dates in descending order (latest date first)
                                    return parsedDate2.compareTo(parsedDate1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    // Handle the parsing exception if necessary
                                }

                                return 0;
                            }
                        });


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
        messagesDao = db.messageDao();
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