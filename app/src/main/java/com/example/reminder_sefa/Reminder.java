package com.example.reminder_sefa;

public class Reminder {

    private String name, repeat, tag;
    private int year, month, day, hour, minute;
    private String Data;

    public Reminder() {
    }

    Reminder(String name, int year, int month, int day, int hour, int minute, String repeat, String tag) {

        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.tag = tag;
    }

    String getData() {
        Data = " " + name + "\n " + year + "/" + month + "/" + day + "\n " + hour + ":" + minute + "\n Repeat: " + repeat + " min" + "\n " + tag;
        return Data;
    }

    public String getName() {
        return name;
    }
}

