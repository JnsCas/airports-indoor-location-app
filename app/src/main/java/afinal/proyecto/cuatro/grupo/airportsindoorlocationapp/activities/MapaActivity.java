package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.estimote.indoorsdk.EstimoteCloudCredentials;
import com.estimote.indoorsdk_module.algorithm.IndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.view.IndoorLocationView;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        buttonUbicacion();
        buttonDestino();
        //buttonInfo();
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(getApplicationContext(), new EstimoteCloudCredentials("utn-proximity-beacons-bcr", "c62d2fc621d3f4809a2d9eb87f77d111"));
        cloudManager.getLocation("fiesta-405", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                // store the Location object for later,
                // you will need it to initialize the IndoorLocationManager!
                //
                // you can also pass it to IndoorLocationView to display a map:
                // indoorView = (IndoorLocationView) findViewById(R.id.indoor_view);
                // indoorView.setLocation(location);
                IndoorLocationView indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
                indoorLocationView.setLocation(location);
            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops
            }
        });
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
