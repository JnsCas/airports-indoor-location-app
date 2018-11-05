package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
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

    private static final String[] LOCATIONS = {
            "", // 0
            "Laboratorio", // 1
            "Pasillo Laboratorio A", // 2
            "Pasillo Laboratorio B", // 3
            "Pasillo Vertical A", // 4
            "Pasillo Vertical B", // 5
            "Pasillo Horizontal Norte", // 6
            "Pasillo Horizontal Sur", // 7
            "Baño Hombres" // 8
    };

    private String getNode(int position){

        String nodePosition;

        switch(position) {
            case 0:
                nodePosition = null;
                break;
            case 1: // node le2
                nodePosition = "1/1";
                break;
            case 2: // node br2
                nodePosition = "2/1";
                break;
            case 3: // node le1
                nodePosition = "3/2";
                break;
            case 4: // node ca2
                nodePosition = "3/3";
                break;
            case 5: // node co1
                nodePosition = "3/5";
                break;
            case 6: // node co2
                nodePosition = "4/5";
                break;
            case 7: // node br1
                nodePosition = "4/3";
                break;
            case 8: // node ca1
                nodePosition = "4/4";
                break;
            default:
                nodePosition = null;
                break;
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
        fromSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LOCATIONS));
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

        /* Spinner To */
        toSpinner = findViewById(R.id.to);
        toSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LOCATIONS));
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
                new GetWayFinding(nodeFrom,nodeTo).execute();
            }
        });
    }


    private class GetWayFinding extends AsyncTask<Void, Void, Void> {

        private  String nodeOrigin;
        private String nodeDestination;
        private JSONArray jsonArray;
        private Integer status;

        public GetWayFinding(String nodeOrigin, String nodeDestination) {
            this.nodeOrigin = nodeOrigin;
            this.nodeDestination = nodeDestination;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            JsonArrayResponse jsonArrayResponse = ConexionWebService.getJson(
                    "/way-finding/" + nodeOrigin + "/" + nodeDestination);
            jsonArray = jsonArrayResponse.getJsonArray();
            status = jsonArrayResponse.getStatus();

            Log.d("*** MapaActivity",
                    "/way-finding/" + nodeOrigin + nodeDestination);
            Log.d("*** MapaActivity",
                    "response : " + jsonArray);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status == 200) {
                newMapManager.NewDestinationFound(imageAdapter,jsonArray);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Ops! Algo no ocurrio como se esperaba.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }

    }
}
