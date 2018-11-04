package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.ImageAdapter;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonArrayResponse;

public class MapaActivity extends AppCompatActivity {

    private NotificationsManager newMapManager;
    private ImageAdapter imageAdapter;

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

        /* Grid listener */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                new GetWayFinding(position).execute();
            }
        });
    }

    private class GetWayFinding extends AsyncTask<Void, Void, Void> {

        private int position;
        private JSONArray jsonArray;
        private Integer status;

        public GetWayFinding(int position) {
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String nodeOrigin = getNodeOrigin();
            String nodeDestination = getNodeDestination(position);

            JsonArrayResponse jsonArrayResponse = ConexionWebService.getJson(
                    "/way-finding/"+nodeOrigin+nodeDestination);
            jsonArray = jsonArrayResponse.getJsonArray();
            status = jsonArrayResponse.getStatus();

            Log.d("*** MapaActivity",
                    "/way-finding/"+nodeOrigin+nodeDestination);
            Log.d("*** MapaActivity",
                    "response : " + jsonArray);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status == 200) {

                Toast.makeText(
                        getApplicationContext(),
                        "New Position: " + position,
                        Toast.LENGTH_LONG
                ).show();

                newMapManager.NewDestinationFound(imageAdapter, position, jsonArray);
            }
        }

        private String getNodeOrigin(){
            return "1/1/";
        }

        private String getNodeDestination(int position){

            String nodePosition;

            switch(position) {
                case 0:
                    nodePosition = null;
                    break;
                case 1: // node co1
                    nodePosition = "3/5";
                    break;
                case 2: // node co2
                    nodePosition = "4/5";
                    break;
                case 3: // node co2
                    nodePosition = "4/5";
                    break;
                case 4:
                    nodePosition = null;
                    break;
                case 5: // node co1
                    nodePosition = "3/5";
                    break;
                case 6: // node co1
                    nodePosition = "3/5";
                    break;
                case 7: // node co2
                    nodePosition = "4/5";
                    break;
                case 8: // node le2
                    nodePosition = "1/1";
                    break;
                case 9: // node ca2
                    nodePosition = "3/3";
                    break;
                case 10: // node br1
                    nodePosition = "4/3";
                    break;
                case 11: // node br1
                    nodePosition = "4/3";
                    break;
                case 12:
                    nodePosition = null;
                    break;
                case 13: // node br2
                    nodePosition = "2/1";
                    break;
                case 14: // node br2
                    nodePosition = "2/1";
                    break;
                case 15:
                    nodePosition = null;
                    break;
                default:
                    nodePosition = null;
                    break;
            }
            return nodePosition;
        }
    }
}
