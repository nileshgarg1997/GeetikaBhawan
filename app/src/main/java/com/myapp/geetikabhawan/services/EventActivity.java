package com.myapp.geetikabhawan.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapp.geetikabhawan.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event");
    }
}