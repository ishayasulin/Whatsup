package com.example.whatsup.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.whatsup.databinding.ChatBinding;
import com.example.whatsup.entities.Contact;

public class ContactAdapter extends ListAdapter<Contact, ContactViewHolder> {
    private OnListItemClick onListItemClick;

    public ContactAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @NonNull ChatBinding binding = ChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact current = getItem(position);
        holder.itemView.setOnClickListener((View view) -> onListItemClick.onClick(view, current));
        holder.bind(current);
    }

    static public class ContactDiff extends DiffUtil.ItemCallback<Contact> {

        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    }

    public interface OnListItemClick {
        void onClick(View view, Contact contact);
    }

    public void setItemClickListener(OnListItemClick callback) {
        this.onListItemClick = callback;
    }

}