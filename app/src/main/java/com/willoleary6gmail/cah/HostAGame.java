package com.willoleary6gmail.cah;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class HostAGame extends AppCompatActivity {
    boolean swtch = true;
    Button main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_agame);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView hostTitle = (TextView) findViewById(R.id.hostHeader);
        TextView createHead = (TextView) findViewById(R.id.createHeader);
        TextView lobbyTitle = (TextView) findViewById(R.id.LobbyName);
        TextView lobbyPass = (TextView) findViewById(R.id.LobbyPassword);
        TextView createLobby = (TextView) findViewById(R.id.makeLobby);
        TextView backToMain = (TextView) findViewById(R.id.mainMenu);
        hostTitle.setTypeface(font);
        createHead.setTypeface(font);
        lobbyTitle.setTypeface(font);
        lobbyPass.setTypeface(font);
        createLobby.setTypeface(font);
        backToMain.setTypeface(font);
        final EditText password = (EditText) findViewById(R.id.LobbyPassword);
        password.setVisibility(View.INVISIBLE);
        Switch privateGame = (Switch) findViewById(R.id.privateGame);
        privateGame.setTypeface(font);
        main = (Button) findViewById(R.id.mainMenu);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(HostAGame.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });
        privateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swtch){
                    password.setVisibility(View.VISIBLE);
                    swtch = false;
                }else {
                    password.setVisibility(View.INVISIBLE);
                    swtch = true;
                }

            }
        });

    }
}