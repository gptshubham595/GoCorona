package com.iitg.gocorona.hygiea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.iitg.gocorona.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_hygiea);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sharedpreferences = getSharedPreferences("signin", Context.MODE_PRIVATE);
                String result = sharedpreferences.getString("signedin", "none");
                if (result.equals(""))
                    startActivity(new Intent(getApplicationContext(), GoogleSIgnInActivity.class));
                else if (result.equals("logedin"))
                    startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                else if (result.equals("none"))
                    startActivity(new Intent(getApplicationContext(), GoogleSIgnInActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), GoogleSIgnInActivity.class));

                Splash.this.finish();

            }
        }, 2000);
    }
}
