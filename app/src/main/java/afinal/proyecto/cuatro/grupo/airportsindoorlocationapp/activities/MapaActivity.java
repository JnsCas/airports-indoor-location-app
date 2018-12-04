package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.ImageAdapter;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonArrayResponse;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;

public class MapaActivity extends AppCompatActivity {

    private NotificationsManager newMapManager;
    private ImageAdapter imageAdapter;
    Spinner toSpinner;
    String nodeFrom;
    String nodeTo;

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
            case 7: // node br2
                nodePosition = "2/1";
                break;
            case 8: // node le1
                nodePosition = "3/2";
                break;
            default:
                nodePosition = null;
                break;
        }
        return nodePosition;
    }

    /* Get the position number in base a full name beacon */
    private int getPosition(String beacon) {

        int node;

        switch (beacon) {
            case "lemon-2":
                node = 1;
                break;
            case "candy-2":
                node = 2;
                break;
            case "coconut-1":
                node = 3;
                break;
            case "coconut-2":
                node = 4;
                break;
            case "beetroot-1":
                node = 5;
                break;
            case "candy-1":
                node = 6;
                break;
            case "beetroot-2":
                node = 7;
                break;
            case "lemon-1":
                node = 8;
                break;
            case "br1":
                node = 5;
                break;
            case "ca1":
                node = 6;
                break;
            default:
                node = 0;
                break;
        }
        return node;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        /* New implementation of last position by backend information */
        final int idUser = getIntent().getIntExtra("idUser", 0);
        new GetLastPosition((long) idUser).execute();
        System.out.println("---> MapaActivity > userID : " + idUser);

        /* Set image map as a grid view */
        GridView gridview = findViewById(R.id.newmapgridview);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        /* Send Beacon listener information */
        newMapManager = NotificationsManager.getInstance();
        newMapManager.NewMapManager(this, imageAdapter);

        /* Spinner To */
        toSpinner = findViewById(R.id.to);
        toSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LOCATIONS));

        /* Check if the activity is activated from background */
        Boolean isFromBackground = getIntent().getBooleanExtra ("fromBackgroundService", false);

        if (isFromBackground) {
            String node = getIntent().getStringExtra("beaconTag");
            //nodeTo = getNode(getPosition(node));
            System.out.println("---> MapaActivity > isFromBackground -> beaconTag: " + node);
            toSpinner.setSelection(getPosition(node));
        }

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nodeTo = getNode(position);
                new GetLastPosition((long) idUser).execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class GetLastPosition extends AsyncTask<Void, Void, Void> {

        private Long userID;
        private JSONObject jsonObject;
        private Integer status;
        private String auxZone;

        GetLastPosition(Long userID) {
            this.userID = userID;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (userID != null) {

                String resource = "/lastposition/" + userID;

                JsonObjectResponse jsonObjectResponse = ConexionWebService.getJsonObject(resource);
                status = jsonObjectResponse.getStatus();

                if (status == 200) {

                    jsonObject = jsonObjectResponse.getJsonObject();

                    auxZone = jsonObject.optString("zone");

                    nodeFrom = getNode(getPosition(auxZone));

                } else {

                    status = 200;
                    auxZone = "lemon-2";
                }

                System.out.println("---> MapaActivity > auxZone: " + auxZone);

                // auxMomentoPosicion = jsonObject.optString("momentoPosicion");

            } else {

                status = 0;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (status == 200) {

                new GetWayFinding(nodeFrom, nodeTo).execute();

                System.out.println("---> MapaActivity > nodeFrom: " + nodeFrom);

            } else if (status == 0) {
                Toast.makeText(
                        getApplicationContext(),
                        "Ups! Parece que, por alg\u00fan motivo, no se encontr\u00f3 motivo al usuario .",
                        Toast.LENGTH_LONG
                ).show();

            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Ups! Algo no ocurri\u00f3 como se esperaba con el usuario registrado.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }

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

            status = 0;

            System.out.println("---> MapaActivity > nodeOrigin " + nodeOrigin + " and nodeDestination " + nodeDestination);

            if (nodeOrigin != null && nodeDestination != null) {

                String resource = "/way-finding/" + nodeOrigin + "/" + nodeDestination;

                System.out.println("---> MapaActivity > resource: " + resource);

                JsonArrayResponse jsonArrayResponse = ConexionWebService.getJsonArray(resource);

                jsonArray = jsonArrayResponse.getJsonArray();

                status = jsonArrayResponse.getStatus();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (status == 200) {

                newMapManager.NewDestinationFound(imageAdapter, jsonArray);

            }  else if (status == 0) {

                // nada

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
