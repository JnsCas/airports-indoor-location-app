package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.HomeActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.SignInActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.UserSupportActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.HomeActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.VueloActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.MapaActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.InfoActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegistrarme();
        buttonUserSupport();
        buttonLogin();
<<<<<<< HEAD
=======

>>>>>>> 8d2c4c9b4e91cf1df8f5c4789739c68dde0e4b7c
    }

    private void buttonUserSupport() {
        Button btnUserSupport = (Button) findViewById(R.id.main_userSupport_btn);

        btnUserSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUserSupport = new Intent(getApplicationContext(), UserSupportActivity.class);
                startActivity(intentUserSupport);
            }
        });
    }

    private void buttonRegistrarme() {
        Button btnRegistrarme = (Button) findViewById(R.id.main_registrarme_btn);

        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intentSignIn);
            }
        });
    }

<<<<<<< HEAD
=======

>>>>>>> 8d2c4c9b4e91cf1df8f5c4789739c68dde0e4b7c
    private void buttonLogin() {
        Button btnLogin = (Button) findViewById(R.id.main_login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                Intent intentLogin = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentLogin);
=======
                Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentHome);
>>>>>>> 8d2c4c9b4e91cf1df8f5c4789739c68dde0e4b7c
            }
        });
    }
}
