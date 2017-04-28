package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LobbyActivity extends AppCompatActivity {
    private static final String URL = "https://15155528serversite.000webhostapp.com/gameInfo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        String gameId = String.valueOf(gameInfo.getInt("game_id",0));
        final String player1Id = String.valueOf(gameInfo.getInt("player1_id",0));
        final String player2Id = String.valueOf(gameInfo.getInt("player2_id",0));
        final String player3Id = String.valueOf(gameInfo.getInt("player3_id",0));
        final String player4Id = String.valueOf(gameInfo.getInt("player4_id",0));
        final String my_id = String.valueOf(gameInfo.getInt("myPlayer_id",0));

        final TextView host = (TextView) findViewById(R.id.player1);
        final CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        host.setVisibility(View.INVISIBLE);
        hostChck.setVisibility(View.INVISIBLE);
        hostChck.setClickable(false);


        final TextView player2 = (TextView) findViewById(R.id.player2);
        final CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        player2.setVisibility(View.INVISIBLE);
        player2Chck.setVisibility(View.INVISIBLE);
        player2Chck.setClickable(false);


        final TextView player3 = (TextView) findViewById(R.id.player3);
        final CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        player3.setVisibility(View.INVISIBLE);
        player3Chck.setVisibility(View.INVISIBLE);
        player3Chck.setClickable(false);

        final TextView player4 = (TextView) findViewById(R.id.player4);
        final CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
        player4.setVisibility(View.INVISIBLE);
        player4Chck.setVisibility(View.INVISIBLE);
        player4Chck.setClickable(false);

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                    String name = userInfo.getString("username", "");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean fromServer;
                                /*two boolean arrays first to check if we can connect
                                * with the database, and the second to ensure we have the
                                * right details*/
                    fromServer = jsonResponse.getBoolean("success");
                    String [] listNames = {"player1_status_id","player2_status_id","player3_status_id","player4_status_id"};
                    String [] playerList = new String[4];
                    if(fromServer) {
                      for(int i = 0; i < listNames.length; i++) {
                          playerList[i] = jsonResponse.getString(listNames[i]);
                      }
                          if (playerList[0].equals(player1Id) && (!player1Id.equals("0"))) {
                              host.setText(name);
                              host.setVisibility(View.VISIBLE);
                              hostChck.setVisibility(View.VISIBLE);
                              if(player1Id.equals(my_id)){
                                  hostChck.setClickable(true);
                              }
                          }
                          if (playerList[1].equals(player2Id)&& (!player2Id.equals("0"))) {
                              player2.setText(name);
                              player2.setVisibility(View.VISIBLE);
                              player2Chck.setVisibility(View.VISIBLE);
                              if(player2Id.equals(my_id)) {
                                  player2Chck.setClickable(true);
                              }
                          }if (playerList[2].equals(player3Id)&& (!player3Id.equals("0"))) {
                              player3.setText(name);
                              player3.setVisibility(View.VISIBLE);
                              player3Chck.setVisibility(View.VISIBLE);
                              if(player3Id.equals(my_id)) {
                                  player3Chck.setClickable(true);
                              }
                          }if (playerList[3].equals(player4Id)&& (!player4Id.equals("0"))) {
                              player4.setText(name);
                              player4.setVisibility(View.VISIBLE);
                              player4Chck.setVisibility(View.VISIBLE);
                              if(player4Id.equals(my_id)) {
                                  player4Chck.setClickable(true);
                              }
                          } else {
                              Toast.makeText(getApplicationContext(),
                                      "Error: Didnt work",
                                      Toast.LENGTH_LONG).show();
                          }


                    }else{
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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Server_Login_Register_Request loginRequest = new Server_Login_Register_Request(gameId,responseListener, URL);
        RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
        //adds the login request to the que
        queue.add(loginRequest);

    }
}
