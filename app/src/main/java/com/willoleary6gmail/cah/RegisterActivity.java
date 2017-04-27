package com.willoleary6gmail.cah;

import android.content.Intent;
import android.graphics.Typeface;
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
/*Class that registers the user for the game*/
public class RegisterActivity extends AppCompatActivity {
    private static final String URL = "https://15155528serversite.000webhostapp.com/Registration/user_control.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView gameTitle = (TextView) findViewById(R.id.cahHeader);
        TextView regHead = (TextView) findViewById(R.id.regHeader);
        TextView regButton = (TextView) findViewById(R.id.button2);
        TextView accExists = (TextView) findViewById(R.id.accountExists);
        TextView logButton = (TextView) findViewById(R.id.tvLoginHere);
        gameTitle.setTypeface(font);
        regHead.setTypeface(font);
        regButton.setTypeface(font);
        accExists.setTypeface(font);
        logButton.setTypeface(font);
         final EditText etUsername = (EditText) findViewById(R.id.Username);
         final EditText etPassword = (EditText) findViewById(R.id.Password);

         Button bRegister = (Button) findViewById(R.id.button2);
        TextView LoginLink = (TextView) findViewById(R.id.tvLoginHere);

       bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                buttonSound();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            String updatedName = username;
                            String updatedPwd = password;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean [] fromServer = new boolean[3];
                            fromServer[0] = jsonResponse.getBoolean("success");
                            fromServer[1] = jsonResponse.getBoolean("valid_name");
                            fromServer[2] = jsonResponse.getBoolean("data_fields");

                            if(fromServer[0] && fromServer[1] && fromServer[2])
                            {
                                Intent intent = new Intent(RegisterActivity.this,loginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful." ,
                                        Toast.LENGTH_LONG).show();
                                        Intent LoginIntent = new Intent(RegisterActivity.this,loginActivity.class);
                                        loginActivity log = new loginActivity();
                                        log.saveInfo(updatedName,updatedPwd);
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
                Server_Login_Register_Request registerRequest = new Server_Login_Register_Request(username,password,responseListener,URL);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
        LoginLink.setOnClickListener(new View.OnClickListener() {
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
}
