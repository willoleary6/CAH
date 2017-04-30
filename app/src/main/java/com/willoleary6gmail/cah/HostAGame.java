package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HostAGame extends AppCompatActivity {
    boolean swtch = true;
    Button main;
    private static final String URL = "https://15155528serversite.000webhostapp.com/NewGame.php";
    Button makeLobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_agame);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        final String username = userInfo.getString("username", "");
        final String password = userInfo.getString("password", "");
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        final TextView Name = (TextView) findViewById(R.id.LobbyName);
        final TextView PassCode = (TextView) findViewById(R.id.LobbyPassword);
        PassCode.setVisibility(View.INVISIBLE);
        Switch privateGame = (Switch) findViewById(R.id.privateGame);
        privateGame.setTypeface(font);
        setText(font);


        main = (Button) findViewById(R.id.mainMenu);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(HostAGame.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });

        makeLobby = (Button) findViewById(R.id.makeLobby);
        makeLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lobbyName = Name.getText().toString();
                final String lobbyPassCode = PassCode.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int GameId;
                            int handId;
                            int playerId;
                            String timestamp;
                            boolean[] fromServer = new boolean[3];
                                /*two boolean arrays first to check if we can connect
                                * with the database, and the second to ensure we have the
                                * right details*/
                            fromServer[0] = jsonResponse.getBoolean("success");
                            fromServer[1] = jsonResponse.getBoolean("valid_name");
                            fromServer[2] = jsonResponse.getBoolean("Lobby_name");
                            if(!fromServer[2]) {
                                Toast.makeText(getApplicationContext(),
                                        "Error: Error that name is already in use",
                                        Toast.LENGTH_LONG).show();
                            }else if(!fromServer[1]) {
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
                                    Intent toLogIn = new Intent(HostAGame.this, loginActivity.class);
                                    startActivity(toLogIn);
                                }catch (java.lang.InterruptedException e){
                                    e.printStackTrace();
                                }
                            }else if(!fromServer[0]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: Server error, please try again soon",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                 GameId = jsonResponse.getInt("gameId");
                                 handId = jsonResponse.getInt("hand_id");
                                 playerId = jsonResponse.getInt("playerId");
                                 timestamp = jsonResponse.getString("createdTimeStamp");
                                 SharedPreferences gameDetails = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                                 SharedPreferences.Editor edit = gameDetails.edit();
                                 edit.putString("timestamp",timestamp);
                                 edit.putInt("game_id",GameId);
                                 edit.putInt("hand_id",handId);
                                 edit.putString("player1_id",String.valueOf(playerId));
                                 edit.putInt("myPlayer_id",playerId);
                                 edit.putString("player2_id","0");
                                 edit.putString("player2Username","EMPTY");
                                 edit.putString("player3_id","0");
                                 edit.putString("player3Username","EMPTY");
                                 edit.putString("player4_id","0");
                                 edit.putString("player4Username","EMPTY");
                                 edit.putInt("playerCount",1);
                                 edit.apply();
                                 Toast.makeText(getApplicationContext(),
                                        "Game Created. ",
                                        Toast.LENGTH_LONG).show();
                                Intent LobbyIntent = new Intent(HostAGame.this,LobbyActivity.class);
                                HostAGame.this.startActivity(LobbyIntent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Server_Login_Register_Request createRequest = new Server_Login_Register_Request(username, password, lobbyName, lobbyPassCode, swtch, responseListener, URL);
                RequestQueue queue = Volley.newRequestQueue(HostAGame.this);
                queue.add(createRequest);
            }
        });

        privateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swtch){
                    PassCode.setVisibility(View.VISIBLE);
                    swtch = false;
                }else {
                    PassCode.setVisibility(View.INVISIBLE);
                    swtch = true;
                }

            }
        });

    }


    private void setText(Typeface font){
        TextView hostHead = (TextView) findViewById(R.id.hostHeader);
        TextView createHead = (TextView) findViewById(R.id.createHeader);
        TextView lobbyTitle = (TextView) findViewById(R.id.LobbyName);
        TextView lobbyPass = (TextView) findViewById(R.id.LobbyPassword);
        TextView createLobby = (TextView) findViewById(R.id.makeLobby);
        TextView backToMain = (TextView) findViewById(R.id.mainMenu);
        hostHead.setTypeface(font);
        createHead.setTypeface(font);
        lobbyTitle.setTypeface(font);
        lobbyPass.setTypeface(font);
        createLobby.setTypeface(font);
        backToMain.setTypeface(font);
    }
}