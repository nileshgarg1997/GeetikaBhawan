package com.myapp.geetikabhawan.packages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapp.geetikabhawan.R;

public class BudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_budget);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Budget");
    }
}