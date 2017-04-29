package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.button;

public class FindAGame extends AppCompatActivity {
    boolean switchClick = true;
    Button main, searchLobby;
    //private static final String URL = "https://15155528serversite.000webhostapp.com/findAGame.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_agame);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        final String user = userInfo.getString("username", "");
        final String userPass = userInfo.getString("password", "");
        final TextView gName = (TextView) findViewById(R.id.gameName);
        final TextView gPassword = (TextView) findViewById(R.id.gamePassword);
        gPassword.setVisibility(View.INVISIBLE);
        Switch publicPrivate = (Switch) findViewById(R.id.privateGame);

        main = (Button) findViewById(R.id.backToMain);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(FindAGame.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });

        searchLobby = (Button) findViewById(R.id.search);
        searchLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView lobbyName = (TextView) findViewById(R.id.gameName);
                final TextView lobbyPassword = (TextView) findViewById(R.id.gamePassword);
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int GameId;
                            int handId;
                            int playerId;
                            boolean[] fromServer = new boolean[3];
                            fromServer[0] = jsonResponse.getBoolean("success");
                            fromServer[1] = jsonResponse.getBoolean("valid_name");
                            fromServer[2] = jsonResponse.getBoolean("Lobby_name");

                            if (!fromServer[2]) {
                                Toast.makeText(getApplicationContext(),
                                        "Error: No lobby with the given name",
                                        Toast.LENGTH_LONG).show();
                            } else if (!fromServer[1]) {
                                Toast.makeText(getApplicationContext(),
                                        "Error: Credentials invalid",
                                        Toast.LENGTH_LONG).show();
                                SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = userInfo.edit();
                                editor.clear();
                                editor.commit();
                                finish();
                                try {
                                    wait(1000);
                                    Intent toJoin = new Intent(FindAGame.this, loginActivity.class);
                                    startActivity(toJoin);
                                } catch (java.lang.InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else if (!fromServer[0]) {
                                Toast.makeText(getApplicationContext(),
                                        "Error: Server error, please try again soon",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Game Joined.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        });

        publicPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchClick){
                    gPassword.setVisibility(View.VISIBLE);
                    switchClick = false;
                }else {
                    gPassword.setVisibility(View.INVISIBLE);
                    switchClick = true;
                }

            }
        });
    }
}
