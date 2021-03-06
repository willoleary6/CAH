package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
/*Class that logs in the user to the game*/
public class loginActivity extends AppCompatActivity {
   /*url to the php file that will handle the data and check the database if the users details are correct*/
    private static final String URL = "https://15155528serversite.000webhostapp.com/Login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent toMainMenu = new Intent(loginActivity.this, MainMenu.class);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String name = userInfo.getString("username", "");
        String pwd = userInfo.getString("password", "");
        /*checks if the user is already logged in*/
        if (!(name.equals("") || pwd .equals(""))) {
            // if so the user is automatically logged it
            loginActivity.this.startActivity(toMainMenu);
        } else {
            setContentView(R.layout.activity_login);
            Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
            TextView gameTitle = (TextView) findViewById(R.id.cahHeader);
            TextView logHead = (TextView) findViewById(R.id.logHeader);
            final TextView user = (TextView) findViewById(R.id.Username);
            final TextView pass = (TextView) findViewById(R.id.Password);
            final TextView logButton = (TextView) findViewById(R.id.button2);
            final TextView regButton = (TextView) findViewById(R.id.tvRegisterHere);
            gameTitle.setTypeface(font);
            logHead.setTypeface(font);
            user.setTypeface(font);
            pass.setTypeface(font);
            logButton.setTypeface(font);
            regButton.setTypeface(font);
            /*Set variables to users inputs*/


            logButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = user.getText().toString();
                    final String password = pass.getText().toString();
                    //added sound effect to help users
                    buttonSound();
                    //Listener to wait for php script to return answer
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean[] fromServer = new boolean[2];
                                /*two boolean arrays first to check if we can connect
                                * with the database, and the second to ensure we have the
                                * right details*/
                                fromServer[0] = jsonResponse.getBoolean("success");
                                fromServer[1] = jsonResponse.getBoolean("data_fields");

                                if (fromServer[0] && fromServer[1]) {
                                    //if both fields are true then the user is sent to the main menu
                                    int idNumber = jsonResponse.getInt("user_id");
                                    Intent toMainMenu = new Intent(loginActivity.this, MainMenu.class);
                                    saveInfo(username, password, idNumber);
                                    loginActivity.this.startActivity(toMainMenu);
                                } else if (!fromServer[0]) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error: You must enter a valid password and username",
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    /*creates a hash map for volley*/
                    Server_Login_Register_Request loginRequest = new Server_Login_Register_Request(username, password, responseListener, URL);
                    RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                    //adds the login request to the que
                    queue.add(loginRequest);
                }
            });

            //link to register form
            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(loginActivity.this, RegisterActivity.class);
                    loginActivity.this.startActivity(registerIntent);
                }
            });
        }
    }
    private void buttonSound() {
        //every time a button is clicked this method is called
        MediaPlayer buttonClick = MediaPlayer.create(loginActivity.this, R.raw.click);
        buttonClick.start();
    }
    public void saveInfo(String username, String password, int idNumber) {
        /*If the php script returns a valid response then the details will be saved
        * in shared prefrences and will be used in future occasions */
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = userInfo.edit();
        //setting the values
        edit.putString("username",username);
        edit.putString("password",password);
        edit.putInt("user_id",idNumber);
        edit.apply();

    }

}

