package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonVuelo();
        buttonMapa();
        buttonSupport();
        buttonNotification();
    }

    private void buttonMapa() {
        ImageButton btnMapa = findViewById(R.id.mapa_imgbtn);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(getApplicationContext(), MapaActivity.class);
                startActivity(intentMapa);
            }
        });
    }

    private void buttonVuelo() {
        ImageButton btnVuelo = findViewById(R.id.vuelo_imgbtn);

        btnVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVuelo = new Intent(getApplicationContext(), VueloActivity.class);
                startActivity(intentVuelo);
            }
        });
    }

    private void buttonSupport() {
        ImageButton btnInfo = findViewById(R.id.support_imgbtn);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intentInfo);
            }
        });
    }

    private void buttonNotification() {
        ImageButton btnInfo = findViewById(R.id.notification_imgbtn);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intentInfo);
            }
        });
    }
}
