package com.example.whatsup.adapters;

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
        binding.lastMessage.setText(contact.getLastMessage().getContent());
        binding.contactName.setText(contact.getUser().getDisplayName());
        String messageISO = contact.getLastMessage().getCreated();
        if (messageISO == null) {
            binding.lastDate.setText("");
            return;
        }
        binding.lastDate.setText(messageISO.substring(11, 16));





        ///here convert the picture from 64base to imageView
    }
}