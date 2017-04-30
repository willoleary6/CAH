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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FindAGame extends AppCompatActivity {
    boolean switchClick = true;
    Button main, searchLobby;
    private static final String URL = "https://15155528serversite.000webhostapp.com/findAGame.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_agame);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        setText(font);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        final String user_id = String.valueOf(userInfo.getInt("user_id",0));
        final TextView gName = (TextView) findViewById(R.id.gameName);
        final TextView gPassword = (TextView) findViewById(R.id.gamePassword);
        gPassword.setVisibility(View.INVISIBLE);
        Switch publicPrivate = (Switch) findViewById(R.id.switch1);

        // Returns you back to the main menu
        main = (Button) findViewById(R.id.backToMain);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(FindAGame.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });

        // Searches for the lobby within the database based on the user criteria supplied
        searchLobby = (Button) findViewById(R.id.search);
        searchLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gameName = gName.getText().toString();
                final String gamePassword = gPassword.getText().toString();
                searchLobby.setClickable(false);
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            int playerCount;
                            int myId;
                            int handId;
                            String playerId;
                            String playerUsername;
                            String timestamp;
                            String [] playerNumbers = {"player1_id","player2_id","player3_id","player4_id"};
                            String [] playerUsernames = {"player1Username","player2Username","player3Username","player4Username"};
                            String [] [] playerDetails = new String [4][2];
                            boolean[] fromServer = new boolean[6];
                            fromServer[0] = jsonResponse.getBoolean("finalInsert");
                            fromServer[1] = jsonResponse.getBoolean("playerstatus");
                            fromServer[2] = jsonResponse.getBoolean("hand");
                            fromServer[3] = jsonResponse.getBoolean("emptySpace");
                            fromServer[4] = jsonResponse.getBoolean("find");
                            fromServer[5] = jsonResponse.getBoolean("success");
                            if(fromServer[0] && fromServer[1] && fromServer[2]
                                    && fromServer[3] && fromServer[4] && fromServer[5]){

                                SharedPreferences gameDetails = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = gameDetails.edit();
                                playerCount = jsonResponse.getInt("playerCount");
                                timestamp = jsonResponse.getString("CreatedTimeStamp");
                                myId = jsonResponse.getInt("myId");
                                handId = jsonResponse.getInt("hand_id");
                                edit.putString("timestamp",timestamp);
                                edit.putInt("playerCount",playerCount);
                                edit.putInt("hand_id",handId);
                                edit.putInt("myPlayer_id",myId);

                                //test = jsonResponse.getString(playerTemplates[0]);
                                for(int i = 0; i < 4; i++) {
                                    if(i > playerCount+1){
                                        edit.putString(playerNumbers[i-1],"0");
                                        edit.putString(playerUsernames[i-1],"EMPTY");
                                    }
                                    playerId = jsonResponse.getString(playerNumbers[i]);
                                    edit.putString(playerNumbers[i],playerId);
                                    playerUsername = jsonResponse.getString(playerUsernames[i]);
                                    edit.putString(playerUsernames[i],playerUsername);
                                    /*Toast.makeText(getApplicationContext(),
                                            playerNumbers[i]+" "+playerId+"    "+ playerUsernames[i]+"   "+ playerUsername,
                                            Toast.LENGTH_LONG).show();*/
                                }
                                Toast.makeText(getApplicationContext(),
                                        "Working ",
                                        Toast.LENGTH_LONG).show();
                                    edit.apply();
                                    Intent toLogIn = new Intent(FindAGame.this, LobbyActivity.class);
                                    startActivity(toLogIn);


                            }else{
                                if(!fromServer[0]){
                                    // If the user is unable to join the game
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Join game failed",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }else if(!fromServer[1]){
                                    // If the player is not created within the game
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Unable to create player entity",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }else if(!fromServer[2]){
                                    // If the player is joined but their hand of cards is not created
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Unable to generate hand",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }else if(!fromServer[3]){
                                    // If the game is full
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Game is full",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }else if(!fromServer[4]){
                                    // If the game does not exist
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Unable to find game",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }else{
                                    // If something unexpected results in the find game being unsuccessful
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Something went wrong",
                                            Toast.LENGTH_LONG).show();
                                    searchLobby.setClickable(true);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // A request is sent to join the game that the user wishes to join
                Server_Login_Register_Request joinGame = new Server_Login_Register_Request(gameName,gamePassword,user_id,responseListener, URL);
                RequestQueue queue = Volley.newRequestQueue(FindAGame.this);
                queue.add(joinGame);
            }
        });

        // Displays the password field if the user chooses to create a private game i.e. presses the switch
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

    // Method for setting the font of the text
    private void setText(Typeface font) {
        TextView findTitle = (TextView) findViewById(R.id.findHeader);
        TextView publicPrivateSwitch = (TextView) findViewById(R.id.switch1);
        TextView privateText = (TextView) findViewById(R.id.privateHeader);
        TextView gameName = (TextView) findViewById(R.id.gameName);
        TextView gamePass = (TextView) findViewById(R.id.gamePassword);
        TextView searchButton = (TextView) findViewById(R.id.search);
        TextView mainMenuButton = (TextView) findViewById(R.id.backToMain);
        findTitle.setTypeface(font);
        publicPrivateSwitch.setTypeface(font);
        privateText.setTypeface(font);
        gameName.setTypeface(font);
        gamePass.setTypeface(font);
        searchButton.setTypeface(font);
        mainMenuButton.setTypeface(font);
    }
}