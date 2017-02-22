package com.willoleary6gmail.cah;

import android.content.Intent;
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

public class loginActivity extends AppCompatActivity {
    private EditText Username,password;
    private Button bLogin;
    private RequestQueue requestQueue;
    private static final String URL = "http://193.1.100.76:80/cah/registration/user_control.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.Username);
        final EditText etPassword = (EditText) findViewById(R.id.Password);

        final Button bLogin = (Button) findViewById(R.id.button2);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                buttonSound();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            String updatedName = username;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean [] fromServer = new boolean[2];
                            fromServer[0] = jsonResponse.getBoolean("success");
                            fromServer[1] = jsonResponse.getBoolean("data_fields");

                            if(fromServer[0] && fromServer[1])
                            {
                                Intent intent = new Intent(loginActivity.this,loginActivity.class);
                                loginActivity.this.startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Login successful" ,
                                        Toast.LENGTH_LONG).show();
                                Intent registerIntent = new Intent(loginActivity.this,MainMenu.class);
                                loginActivity.this.startActivity(registerIntent);
                            }else if(!fromServer[0]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: Unknown cause" ,
                                        Toast.LENGTH_LONG).show();
                            }else if(!fromServer[1]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: You must enter a password and username",
                                        Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Server_Login_Register_Request registerRequest = new Server_Login_Register_Request(username,password,responseListener,"https://15155528serversite.000webhostapp.com/Login/user_control.php");
                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                queue.add(registerRequest);
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(loginActivity.this,RegisterActivity.class);
                loginActivity.this.startActivity(registerIntent);
            }
        });
    }
    private void buttonSound() {
        MediaPlayer buttonClick = MediaPlayer.create(loginActivity.this, R.raw.click);
        buttonClick.start();
    }







    }

