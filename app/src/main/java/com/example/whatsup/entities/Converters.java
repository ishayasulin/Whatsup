package com.example.whatsup.entities;

import androidx.room.TypeConverter;

public class Converters {
    // Type converters for User class
    @TypeConverter
    public static String fromUser(User user) {
        // Convert User object to a string representation for storage
        return user.getUsername() + "," + user.getDisplayName() + "," + user.getProfilePic();
    }

    @TypeConverter
    public static User toUser(String userString) {
        // Convert the stored string back to a User object
        String[] parts = userString.split(",");
        String username = parts[0];
        String displayName = parts[1];
        String profilePic = parts[2];

        return new User(username, displayName, profilePic);
    }

    // Type converters for Sender class
    @TypeConverter
    public static String fromSender(Sender sender) {
        // Convert Sender object to a string representation for storage
        return sender.getUsername();
    }

    @TypeConverter
    public static Sender toSender(String senderString) {
        // Convert the stored string back to a Sender object
        return new Sender(senderString);
    }

    // Type converters for Message class
    @TypeConverter
    public static String fromMessage(Message message) {
        if(message == null){
            return "";
        }
        // Convert Message object to a string representation for storage
        return message.getId() + "," + message.getCreated() + "," + fromSender(message.getSender()) + "," + message.getContent();
    }

    @TypeConverter
    public static Message toMessage(String messageString) {
        // Convert the stored string back to a Message object
        if(messageString.equals("")){
            return null;
        }
        String[] parts = messageString.split(",");
        int id = Integer.parseInt(parts[0]);
        String created = parts[1];
        Sender sender = toSender(parts[2]);
        String content = parts[3];

        return new Message(id, created, sender, content);
    }
}
