package com.example.reminder_sefa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import static android.media.RingtoneManager.TYPE_ALARM;
import static android.media.RingtoneManager.TYPE_NOTIFICATION;


public class Settings extends AppCompatActivity {

    Button light, dark;
    Button btn;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        light = findViewById(R.id.btnLight);
        dark = findViewById(R.id.btnDark);

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.LightTheme);
            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme(R.style.DarkTheme);
            }
        });

        btn = findViewById(R.id.btnSelRingtone);
        txtView = findViewById(R.id.tvRingtone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Intent to select Ringtone.
                Uri currentTone = RingtoneManager.getActualDefaultRingtoneUri(Settings.this, TYPE_NOTIFICATION);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                //intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(sharedPreferences.getString("path"))); //bu sekilde
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                //intent.putExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

                Log.i("settings", String.valueOf(currentTone));

                startActivityForResult(intent, 999);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getSharedPreferences("deneme2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (requestCode == 999 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            txtView.setText("From :" + uri.getPath());
            editor.putString("NAME", String.valueOf(uri));
            editor.apply();
            Log.i("settings", String.valueOf(uri));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Settings.this, Main2Activity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}