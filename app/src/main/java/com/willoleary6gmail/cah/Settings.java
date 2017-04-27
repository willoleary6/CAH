package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    Button bt1,logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Typeface font = Typeface.createFromAsset(getAssets(), "helvetica-neue-lt-std-75-bold-5900e95806952.otf");
        TextView settingTitle = (TextView) findViewById(R.id.settingHeader);
        TextView signOut = (TextView) findViewById(R.id.logOutButton);
        TextView backToMain = (TextView) findViewById(R.id.button);
        settingTitle.setTypeface(font);
        signOut.setTypeface(font);
        backToMain.setTypeface(font);
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
