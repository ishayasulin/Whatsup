package com.example.whatsup.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.whatsup.R;
import com.example.whatsup.State;
import com.example.whatsup.repositories.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Settings extends AppCompatActivity {

    private Switch switchMode;
    private EditText portNumber;
    private Button serverButton;
    private FloatingActionButton back;
    private int currentNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchMode = findViewById(R.id.switchMode);
        portNumber = findViewById(R.id.portNumber);
        back = (FloatingActionButton) findViewById(R.id.returnBtn2);
        serverButton = findViewById(R.id.serverButton); // Add this line to initialize portButton
        State.changed = false;
        switchMode = findViewById(R.id.switchMode);

        // Get the current night mode
        currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switchMode.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
                    // Enable dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    State.changed = true;
                    State.isNight = true;
                    changeBack();
                } else if (!isChecked && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
                    // Enable light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    State.changed = true;
                    State.isNight = false;
                    changeBack();
                }
            }
        });
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click to change port number
                String newServer = portNumber.getText().toString();
                if (!newServer.isEmpty()) {
                    UserRepository.getInstance(getApplication()).updateServerUrl(newServer);
                }
                else{
                    portNumber.setError("please enter a server ip + port");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void changeBack(){
        ConstraintLayout rootLayout = findViewById(R.id.settingsLayout);
        if(State.isNight) {
            rootLayout.setBackgroundResource(R.drawable.wallpaper2);
        }
        else{
            rootLayout.setBackgroundResource(R.drawable.wallpaper2);
        }
    }

}
