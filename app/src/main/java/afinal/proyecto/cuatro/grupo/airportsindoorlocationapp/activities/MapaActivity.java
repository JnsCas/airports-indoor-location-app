package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.os.Bundle;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;


public class MapaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(applicationContext, new EstimoteCloudCredentials("utn-proximity-beacons-bcr", "Yc62d2fc621d3f4809a2d9eb87f77d111"));
        cloudManager.getLocation("fiesta-405", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                // store the Location object for later,
                // you will need it to initialize the IndoorLocationManager!
                //
                // you can also pass it to IndoorLocationView to display a map:
                // indoorView = (IndoorLocationView) findViewById(R.id.indoor_view);
                // indoorView.setLocation(location);
                indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
                indoorLocationView.setLocation(location);
            }

            @Override
            public void failure(EstimoteCloudException e) {
                // oops!
            }
        });

        IndoorLocationView indoorView =
                (IndoorLocationView) findViewById(R.id.indoor_view);
        indoorView.setLocation(location);
    }
}
