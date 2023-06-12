package com.example.whatsup;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getContacts();
    @Query("SELECT * FROM contact WHERE username = :username")
    Contact get(@NonNull String username);
    @Insert
    void insert(Contact... contacts);
    @Delete
    void delete(Contact... contacts);
}
