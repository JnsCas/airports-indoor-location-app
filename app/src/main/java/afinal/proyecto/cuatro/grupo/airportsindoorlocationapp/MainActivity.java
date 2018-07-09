package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.LoginActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.SignInActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        int timeout = 10; // make the activity visible for 10 miliseconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginPage);
            }
        }, timeout);

    }
}