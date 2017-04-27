package com.willoleary6gmail.cah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class HostAGame extends AppCompatActivity {
    boolean swtch = true;
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_agame);
        final EditText password = (EditText) findViewById(R.id.LobbyPassword);
        password.setVisibility(View.INVISIBLE);
        Switch privteGame = (Switch) findViewById(R.id.privte);
        bt1 = (Button) findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(HostAGame.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });
        privteGame.setOnClickListener(new View.OnClickListener() {
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