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

import com.example.whatsup.R;
import com.example.whatsup.State;

public class Settings extends AppCompatActivity {

    private Switch switchMode;
    private EditText portNumber;
    private Button portButton;
    private int currentNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchMode = findViewById(R.id.switchMode);
        portNumber = findViewById(R.id.portNumber);
        portButton = findViewById(R.id.portButton); // Add this line to initialize portButton

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
                } else if (!isChecked && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
                    // Enable light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        portButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click to change port number
                String newPort = portNumber.getText().toString().trim();
                if (!newPort.isEmpty()) {
                    State.server = "10.0.2.2:" + newPort;
                }
            }
        });
    }
}
