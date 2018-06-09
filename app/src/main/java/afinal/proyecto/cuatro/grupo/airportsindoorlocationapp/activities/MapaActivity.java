package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        buttonUbicacion();
        buttonDestino();
        //buttonInfo();

    }

    private void buttonUbicacion() {
        Button btnUbicacion = (Button) findViewById(R.id.main_ubicacion_btn);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUbicacion = new Intent(getApplicationContext(), MapaActivity.class);
                startActivity(intentUbicacion);
            }
        });
    }

    private void buttonDestino() {
        Button btnDestino = (Button) findViewById(R.id.main_destino_btn);

        btnDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentDestino = new Intent(getApplicationContext(), DestinoActivity.class);
                startActivity(intentDestino);
            }
        });
    }

}
