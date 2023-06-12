package com.example.whatsup;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


public class AddContact extends AppCompatActivity {
    private AppDB db;
    private Contact contact;
    ContactDao contactDao;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        save = (Button) findViewById(R.id.btnSave2);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "FooDB")
                .allowMainThreadQueries().build();
        contactDao = db.contactDao();
        handleSave();
    }

    private void handleSave() {
        save.setOnClickListener(view -> {
            //TODO - save contact
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}