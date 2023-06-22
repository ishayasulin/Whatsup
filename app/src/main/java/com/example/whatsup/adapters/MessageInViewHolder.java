package com.example.whatsup.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.databinding.RecycleMessagesByMeBinding;
import com.example.whatsup.entities.Message;

public class MessageInViewHolder extends RecyclerView.ViewHolder{
    private RecycleMessagesByMeBinding binding;

    public MessageInViewHolder(RecycleMessagesByMeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void bind(Message message) {
//        binding.setText(message.getContent());
//        binding.textGchatDateOther.setText(message.getCreated());
        if(message == null){
            return;
        }
        if(message.getCreated() == null){
            return;
        }
        binding.textGchatMessageMe.setText(message.getContent());
        binding.textGchatTimestampMe.setText(message.getCreated().substring(11, 16));
    }
}