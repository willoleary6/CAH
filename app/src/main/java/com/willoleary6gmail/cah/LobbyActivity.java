package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class LobbyActivity extends AppCompatActivity {
    private static final String URL = "https://15155528serversite.000webhostapp.com/lobbyUpdate.php";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        final String gameId = String.valueOf(gameInfo.getInt("game_id", 0));
        int playerCount = gameInfo.getInt("game_id", 0);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        Toast.makeText(getApplicationContext(),
                "my id " +my_id,
                Toast.LENGTH_LONG).show();
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String name = userInfo.getString("username", "");


        String player1Id = gameInfo.getString("player1_id", "");
        String player1Username = gameInfo.getString("player1Username", "");
        final TextView host = (TextView) findViewById(R.id.player1);
        final CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        host.setText(player1Username);
        host.setVisibility(View.INVISIBLE);
        hostChck.setVisibility(View.INVISIBLE);
        hostChck.setClickable(false);
        if (!(player1Id.equals("0"))) {
            //host.setText(name);
            host.setVisibility(View.VISIBLE);
            hostChck.setVisibility(View.VISIBLE);
            if (player1Id.equals(my_id)) {
                host.setText(name);
                hostChck.setClickable(true);
            }
        }


        String player2Id = gameInfo.getString("player2_id", "");
        String player2Username = gameInfo.getString("player2Username", "");
        final TextView player2 = (TextView) findViewById(R.id.player2);
        final CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        player2.setText(player2Username);
        player2.setVisibility(View.INVISIBLE);
        player2Chck.setVisibility(View.INVISIBLE);
        player2Chck.setClickable(false);
        if (!(player2Id.equals("0"))) {
            //player2.setText(name);
            player2.setVisibility(View.VISIBLE);
            player2Chck.setVisibility(View.VISIBLE);
            if (player2Id.equals(my_id)) {
                player2Chck.setClickable(true);
            }
        }


        String player3Id = gameInfo.getString("player3_id", "");
        String player3Username = gameInfo.getString("player3Username", "");
        final TextView player3 = (TextView) findViewById(R.id.player3);
        final CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        player3.setText(player3Username);
        player3.setVisibility(View.INVISIBLE);
        player3Chck.setVisibility(View.INVISIBLE);
        player3Chck.setClickable(false);
        if (!(player3Id.equals("0"))) {
            //player3.setText(name);
            player3.setVisibility(View.VISIBLE);
            player3Chck.setVisibility(View.VISIBLE);
            if (player3Id.equals(my_id)) {
                player3Chck.setClickable(true);
            }
        }

        final TextView player4 = (TextView) findViewById(R.id.player4);
        final CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
        String player4Id = gameInfo.getString("player4_id", "");
        String player4Username = gameInfo.getString("player4Username", "");
        player4.setText(player4Username);
        player4.setVisibility(View.INVISIBLE);
        player4Chck.setVisibility(View.INVISIBLE);
        player4Chck.setClickable(false);
        if (!(player4Id.equals("0"))) {
            //player4.setText(name);
            player4.setVisibility(View.VISIBLE);
            player4Chck.setVisibility(View.VISIBLE);
            if (player4Id.equals(my_id)) {
                player4Chck.setClickable(true);
            }
        }

        Toast.makeText(getApplicationContext(),
                "id 4 "+player4Id,
                Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),
                "name 4"+player4Username,
                Toast.LENGTH_LONG).show();

        Handler updater = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                boolean repeat = true;
                while(repeat){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    /*creates a hash map for volley*/
                    Server_Login_Register_Request loginRequest = new Server_Login_Register_Request(gameId,responseListener, URL);
                    RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
                    //adds the login request to the que
                    queue.add(loginRequest);
                }
            };
        };
















                    /*else{
                        Toast.makeText(getApplicationContext(),
                                "Error: Something went wrong",
                                Toast.LENGTH_LONG).show();
                        try {
                            wait(1000);
                            Intent toLogIn = new Intent(LobbyActivity.this, MainMenu.class);
                            startActivity(toLogIn);
                        }catch (java.lang.InterruptedException e){
                            e.printStackTrace();
                        }
                    }*/


    }
}



