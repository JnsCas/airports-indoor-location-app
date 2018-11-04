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

                Toast.makeText(
                        getApplicationContext(),
                        "Position: " + position,
                        Toast.LENGTH_LONG
                ).show();

                new GetWayFinding(position).execute();

            }
        });
    }

    private class GetWayFinding  extends AsyncTask<Void, Void, Void> {

        private int position;
        private JSONArray jsonArray;
        private Integer status;

        public GetWayFinding(int position) {
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayResponse jsonArrayResponse = ConexionWebService.getJson(
                    "/way-finding/1/1/4/4");
            jsonArray = jsonArrayResponse.getJsonArray();
            status = jsonArrayResponse.getStatus();

            Log.d("*** MapaActivity",
                    "jsonArrayResponse : " + jsonArrayResponse.toString());
            Log.d("*** MapaActivity",
                    "jsonArray : " + jsonArray);
            Log.d("*** MapaActivity",
                    "status: " + status);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status == 200) {
                newMapManager.NewDestinationFound(imageAdapter, position, jsonArray);
            }
        }
    }
}
