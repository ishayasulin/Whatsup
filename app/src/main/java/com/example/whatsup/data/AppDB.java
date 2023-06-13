package com.example.whatsup.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatsup.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}