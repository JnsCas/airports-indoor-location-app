package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class DestinoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destino);

        buttonConfirmar();

    }

    private void buttonConfirmar() {
        Button btnConfirmar = (Button) findViewById(R.id.destino_confirmar_btn);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentConfirmar = new Intent(getApplicationContext(), MapaActivity.class);
                startActivity(intentConfirmar);
            }
        });
    }

}
