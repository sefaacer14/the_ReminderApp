package com.example.reminder_sefa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class newReminder extends AppCompatActivity implements arrayList {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView displayDate, displayHour;
    Context context = this;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreminder);

        createNotificationChannel();

        final EditText reminderName = (EditText) findViewById(R.id.editText1);
        final EditText repeat = (EditText) findViewById(R.id.editText3);
        displayDate = (TextView) findViewById(R.id.textView2);
        displayHour = (TextView) findViewById(R.id.textView3);

        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(newReminder.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.etiket));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final int[] year1 = new int[1];
        final int[] month1 = new int[1];
        final int[] day1 = new int[1];
        final int[] hour1 = new int[1];
        final int[] minute1 = new int[1];


        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year1[0] = cal.get(Calendar.YEAR);
                month1[0] = cal.get(Calendar.MONTH);
                day1[0] = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(newReminder.this, dateSetListener, year1[0], month1[0], day1[0]);
                dialog.show();
            }
        });

        final int[] year2 = new int[1];
        final int[] month2 = new int[1];
        final int[] day2 = new int[1];
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(String.valueOf(getApplicationContext()), "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date2 = month + "/" + dayOfMonth + "/" + year;
                displayDate.setText(date2);
                year2[0] = year;
                month2[0] = month;
                day2[0] = dayOfMonth;
            }
        };

        final int[] hour = new int[1];
        final int[] min = new int[1];
        displayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                hour1[0] = cal.get(Calendar.HOUR_OF_DAY);
                minute1[0] = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        displayHour.setText(hourOfDay + ":" + minute);
                        hour[0] = hourOfDay;
                        min[0] = minute;
                    }
                }, hour1[0], minute1[0], android.text.format.DateFormat.is24HourFormat(context));
                timePickerDialog.show();
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.btnEdit2);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                final String remName = (String) reminderName.getText().toString();
                final String remRepeat = (String) repeat.getText().toString();
                final String tag = (String) spinner.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), "Reminder Set!", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("deneme", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME", remName);
                editor.putInt("YEAR", year2[0]);
                editor.putInt("MONTH", month2[0]);
                editor.putInt("DAY", day2[0]);
                editor.putInt("HOUR", hour[0]);
                editor.putInt("MIN", min[0]);
                editor.putString("REPEAT", remRepeat);
                editor.putString("TAG", tag);
                editor.apply();

                Intent intent = new Intent(newReminder.this, myChannel.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(newReminder.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.YEAR, year2[0]);
                cal.set(Calendar.MONTH, month2[0] - 1);
                cal.set(Calendar.DAY_OF_MONTH, day2[0]);
                cal.set(Calendar.HOUR_OF_DAY, hour[0]);
                cal.set(Calendar.MINUTE, min[0]);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                long remRepeat2 = Long.parseLong(remRepeat.trim());
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), remRepeat2 * 1000 * 60, pendingIntent);
                Log.i("newReminder", "123");

                prepareData();

                Intent i = new Intent(newReminder.this, Reminders.class);
                startActivity(i);
                //finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(newReminder.this, Main2Activity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public static final String CHANNEL_ID = "channel1";

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel_id", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is channel");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void prepareData() {
        SharedPreferences sharedPreferences = getSharedPreferences("deneme", Context.MODE_PRIVATE);
        String Name2 = sharedPreferences.getString("NAME", "Kayıt Yok");
        int year2 = sharedPreferences.getInt("YEAR", 0);
        int month2 = sharedPreferences.getInt("MONTH", 0);
        int day2 = sharedPreferences.getInt("DAY", 0);
        int hour2 = sharedPreferences.getInt("HOUR", 0);
        int min2 = sharedPreferences.getInt("MIN", 0);
        String Repeat2 = sharedPreferences.getString("REPEAT", "0");
        String tag2 = sharedPreferences.getString("TAG", "0");

        Reminder rm = new Reminder(Name2, year2, month2, day2, hour2, min2, Repeat2, tag2);
        rem_list.add(rm);
    }
}
