package com.example.whatsup.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.State;
import com.example.whatsup.databinding.RecycleMessagesByMeBinding;
import com.example.whatsup.databinding.RecycleMessagesByOtherBinding;
import com.example.whatsup.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messageList = new ArrayList<>();
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    private RecyclerView recyclerView;


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            @NonNull RecycleMessagesByMeBinding binding = RecycleMessagesByMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MessageInViewHolder(binding);
        }
        @NonNull RecycleMessagesByOtherBinding binding = RecycleMessagesByOtherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MessageOutViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messageList.get(position).getSender().getUsername().equals(State.currentUser)) {
            ((MessageInViewHolder) holder).bind(messageList.get(position));
        } else {
            ((MessageOutViewHolder) holder).bind(messageList.get(position));
        }
    }

    public void submitList(List<Message> m) {
        int previousItemCount = getItemCount();
        messageList = m;
        notifyDataSetChanged();

        int newItemCount = getItemCount();
        if (recyclerView != null && newItemCount > previousItemCount) {
            recyclerView.scrollToPosition(newItemCount - 1);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSender().getUsername().equals(State.currentUser)) {
            return MESSAGE_TYPE_IN;
        }
        return MESSAGE_TYPE_OUT;
    }

    static public class MessageDiff extends DiffUtil.ItemCallback<Message> {

        @Override
        public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    }
}