package com.example.whatsup.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull private String username;
    private String displayName;
    private String lastMessage;
    private String lastDate;
    private int profilePic;

    public Contact(@NonNull String username, String displayName, String lastMessage, String lastDate, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.lastMessage = lastMessage;
        this.lastDate = lastDate;
        this.profilePic = profilePic;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }
}