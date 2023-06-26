package com.example.whatsup.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.databinding.ChatBinding;
import com.example.whatsup.entities.Contact;

class ContactViewHolder extends RecyclerView.ViewHolder {
    private final ChatBinding binding;

    public ContactViewHolder(ChatBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Contact contact) {
        if(contact.getLastMessage() == null){
            binding.lastMessage.setText("");
            binding.lastDate.setText("");
        }
        else {
            binding.lastMessage.setText(contact.getLastMessage().getContent());
            String messageISO = contact.getLastMessage().getCreated();
            binding.lastDate.setText(messageISO.substring(11, 16));
        }
        binding.contactName.setText(contact.getUser().getDisplayName());
        String s = contact.getUser().getProfilePic();
        if(s.length() < 23) return;

        binding.contactImage.setImageBitmap(decodeBase64(s));



    }
    public static Bitmap decodeBase64(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}