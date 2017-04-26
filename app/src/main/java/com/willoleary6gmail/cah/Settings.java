package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    Button bt1,logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logOut = (Button)findViewById(R.id.logOutButton);
        bt1 = (Button) findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFindAGame = new Intent(Settings.this,MainMenu.class);
                startActivity(toFindAGame);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userInfo = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent toLogIn = new Intent(Settings.this,loginActivity.class);
                startActivity(toLogIn);
            }
        });
    }
}
