package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.SignInActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.UserSupportActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegistrarme();
        buttonUserSupport();
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
}
