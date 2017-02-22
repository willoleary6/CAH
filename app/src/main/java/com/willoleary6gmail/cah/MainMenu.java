package com.willoleary6gmail.cah;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainMenu extends AppCompatActivity {
    MediaPlayer buttonClick;
    ImageButton FindAGame,HostAGame,Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        FindAGame = (ImageButton) findViewById(R.id.imageButton);
        HostAGame = (ImageButton) findViewById(R.id.imageButton2);
        Settings =  (ImageButton) findViewById(R.id.imageButton3);

        FindAGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             buttonSound();
                FindAGame.setImageResource(R.drawable.a_dark);
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
                HostAGame.setImageResource(R.drawable.b_dark);
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
                Settings.setImageResource(R.drawable.c_dark);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent toFindAGame = new Intent(MainMenu.this,loginActivity.class);
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