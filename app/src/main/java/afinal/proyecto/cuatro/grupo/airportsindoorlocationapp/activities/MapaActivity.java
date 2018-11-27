package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.ImageAdapter;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonArrayResponse;

public class MapaActivity extends AppCompatActivity {

    private NotificationsManager newMapManager;
    private ImageAdapter imageAdapter;
    Spinner fromSpinner;
    Spinner toSpinner;
    String nodeFrom;
    String nodeTo;

    /* Last position class */
    class LastPosition
    {
        String Zone;
        String Time;

        LastPosition(String zone, String momentoPosicion) {
            this.Zone = zone;
            this.Time = momentoPosicion;
        }
    }

    LastPosition lastPosition;

    /* Statics location / destinations  */
    private static final String[] LOCATIONS = {
            "Seleccione un destino...", // case 0 -> null
            "Hall de entrada",          // case 1 -> le2
            "Sala de espera",           // case 2 -> ca2
            "Hall central",             // case 3 -> co1
            "Corredor norte",           // case 4 -> co2
            "Puerta de embarque 2",     // case 5 -> br1
            "Puerta de embarque 1"      // case 6 -> ca1

            // "Zona de transito A",    // case 2 -> br2
            // "Zona de transito B",    // case 3 -> le1
    };

    /* In order with the destination selected get the node and his position in the graph */
    private String getNode(int position) {

        String nodePosition;

        switch (position) {
            case 0:
                nodePosition = null;
                break;
            case 1: // node le2
                nodePosition = "1/1";
                break;
            case 2: // node ca2
                nodePosition = "3/3";
                break;
            case 3: // node co1
                nodePosition = "3/5";
                break;
            case 4: // node co2
                nodePosition = "4/5";
                break;
            case 5: // node br1
                nodePosition = "4/3";
                break;
            case 6: // node ca1
                nodePosition = "4/4";
                break;
            default:
                nodePosition = null;
                break;
            /*
            case 2: // node br2
                nodePosition = "2/1";
                break;
            case 3: // node le1
                nodePosition = "3/2";
                break;
            */
        }
        return nodePosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        /* Set image map as a grid view */
        GridView gridview = findViewById(R.id.newmapgridview);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        /* Send Beacon listener information */
        newMapManager = NotificationsManager.getInstance();
        newMapManager.NewMapManager(this, imageAdapter);

        /* Spinner From */
        fromSpinner = findViewById(R.id.from);
        fromSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LOCATIONS));
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nodeFrom = getNode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /* New implementation of last position by backend information */

        int idUser = getIntent().getIntExtra("idUser", 0);

        new GetLastPosition((long) idUser).execute();

        nodeFrom = lastPosition.Zone;


        /* Spinner To */
        toSpinner = findViewById(R.id.to);
        toSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LOCATIONS));
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nodeTo = getNode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        buttonBuscarCamino();
    }

    private void buttonBuscarCamino() {

        Button btnBuscarCamino = findViewById(R.id.btn_buscar_camino);

        btnBuscarCamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetWayFinding(nodeFrom, nodeTo).execute();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private class GetWayFinding extends AsyncTask<Void, Void, Void> {

        private String nodeOrigin;
        private String nodeDestination;
        private JSONArray jsonArray;
        private Integer status;

        GetWayFinding(String nodeOrigin, String nodeDestination) {
            this.nodeOrigin = nodeOrigin;
            this.nodeDestination = nodeDestination;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String resource = "/way-finding/" + nodeOrigin + "/" + nodeDestination;
            JsonArrayResponse jsonArrayResponse = ConexionWebService.getJsonArray(resource);

            jsonArray = jsonArrayResponse.getJsonArray();
            status = jsonArrayResponse.getStatus();

            System.out.println("---> MapaActivity: " + resource);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status == 200) {
                newMapManager.NewDestinationFound(imageAdapter, jsonArray);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Ups! Algo no ocurri\u00f3 como se esperaba.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class GetLastPosition extends AsyncTask<Void, Void, Void> {

        private Long userID;
        private JSONArray jsonArray;
        private Integer status;
        private String auxZone;
        private String auxMomentoPosicion;

        GetLastPosition(Long userID) {
            this.userID = userID;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String resource = "/lastposition/" + userID;
            JsonArrayResponse jsonArrayResponse = ConexionWebService.getJsonArray(resource);

            jsonArray = jsonArrayResponse.getJsonArray();
            status = jsonArrayResponse.getStatus();

            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    auxZone = jsonObject1.optString("zone");
                    auxMomentoPosicion = jsonObject1.optString("momentoPosicion");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("---> MapaActivity: " + resource);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status == 200) {

                lastPosition.Zone = auxZone;
                lastPosition.Time = auxMomentoPosicion;

                System.out.println("---> lastPosition: " + lastPosition.Zone);

            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Ups! Algo no ocurri\u00f3 como se esperaba.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }

    }
}
