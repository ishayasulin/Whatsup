package com.example.whatsup;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull private String username;
    private String displayName;
    private int profilePic;

    public Contact(@NonNull String username, String displayName, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
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
