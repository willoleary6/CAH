package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import static java.util.concurrent.TimeUnit.SECONDS;

public class LobbyActivity extends AppCompatActivity {
    boolean dontFire = false;
    MediaPlayer buttonClick;
    private String timestamp;
    ScheduledExecutorService scheduleTaskExecutor;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_lobby);
        timestamp = gameInfo.getString("timestamp", "");
        final String gameId = String.valueOf(gameInfo.getInt("game_id", 0));
        updateUi();
        final CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        final CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        final CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        final CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
        Button start = (Button) findViewById(R.id.startGame);
        Button leave = (Button) findViewById(R.id.leaveGame);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean [] allReady = new boolean[4];
                SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                if(!(gameInfo.getString("player1_id", "").equals("0"))){
                    if(hostChck.isChecked()) {
                        allReady[0] = true;
                    }else{
                        allReady[0] = false;
                    }
                }else{
                    allReady[0] = true;
                }
                if(!(gameInfo.getString("player2_id", "").equals("0"))){
                }else{
                    allReady[1] = true;
                }
                if(!(gameInfo.getString("player3_id", "").equals("0"))){
                    if (player3Chck.isChecked()) {
                        allReady[2] = true;
                    }else{
                        allReady[2] = false;
                    }
                }else{
                    allReady[2] = true;
                }
                if(!(gameInfo.getString("player4_id", "").equals("0"))){
                    if(player4Chck.isChecked()) {
                        allReady[3] = true;
                    }else{
                        allReady[3] = false;
                    }
                }else{
                    allReady[3] = true;
                }
                if(allReady[0] && allReady[1] && allReady[2] && allReady[3]){
                    Intent StartGame = new Intent(LobbyActivity.this, inGame.class);
                    LobbyActivity.this.startActivity(StartGame);
                    /*String URL = "https://15155528serversite.000webhostapp.com/start.php";
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean fromServer;
                                fromServer = jsonResponse.getBoolean("success");
                                if(!fromServer) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error: Unable to start game.",
                                            Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                     //creates a hash map for volley
                    Server_Login_Register_Request checked = new Server_Login_Register_Request(gameId,my_id, responseListener, URL);
                    RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
                    //adds the login request to the que
                    queue.add(checked);*/
                }else{
                    Toast.makeText(getApplicationContext(),
                            "All players are not ready!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // if the user is the host this listener will fire
        hostChck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    //stopping user from clicking multiple times
                                                    hostChck.setClickable(false);
                                                    String playerId = gameInfo.getString("player1_id", "");
                                                    if(!dontFire) {
                                                        /*only execute if the user clicks the
                                                        checkbox and not the thread updating
                                                        other users inputs*/
                                                        Checked(playerId);
                                                    }
                                                }
                                            }
        );
        // similar for previous listener but for second user
        player2Chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       player2Chck.setClickable(false);
                                                       String playerId = gameInfo.getString("player2_id", "");
                                                       if(!dontFire) {
                                                           Checked(playerId);
                                                       }
                                                   }
                                               }
        );
        player3Chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       player3Chck.setClickable(false);
                                                       String playerId = gameInfo.getString("player3_id", "");
                                                       if(!dontFire) {
                                                           Checked(playerId);
                                                       }
                                                   }
                                               }
        );
        player4Chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       player4Chck.setClickable(false);
                                                       String playerId = gameInfo.getString("player4_id", "");
                                                       if(!dontFire) {
                                                           Checked(playerId);
                                                       }
                                                   }
                                               }
        );

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
                //onPause(my_id);
                buttonSound();
                Intent toFindAGame = new Intent(LobbyActivity.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });

        /*Thread that checks the database once a second looking for inputs from users in the lobby and syncing them each other*/
        scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String URL = "https://15155528serversite.000webhostapp.com/lobbyUpdate.php";
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean[] fromServer = new boolean[4];
                            fromServer[0] = jsonResponse.getBoolean("changes");
                            fromServer[1] = jsonResponse.getBoolean("NewUsers");
                            fromServer[2] = jsonResponse.getBoolean("checkBox");
                            fromServer[3] = jsonResponse.getBoolean("start");
                            if (fromServer[0]) {
                                //if changes are detected
                                if (fromServer[1]) {
                                    //if there is a new user
                                    int count = jsonResponse.getInt("newUsersCount");
                                    SharedPreferences.Editor edit = gameInfo.edit();
                                    String[] playerIDTemplates = {"player1_id", "player2_id", "player3_id", "player4_id"};
                                    String[] playerUsernameTemplates = {"player1Username", "player2Username", "player3Username", "player4Username",};
                                    String[] newUsernames = new String[count];
                                    String[] newPlayerStatusIds = new String[count];
                                    //populating shared preferences with the new users details
                                    for (int i = 0; i < count; i++) {
                                        newUsernames[i] = jsonResponse.getString("newUsername" + (i + 1));
                                        newPlayerStatusIds[i] = jsonResponse.getString("newPlayerNumber" + (i + 1));
                                        boolean inputed = false;
                                        int j = 0;
                                        while (j < 4 && !inputed) {
                                            if (gameInfo.getString(playerIDTemplates[j], "").equals("0")) {
                                                edit.putString(playerIDTemplates[j], newPlayerStatusIds[i]);
                                                edit.putString(playerUsernameTemplates[j], newUsernames[i]);
                                                edit.apply();
                                                Toast.makeText(getApplicationContext(),
                                                        "New Player: " + gameInfo.getString(playerUsernameTemplates[j], ""),
                                                        Toast.LENGTH_LONG).show();
                                                inputed = true;
                                                updateUi();
                                            }
                                            j++;
                                        }
                                    }
                                } else if (fromServer[2]) {
                                    //if someone clicked a checkbox in the lobby
                                    dontFire = true;
                                    int count = jsonResponse.getInt("checkedCount");
                                    for (int i = 0; i < count; i++) {
                                        UpdateCheckBox(jsonResponse.getString("checker"+(i+1)));
                                    }
                                }else if (fromServer[3]){
                                    //if the host pressed start
                                    Intent StartGame = new Intent(LobbyActivity.this, inGame.class);
                                    LobbyActivity.this.startActivity(StartGame);
                                }
                            }
                            dontFire = false;
                            timestamp = jsonResponse.getString("timestamp");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                /*creates a hash map for volley*/
                Server_Login_Register_Request updateRequest = new Server_Login_Register_Request(gameId, timestamp, responseListener, URL);
                RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
                //adds the login request to the que
                queue.add(updateRequest);
            }
        }, 0, 1, SECONDS);
    }



    public void updateUi() {
        //display Users details in the lobby
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        String player1Id = gameInfo.getString("player1_id", "");
        String player1Username = gameInfo.getString("player1Username", "");
        String name = userInfo.getString("username", "");

        Button start = (Button) findViewById(R.id.startGame);
        start.setClickable(false);
        // setting the information for the host
        TextView host = (TextView) findViewById(R.id.player1);
        CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        host.setText(player1Username);
        host.setVisibility(View.INVISIBLE);
        hostChck.setVisibility(View.INVISIBLE);
        hostChck.setClickable(false);
        if (!(player1Id.equals("0"))) {
            host.setVisibility(View.VISIBLE);
            hostChck.setVisibility(View.VISIBLE);
            if (player1Id.equals(my_id)) {
               //if you are the host then it will give you access to click his buttons.
                host.setText(name);
                start.setClickable(true);
                hostChck.setClickable(true);
            }
        }

        //setting information for player 2
        String player2Id = gameInfo.getString("player2_id", "");
        String player2Username = gameInfo.getString("player2Username", "");
        TextView player2 = (TextView) findViewById(R.id.player2);
        CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        player2.setText(player2Username);
        player2.setVisibility(View.INVISIBLE);
        player2Chck.setVisibility(View.INVISIBLE);
        player2Chck.setClickable(false);
        if (!(player2Id.equals("0"))) {
            player2.setVisibility(View.VISIBLE);
            player2Chck.setVisibility(View.VISIBLE);
            if (player2Id.equals(my_id)) {
                //if you are player 2 you can click player 2's box
                player2Chck.setClickable(true);
            }
        }

        //setting information for player 3
        String player3Id = gameInfo.getString("player3_id", "");
        String player3Username = gameInfo.getString("player3Username", "");
        TextView player3 = (TextView) findViewById(R.id.player3);
        CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        player3.setText(player3Username);
        player3.setVisibility(View.INVISIBLE);
        player3Chck.setVisibility(View.INVISIBLE);
        player3Chck.setClickable(false);
        if (!(player3Id.equals("0"))) {
            player3.setVisibility(View.VISIBLE);
            player3Chck.setVisibility(View.VISIBLE);
            if (player3Id.equals(my_id)) {
                player3Chck.setClickable(true);
            }
        }
        //setting information for player 3
        TextView player4 = (TextView) findViewById(R.id.player4);
        CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
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
    }

    //when the check box is clicked it calls this method
    public void Checked(final String playerId) {
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        String gameId = String.valueOf(gameInfo.getInt("game_id",0));
        String URL = "https://15155528serversite.000webhostapp.com/checkBox.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean fromServer;
                    fromServer = jsonResponse.getBoolean("success");
                    if(!fromServer) {
                        Toast.makeText(getApplicationContext(),
                                "Error: Check box Sync failed.",
                                Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
      /*creates a hash map for volley*/
      /*This method enters into the log that this user has pressed his check box and
      * every other lobby member will see the box become checked*/
      Server_Login_Register_Request checked = new Server_Login_Register_Request(gameId, playerId, responseListener, URL);
        RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
        //adds the login request to the que
        queue.add(checked);
    }

    public void UpdateCheckBox(String playerId){
        /*If statements dealing with what check box was ticked and what value it is*/
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        final CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        final CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        final CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        final CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
        if(!(playerId.equals(my_id))) {
            if (playerId.equals(gameInfo.getString("player1_id", ""))) {
                //if player 1 checked the box then this tree will execute
                if (hostChck.isChecked()) {
                    //if the box was previously checked then it will be on checked and vice versa
                    hostChck.setChecked(false);
                } else {
                    hostChck.setChecked(true);
                }
            } else if (playerId.equals(gameInfo.getString("player2_id", ""))) {
                if (player2Chck.isChecked()) {
                    player2Chck.setChecked(false);
                } else {
                    player2Chck.setChecked(true);
                }
            } else if (playerId.equals(gameInfo.getString("player3_id", ""))) {
                if (player3Chck.isChecked()) {
                    player3Chck.setChecked(false);
                } else {
                    player3Chck.setChecked(true);
                }
            } else if (playerId.equals(gameInfo.getString("player4_id", ""))) {
                if (player4Chck.isChecked()) {
                    player4Chck.setChecked(false);
                } else {
                    player4Chck.setChecked(true);
                }
            }
        }
        // check to make sure only the user on his/her handset can tick their corresponding box
        if(playerId.equals(gameInfo.getString("player1_id","")) && playerId.equals(my_id)){
            hostChck.setClickable(true);
        }else if(playerId.equals(gameInfo.getString("player2_id","")) && playerId.equals(my_id)){
            player2Chck.setClickable(true);
        }else if(playerId.equals(gameInfo.getString("player3_id","")) && playerId.equals(my_id)){
            player3Chck.setClickable(true);
        }else if(playerId.equals(gameInfo.getString("player4_id","")) && playerId.equals(my_id)){
            player4Chck.setClickable(true);
        }
    }

    protected void onPause(String playerId){
        super.onPause();
        /*SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        String player1Id = gameInfo.getString("player1_id", "");
        String player1Username = gameInfo.getString("player1Username", "");
        String name = userInfo.getString("username", "");
        if(playerId.equals(gameInfo.getString("player1_id","")) && playerId.equals(my_id)) {

        }
        else if (playerId.equals(gameInfo.getString("player2_id","")) && playerId.equals(my_id)) {

        }
        else if (playerId.equals(gameInfo.getString("player3_id","")) && playerId.equals(my_id)) {

        }
        else if (playerId.equals(gameInfo.getString("player4_id","")) && playerId.equals(my_id)) {

        }
        else {

        }*/
        scheduleTaskExecutor.shutdown();
    }

    private void buttonSound() {
        buttonClick = MediaPlayer.create(LobbyActivity.this, R.raw.click);
        buttonClick.start();
    }
}



