package com.example.hackathon_playgroupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


//We used this class as a welcome screen

public class WelcomeScreen extends AppCompatActivity {

    //This is the time that the screen will be displayed
    private static int SPLASH_TIME = 1000;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen_activity);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run (){
                Intent loginIntent = new Intent(WelcomeScreen.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },SPLASH_TIME);
    }
}

