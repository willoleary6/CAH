package com.willoleary6gmail.cah;

/**
 * Created by Aaron Dunne.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

public class inGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView bCard = (TextView) findViewById(R.id.black_card);
        TextView  wCard1= (TextView) findViewById(R.id.white_card1);
        TextView  wCard2= (TextView) findViewById(R.id.white_card2);
        TextView  wCard3= (TextView) findViewById(R.id.white_card3);
        TextView  wCard4= (TextView) findViewById(R.id.white_card4);
        TextView  wCard5= (TextView) findViewById(R.id.white_card5);
        bCard.setTypeface(font);
        wCard1.setTypeface(font);
        wCard2.setTypeface(font);
        wCard3.setTypeface(font);
        wCard4.setTypeface(font);
        wCard5.setTypeface(font);


    }
}
