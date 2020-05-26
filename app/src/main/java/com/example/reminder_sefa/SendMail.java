package com.example.reminder_sefa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SendMail extends AppCompatActivity {

    EditText to, subject, body;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mail);

        to = findViewById(R.id.editText4);
        subject = findViewById(R.id.editText6);
        body = findViewById(R.id.editText8);

        buttonSend = (Button) findViewById(R.id.button);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to.getText().toString()});
                email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
                email.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(email, "Choose Mail App"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendMail.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SendMail.this, Reminders.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
