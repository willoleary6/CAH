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
/*Class that registers the user for the game*/
public class RegisterActivity extends AppCompatActivity {
    private static final String URL = "https://15155528serversite.000webhostapp.com/Register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*declaring all the text views*/
        Typeface font = Typeface.createFromAsset
                (getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView gameTitle = (TextView) findViewById(R.id.cahHeader);
        TextView regHead = (TextView) findViewById(R.id.regHeader);
        final TextView user = (TextView) findViewById(R.id.Username);
        final TextView pass = (TextView) findViewById(R.id.Password);
        TextView regButton = (TextView) findViewById(R.id.button2);
        TextView accExists = (TextView) findViewById(R.id.accountExists);
        TextView logButton = (TextView) findViewById(R.id.tvLoginHere);
       /*setting the fonts*/
        gameTitle.setTypeface(font);
        regHead.setTypeface(font);
        user.setTypeface(font);
        pass.setTypeface(font);
        regButton.setTypeface(font);
        accExists.setTypeface(font);
        logButton.setTypeface(font);

        //when the register button is clicked this listener starts
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = user.getText().toString();
                final String password = pass.getText().toString();
                buttonSound();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean [] fromServer = new boolean[3];
                            fromServer[0] = jsonResponse.getBoolean("success");
                            fromServer[1] = jsonResponse.getBoolean("valid_name");
                            fromServer[2] = jsonResponse.getBoolean("data_fields");

                            if(fromServer[0] && fromServer[1] && fromServer[2])
                            {
                                //if all booleans check out commit user details to shared preferences
                                int idNumber = jsonResponse.getInt("user_id");
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful." ,
                                        Toast.LENGTH_LONG).show();
                                Intent LoginIntent = new Intent
                                        (RegisterActivity.this,loginActivity.class);
                                saveInfo(username,password,idNumber);
                                RegisterActivity.this.startActivity(LoginIntent);
                            }else if(!fromServer[2]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: You must enter a password and username.",
                                        Toast.LENGTH_LONG).show();
                            }else if(!fromServer[0]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: Unknown cause." ,
                                        Toast.LENGTH_LONG).show();
                            }else if(!fromServer[1]){
                                Toast.makeText(getApplicationContext(),
                                        "Error: Username unavailable.",
                                        Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                /*Creates a hash map for volley and adds the */
                Server_Login_Register_Request registerRequest =
                        new Server_Login_Register_Request(username,password,responseListener,URL);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this,loginActivity.class);
                RegisterActivity.this.startActivity(LoginIntent);
            }
        });
    }
    private void buttonSound() {
        MediaPlayer buttonClick = MediaPlayer.create(RegisterActivity.this, R.raw.click);
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
