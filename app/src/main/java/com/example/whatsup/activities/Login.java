package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.databinding.ActivityLoginBinding;
import com.example.whatsup.viewmodels.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.toRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });
        binding.usernameLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.usernameLogin.setHint("");
                } else {
                    binding.usernameLogin.setHint(R.string.username);
                }
            }
        });
        binding.passwordLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.passwordLogin.setHint("");
                } else {
                    binding.passwordLogin.setHint(R.string.password);
                }
            }
        });

        binding.loginBtn.setOnClickListener(v -> {
            String username = binding.usernameLogin.getText().toString();
            String password = binding.passwordLogin.getText().toString();
            if(username.equals("")){
                binding.usernameLogin.setError("Please enter your username");
            }
            if(password.equals("")){
                binding.passwordLogin.setError("Please enter your password");
            }
                viewModel.login(username, password, new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            State.token = response.body();
                            State.currentUser = binding.usernameLogin.getText().toString();
                            Intent intent = new Intent(Login.this, Chats.class);
                            startActivity(intent);
                        }
                        else {
                            State.currentUser = "none";
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

                binding.usernameLogin.setText("");
                binding.passwordLogin.setText("");
                binding.usernameLogin.setError("Incorrect username or password");

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
}
