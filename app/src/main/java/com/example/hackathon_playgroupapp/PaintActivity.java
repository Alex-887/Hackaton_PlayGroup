package com.example.hackathon_playgroupapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PaintActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintView paintView = new PaintView(this);
        setContentView(paintView);
    }
}
