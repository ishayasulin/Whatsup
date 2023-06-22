package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.adapters.MessageListAdapter;
import com.example.whatsup.databinding.ActivityMessagesBinding;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.viewmodels.MessageViewModel;

public class MessagesActivity extends AppCompatActivity {

    private ActivityMessagesBinding binding;
    private MessageViewModel viewModel;
    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        viewModel.updateDao(currentContact.getId());
        currentContact = (Contact) getIntent().getSerializableExtra("contact");

        MessageListAdapter adapter = new MessageListAdapter();
        binding.messagesList.setAdapter(adapter);
        binding.messagesList.setLayoutManager(new LinearLayoutManager(this));
        binding.toChats.setOnClickListener(v -> {
            finish();
        });
        binding.contactName.setText(currentContact.getUser().getUsername());
        //binding.imageView2.setImageResource(currentContact.getUser().getProfilePic());
        //convert to imageView

        viewModel.getMessages(currentContact).observe(this, list -> {
            adapter.submitList(list);
            binding.messagesList.scrollToPosition(adapter.getItemCount() - 1);
        });

        binding.textInputMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_GO) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        binding.buttonGchatSend.setOnClickListener(v -> {
            sendMessage();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {
        String from = State.currentUser;
        String content = binding.textInputMessage.getText().toString();
        if (content.isEmpty()) {
            return;
        }
        viewModel.addMessage(currentContact, content);

        binding.textInputMessage.setText("");
    }
}