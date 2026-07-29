package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView slogan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);

        Animation zoom= AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        Animation fade= AnimationUtils.loadAnimation(this, R.anim.fade_in);

        new Handler().postDelayed(() -> {
            logo.setVisibility(View.VISIBLE);
            logo.startAnimation(zoom);
        },100);

        new Handler().postDelayed(() -> {
            slogan.setVisibility(View.VISIBLE);
            slogan.startAnimation(fade);
        },3500);


        new Handler().postDelayed(() ->{
                Intent intent = new Intent(SplashActivity.this, LogIn.class);
                startActivity(intent);
                finish();
            },6000);
        }
    }

    