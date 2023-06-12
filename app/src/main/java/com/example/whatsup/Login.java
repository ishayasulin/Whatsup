package com.example.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toRegister = (Button) findViewById(R.id.toRegister);
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