package com.example.reminder_sefa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class Main2Activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sefa);

      //  Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        newRemind();
        reminders();
        settings();

    }

    public void newRemind() {
        Button newRemind;

        newRemind = (Button) findViewById(R.id.btnNewRemind);
        newRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, newReminder.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void reminders() {
        Button btnReminders;
        btnReminders = (Button) findViewById(R.id.btnRimenders);
        btnReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, Reminders.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void settings() {
        Button btnSettings;
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, Settings.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }
}

