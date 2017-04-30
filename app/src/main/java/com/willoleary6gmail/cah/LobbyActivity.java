package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
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

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LobbyActivity extends AppCompatActivity {

    private ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(0);
    boolean dontFire = false;
    private String timestamp;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(0);
        final SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        setText(font);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        String name = userInfo.getString("username", "");
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

            }
        });
        hostChck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        hostChck.setClickable(false);
                                                        String playerId = gameInfo.getString("player1_id", "");
                                                        if(!dontFire) {
                                                            Checked(playerId);
                                                        }
                                                        Toast.makeText(getApplicationContext(),
                                                                playerId,
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
        );
        player2Chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           player2Chck.setClickable(false);
                                                           String playerId = gameInfo.getString("player2_id", "");
                                                           if(!dontFire) {
                                                               Checked(playerId);
                                                           };
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
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String URL = "https://15155528serversite.000webhostapp.com/lobbyUpdate.php";
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
                        Toast.makeText(getApplicationContext(),
                                "ON",
                                Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean[] fromServer = new boolean[3];
                            fromServer[0] = jsonResponse.getBoolean("changes");
                            fromServer[1] = jsonResponse.getBoolean("NewUsers");
                            fromServer[2] = jsonResponse.getBoolean("checkBox");
                            if (fromServer[0]) {
                                if (fromServer[1]) {
                                    Toast.makeText(getApplicationContext(),
                                            "New User",
                                            Toast.LENGTH_LONG).show();
                                    int count = jsonResponse.getInt("newUsersCount");
                                    SharedPreferences.Editor edit = gameInfo.edit();
                                    String[] playerIDTemplates = {"player1_id", "player2_id", "player3_id", "player4_id"};
                                    String[] playerUsernameTemplates = {"player1Username", "player2Username", "player3Username", "player4Username",};
                                    String[] newUsernames = new String[count];
                                    String[] newPlayerStatusIds = new String[count];
                                    for (int i = 0; i < count; i++) {
                                        newUsernames[i] = jsonResponse.getString("newUsername" + (i + 1));
                                        newPlayerStatusIds[i] = jsonResponse.getString("newPlayerNumber" + (i + 1));
                                        boolean inputed = false;
                                        int j = 0;
                                        while (j < 4 && inputed == false) {
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
                                    dontFire = true;
                                    int count = jsonResponse.getInt("checkedCount");
                                    for (int i = 0; i < count; i++) {
                                        UpdateCheckBox(jsonResponse.getString("checker"+(i+1)));
                                    }
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

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(0);
    }

    public void updateUi() {
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        String player1Id = gameInfo.getString("player1_id", "");
        String player1Username = gameInfo.getString("player1Username", "");
        String name = userInfo.getString("username", "");

        Button start = (Button) findViewById(R.id.start);
        start.setClickable(false);

        TextView host = (TextView) findViewById(R.id.player1);
        CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
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
                start.setClickable(true);
                hostChck.setClickable(true);
            }
        }


        String player2Id = gameInfo.getString("player2_id", "");
        String player2Username = gameInfo.getString("player2Username", "");
        TextView player2 = (TextView) findViewById(R.id.player2);
        CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
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
        TextView player3 = (TextView) findViewById(R.id.player3);
        CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
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

    public void Checked(final String playerId) {
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        String gameId = String.valueOf(gameInfo.getInt("game_id",0));
        String URL = "https://15155528serversite.000webhostapp.com/checkBox.php";
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
                                "Error: Check box Sync failed.",
                                Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
      /*creates a hash map for volley*/
        Server_Login_Register_Request checked = new Server_Login_Register_Request(gameId, playerId, responseListener, URL);
        RequestQueue queue = Volley.newRequestQueue(LobbyActivity.this);
        //adds the login request to the que
        queue.add(checked);
    }
    public void UpdateCheckBox(String playerId){
        SharedPreferences gameInfo = getSharedPreferences("gameDetails", Context.MODE_PRIVATE);
        String my_id = String.valueOf(gameInfo.getInt("myPlayer_id", 0));
        final CheckBox hostChck = (CheckBox) findViewById(R.id.player1chck);
        final CheckBox player2Chck = (CheckBox) findViewById(R.id.player2chck);
        final CheckBox player3Chck = (CheckBox) findViewById(R.id.player3chck);
        final CheckBox player4Chck = (CheckBox) findViewById(R.id.player4chck);
        if(!(playerId.equals(my_id))) {
             if (playerId.equals(gameInfo.getString("player1_id", ""))) {
                 if (hostChck.isChecked()) {
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

    // Method for setting the font of the text
    private void setText(Typeface font){
        TextView gameLobbyHead = (TextView) findViewById(R.id.gameLobby);
        TextView playersHead = (TextView) findViewById(R.id.players);
        TextView readyHead = (TextView) findViewById(R.id.playerReady);
        TextView gameHost = (TextView) findViewById(R.id.gameHost);
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);
        TextView player3 = (TextView) findViewById(R.id.player3);
        TextView player4 = (TextView) findViewById(R.id.player4);
        TextView player1Check = (TextView) findViewById(R.id.player1chck);
        TextView player2Check = (TextView) findViewById(R.id.player2chck);
        TextView player3Check = (TextView) findViewById(R.id.player3chck);
        TextView player4Check = (TextView) findViewById(R.id.player4chck);
        TextView start = (TextView) findViewById(R.id.startGame);
        TextView leave = (TextView) findViewById(R.id.leaveGame);
        gameLobbyHead.setTypeface(font);
        playersHead.setTypeface(font);
        readyHead.setTypeface(font);
        gameHost.setTypeface(font);
        player1.setTypeface(font);
        player2.setTypeface(font);
        player3.setTypeface(font);
        player4.setTypeface(font);
        player1Check.setTypeface(font);
        player2Check.setTypeface(font);
        player3Check.setTypeface(font);
        player4Check.setTypeface(font);
        start.setTypeface(font);
        leave.setTypeface(font);
    }
  /*  public void beep() {
        final Runnable beeper = new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
                scheduleTaskExecutor.shutdown();
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduleTaskExecutor.scheduleAtFixedRate(
                beeper, 1, 1, SECONDS);
        scheduleTaskExecutor.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 1 * 1, SECONDS);
    }*/
}



