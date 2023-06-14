package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsup.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toRegister = (TextView) findViewById(R.id.toRegister);
        toRegister.setOnClickListener(this);
        final EditText usernameEditText = findViewById(R.id.usernameLogin);
        final EditText passwordEditText = findViewById(R.id.passwordLogin);




        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    usernameEditText.setHint("");
                } else {
                    usernameEditText.setHint(R.string.username);
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordEditText.setHint("");
                } else {
                    passwordEditText.setHint(R.string.password);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v == toRegister){
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }
}