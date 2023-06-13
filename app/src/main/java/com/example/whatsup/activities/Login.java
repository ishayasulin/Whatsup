package com.example.whatsup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    }


    @Override
    public void onClick(View v) {
        if(v == toRegister){
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }
}