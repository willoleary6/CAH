package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;


public class MainMenu extends AppCompatActivity {
    MediaPlayer buttonClick;
    ImageButton FindAGame,HostAGame,Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        Button FindAGame = (Button) findViewById(R.id.findGame);
        Button HostAGame = (Button) findViewById(R.id.hostGame);
        Button Settings  = (Button) findViewById(R.id.settings);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String name = userInfo.getString("username", "");
        String pwd = userInfo.getString("password", "");
        TextView details = (TextView) findViewById(R.id.userName);
        details.setText(name);
        Toast.makeText(getApplicationContext(),
                "Logged in as '"+name+"'",
                Toast.LENGTH_LONG).show();
        FindAGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             buttonSound();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent toFindAGame = new Intent(MainMenu.this,FindAGame.class);
                        startActivity(toFindAGame);
                    }
                }, 500);
            }
        });
        HostAGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound();
                // Needs its own method later, don't have time to do it now....
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent toFindAGame = new Intent(MainMenu.this,HostAGame.class);
                        startActivity(toFindAGame);
                    }
                }, 500);
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent toFindAGame = new Intent(MainMenu.this,Settings.class);
                        startActivity(toFindAGame);
                    }
                }, 500);
            }
        });
    }

    private void buttonSound() {
        buttonClick = MediaPlayer.create(MainMenu.this, R.raw.click);
        buttonClick.start();
    }
}