package com.example.whatsup.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.example.whatsup.entities.Message;
import com.example.whatsup.repositories.UserRepository;
import com.example.whatsup.viewmodels.MessageViewModel;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {

    private ActivityMessagesBinding binding;
    private UserRepository repository;
    private MessageViewModel viewModel;
    private Contact currentContact;
    private MessageListAdapter adapter;
    private io.socket.client.Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://" + State.server);
        } catch (URISyntaxException e) {
            Log.e("SocketConnection", "Socket URI syntax exception: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mSocket.on("new message", onNewMessage);
        initializeSocket();
        if (!mSocket.connected()) {
            mSocket.connect();
            Log.e("SocketConnection", "Failed to connect to the server");
        }
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = UserRepository.getInstance(getApplication());
        adapter = new MessageListAdapter();
        binding.messagesList.setAdapter(adapter);
        binding.messagesList.setLayoutManager(new LinearLayoutManager(this));
        currentContact = (Contact) getIntent().getSerializableExtra("contact");
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        viewModel.updateDao(currentContact.getId(), new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> m = response.body();
                Collections.reverse(m);
                adapter.submitList(m);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        binding.toChats.setOnClickListener(v -> {
            repository.loadUser(State.currentUser);
            finish();
        });
        binding.contactName.setText(currentContact.getUser().getUsername());
        String base64 = currentContact.getUser().getProfilePic();
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.imageView2.setImageBitmap(decodedByte);



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
        String content = binding.textInputMessage.getText().toString();
        if (content.isEmpty()) {
            return;
        }
        viewModel.addMessage(currentContact,content, new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                viewModel.updateDao(currentContact.getId(), new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        List<Message> m = response.body();
                        Collections.reverse(m);
                        adapter.submitList(m);
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                attemptSend(currentContact.getId());
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
            }
        });

        binding.textInputMessage.setText("");
    }
    private void attemptSend(String ID) {
        mSocket.emit("send_message", ID);
    }
    private Emitter.Listener onNewMessage = args -> {
        String id = (String) args[0];
        if(id == currentContact.getId()){
            int x;
        }
    };
    private void initializeSocket() {
        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.reconnection = true;
        options.reconnectionDelay = 2000;
        options.reconnectionDelayMax = 5000;
        try {
            mSocket = IO.socket("http://" + State.server);
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            if(!mSocket.connected()){
                mSocket.connect();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "Socket Connected!");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onConnectError");
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onDisconnect");
           if(!mSocket.connected()){
                mSocket.connect();
            }
        }
    };
}