package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.whatsup.R;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity {
    private AppDB db;
    private ListView lvPosts;
    private List<String> contacts;
    private List<Contact> dbContacts;
    private ArrayAdapter<String> adapter;
    private ContactDao contactDao;
    private FloatingActionButton add;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        add = (FloatingActionButton) findViewById(R.id.btnAdd);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "FooDB")
                .allowMainThreadQueries().build();

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
        loadPosts();
    }

    private void handlePosts() {
        lvPosts = findViewById(R.id.lvPosts);
        contacts = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts);

        loadPosts();
        lvPosts.setAdapter(adapter);
        lvPosts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            contacts.remove(i);
            Contact contact = dbContacts.remove(i);
            contactDao.delete(contact);
            adapter.notifyDataSetChanged();
            return true;
        });
    }
    private void loadPosts() {
        contacts.clear();
        dbContacts = contactDao.getContacts();
        for (Contact post : dbContacts){
            contacts.add(post.getUsername() + "," + post.getDisplayName());
        }

        adapter.notifyDataSetChanged();
    }
}
