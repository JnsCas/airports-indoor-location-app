package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class CargarVueloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargarvuelo);

        buttonGuardar();

    }

    private void buttonGuardar() {
        Button btnGuardar = (Button) findViewById(R.id.cargarvuelo_guardar_btn);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVuelo = new Intent(getApplicationContext(), UserSupportActivity.class);
                startActivity(intentVuelo);
            }
        });
    }

}
