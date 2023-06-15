package com.example.whatsup.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsup.R;
import com.example.whatsup.entities.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    List<Contact> contacts;

    private class ViewHolder{
        ImageView profile;
        TextView name;
        TextView message;
        TextView date;
    }

    public ContactAdapter(List<Contact> posts) {
        this.contacts = posts;
    }

    @Override
    public int getCount() {
        if(contacts == null) return 0;
        return contacts.size();
    }

    @Override
    public Object getItem(int position) { return contacts.get(position); }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.profile = convertView.findViewById(R.id.contactImage);
            viewHolder.name = convertView.findViewById(R.id.contactName);
            viewHolder.message = convertView.findViewById(R.id.lastMessage);
            viewHolder.date = convertView.findViewById(R.id.lastDate);

            convertView.setTag(viewHolder);
        }

        Contact p = contacts.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.profile.setImageResource(p.getProfilePic());
        viewHolder.name.setText(p.getDisplayName());
        viewHolder.message.setText(p.getLastMessage());
        viewHolder.date.setText(p.getLastDate());

        return convertView;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}