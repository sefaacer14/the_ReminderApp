package com.example.reminder_sefa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Reminders extends AppCompatActivity implements arrayList {

    private myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new myAdapter(rem_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerListener(getApplicationContext(), recyclerView, new RecyclerListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Reminder rem = rem_list.get(position);
                Toast.makeText(getApplicationContext(), rem.getName() + " is selected!", Toast.LENGTH_SHORT).show();

                Button buttonDelete = (Button) findViewById(R.id.btnDelete);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rem_list.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),  "Reminder deleted!", Toast.LENGTH_SHORT).show();

                    }
                });

                Button buttonEdit = (Button) findViewById(R.id.btnEdit);
                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rem_list.remove(position);
                        Intent i = new Intent(Reminders.this, newReminder.class);
                        startActivity(i);
                        finish();
                        adapter.notifyDataSetChanged();
                    }
                });

                Button buttonSend = (Button) findViewById(R.id.btnSend);
                buttonSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Reminders.this, SendMail.class);
                        startActivity(i);
                        finish();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Reminders.this, Main2Activity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}

