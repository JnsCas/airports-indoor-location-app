package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.VueloActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.MapaActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.InfoActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonVuelo();
        buttonMapa();
        buttonInfo();

    }

    private void buttonMapa() {
        Button btnMapa = (Button) findViewById(R.id.home_mapa_btn);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(getApplicationContext(), MapaActivity.class);
                startActivity(intentMapa);
            }
        });
    }

    private void buttonVuelo() {
        Button btnVuelo = (Button) findViewById(R.id.home_vuelo_btn);

        btnVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVuelo = new Intent(getApplicationContext(), VueloActivity.class);
                startActivity(intentVuelo);
            }
        });
    }

    private void buttonInfo() {
        TextView btnInfo = (TextView) findViewById(R.id.home_info_btn);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intentInfo);
            }
        });
    }

}
