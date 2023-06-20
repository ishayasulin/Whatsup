package com.example.whatsup.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.whatsup.State;
import com.example.whatsup.repositories.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel {
    private UserRepository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public void register(String username, String password, String displayName, String profilePic, Callback<String> callback) {
        repository.register(username, password, displayName, profilePic).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200) {
                    State.code = 200;
                    callback.onResponse(call, response);
                }
                else{
                    State.code = 409;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure(call, t);
            }
        });
    }

}