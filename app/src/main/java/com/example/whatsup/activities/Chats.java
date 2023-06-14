package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.whatsup.R;
import com.example.whatsup.adapters.ContactAdapter;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Chats extends AppCompatActivity {
    private AppDB db;
    private ListView lvContacts;
    private List<Contact> dbContacts;
    private ContactAdapter adapter;
    private ContactDao contactDao;
    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        add = findViewById(R.id.btnAdd);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        contactDao = db.contactDao();
        handlePosts();

        add.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddContact.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handlePosts();
        handlePosts();
    }

    private void handlePosts() {
        lvContacts = findViewById(R.id.lvContacts);
        adapter = new ContactAdapter(dbContacts);

        loadPosts();
        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Contact contact = dbContacts.get(i);
            contactDao.delete(contact);
            dbContacts.remove(i);
            handlePosts();
            return true;
        });

    }

    private void loadPosts() {
        dbContacts = contactDao.getContacts();
        adapter.notifyDataSetChanged();
    }
}
