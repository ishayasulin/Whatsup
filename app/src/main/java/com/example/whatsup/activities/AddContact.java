package com.example.whatsup.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.whatsup.R;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.User;

import java.util.Random;

public class AddContact extends AppCompatActivity {
    private AppDB db;
    ContactDao contactDao;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        save = findViewById(R.id.btnSave2);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        contactDao = db.contactDao();
        handleSave();
    }

    private void handleSave() {
        save.setOnClickListener(view -> {
            // Instance of random class
            Random rand = new Random();
            // Setting the upper bound to generate the
            // random numbers in specific range
            int upperbound = 500;
            // Generating random values from 0 - 24
            // using nextInt()
            int int_random = rand.nextInt(upperbound);
            String id = String.valueOf(int_random);
            String displayName = "123"; // Get display name from user input
            String lastMessage = "123"; // Get last message from user input
            String lastDate = "12:00"; // Get last date from user input
            int profilePic = R.drawable.ic_launcher_background; // Get profile picture resource ID from user input

            // Create a new Contact object
            Contact contact = new Contact(id, new User("ishay", displayName, ""), null);

            // Insert the contact into the database
            contactDao.insert(contact);
            finish();
        });
    }
}
