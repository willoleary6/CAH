package com.willoleary6gmail.cah;

/**
 * Created by Aaron Dunne.
 */


import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class inGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView header = (TextView) findViewById(R.id.cahHeader);
        TextView leave = (TextView) findViewById(R.id.leaveGame);
        TextView bCard = (TextView) findViewById(R.id.black_card);
        TextView  wCard1= (TextView) findViewById(R.id.white_card1);
        TextView  wCard2= (TextView) findViewById(R.id.white_card2);
        TextView  wCard3= (TextView) findViewById(R.id.white_card3);
        TextView  wCard4= (TextView) findViewById(R.id.white_card4);
        TextView  wCard5= (TextView) findViewById(R.id.white_card5);
        header.setTypeface(font);
        leave.setTypeface(font);
        bCard.setTypeface(font);
        wCard1.setTypeface(font);
        wCard2.setTypeface(font);
        wCard3.setTypeface(font);
        wCard4.setTypeface(font);
        wCard5.setTypeface(font);


    }
}
