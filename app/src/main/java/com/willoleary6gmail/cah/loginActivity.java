package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
/*Class that logs in the user to the game*/
public class loginActivity extends AppCompatActivity {
    private EditText Username,password;
    private Button bLogin;
    private RequestQueue requestQueue;
   /*url to the php file that will handle the data and check the database if the users details are correct*/
    private static final String URL = "https://15155528serversite.000webhostapp.com/Login/user_control.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent toMainMenu = new Intent(loginActivity.this, MainMenu.class);
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String name = userInfo.getString("username", "");
        String pwd = userInfo.getString("password", "");
        /*checks if the user is already logged in*/
        if (name != "" || pwd != "") {
            loginActivity.this.startActivity(toMainMenu);
        } else {
            Runnable estabConnection = new Runnable() {
                @Override
                public void run() {
                    establishConnection();
                }
            };
            Thread testCon = new Thread(estabConnection);
            testCon.start();
            setContentView(R.layout.activity_login);
            /*Set variables to users inputs*/
            final EditText etUsername = (EditText) findViewById(R.id.Username);
            final EditText etPassword = (EditText) findViewById(R.id.Password);

            final Button bLogin = (Button) findViewById(R.id.button2);
            final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();
                    //added sound effect to help users
                    buttonSound();
                    //Listener to wait for php script to return answer
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                String updatedName = username;
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean[] fromServer = new boolean[2];
                                /*two boolean arrays first to check if we can connect
                                * with the database, and the second to ensure we have the
                                * right details*/
                                fromServer[0] = jsonResponse.getBoolean("success");
                                fromServer[1] = jsonResponse.getBoolean("data_fields");

                                if (fromServer[0] && fromServer[1]) {
                                    Intent intent = new Intent(loginActivity.this, loginActivity.class);
                                    loginActivity.this.startActivity(intent);
                                    saveInfo(updatedName, password);
                                    loginActivity.this.startActivity(toMainMenu);
                                } else if (!fromServer[0]) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error: You must enter a valid password and username",
                                            Toast.LENGTH_LONG).show();
                                } else if (!fromServer[1]) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error: You have not entered inputs into both fields",
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
            registerLink.setOnClickListener(new View.OnClickListener() {
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
    public void saveInfo(String username, String password) {
        /*If the php script returns a valid response then the details will be saved
        * in shared prefrences and will be used in future occasions */
        SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = userInfo.edit();
        //setting the values
        edit.putString("username",username);
        edit.putString("password",password);
        edit.apply();

    }
    private void establishConnection() {
        Response.Listener<String> TestConnection = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    // boolean fromServer =
                } catch (JSONException e) {

                }
            }
        };
                    /*creates a hash map for volley*/
        Server_Login_Register_Request testConnection = new Server_Login_Register_Request(TestConnection,URL);
        RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
        //adds the login request to the que
        queue.add(testConnection);

        }
}

