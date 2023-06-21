package com.example.whatsup.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.whatsup.entities.Converters;
import com.example.whatsup.entities.Message;
import com.example.whatsup.entities.Sender;

import java.util.List;

@Dao
@TypeConverters(Converters.class) // Add this line
public interface MessageDao {
    @Query("SELECT * FROM message WHERE sender = :senderr ORDER BY id")
    LiveData<List<Message>> getMessages(Sender senderr);

    @Query("SELECT * FROM message WHERE id =:id")
    Message get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> order);

    @Update
    void update(Message... messages);

    @Query("DELETE FROM message")
    void deleteAll();

    @Delete
    void delete(Message... messages);
}