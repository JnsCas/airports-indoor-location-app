package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;

public class UserSupportActivity extends AppCompatActivity {

    private ArrayAdapter<String> comboAdapter;
    private Spinner vuelosSpinner;
    private ArrayList<Vuelo> flights;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_support);

        idUser = getIntent().getIntExtra("idUser", 0);

        new GetFlights().execute();

        vuelosSpinner = findViewById(R.id.usersupport_vuelos_sp);

        vuelosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Vuelo vuelo = flights.get(position);

                //TextView numeroTV = findViewById(R.id.usersupport_result_numero);
                //numeroTV.setText(vuelo.getNumber());

                TextView destinoTV;
                destinoTV = findViewById(R.id.usersupport_result_destino);
                destinoTV.setText(vuelo.getDestination().toString());

                TextView fechaEmbarqueTV;
                fechaEmbarqueTV = findViewById(R.id.usersupport_result_fechaEmbarque);
                android.text.format.DateFormat df = new android.text.format.DateFormat();
                fechaEmbarqueTV.setText(df.format("yyyy-MM-dd hh:mm A", vuelo.getBoardingDateTime()));

                TextView puertaEmbarqueTV;
                puertaEmbarqueTV = findViewById(R.id.usersupport_result_puertaEmbarque);
                puertaEmbarqueTV.setText(vuelo.getBoardingGate().toString());

                TextView estadoTV;
                estadoTV = findViewById(R.id.usersupport_result_estado_de_vuelo);
                estadoTV.setText(vuelo.getStateFlight().getDescription());

                Log.d("------ UserSupport", vuelo.getStateFlight().getDescription());

                if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("CANCELADO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorCancelado));
                } else if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("EN HORARIO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorEnHorario));
                } else if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("DEMORADO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorDemorado));
                } else {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorBeacon1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void cargarVuelosEnSpinner() {
        List<String> listaVuelosStr = new ArrayList<>();
        for (Vuelo v: flights) {
            listaVuelosStr.add(v.toString());
        }
        comboAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaVuelosStr);
        //Cargo el spinner con los datos
        vuelosSpinner.setAdapter(comboAdapter);
    }

    private class GetFlights extends AsyncTask<Void, Void, Void> {

        private JsonObjectResponse response;
        private ProgressDialog progressDialog;

        public GetFlights() {
            this.progressDialog = new ProgressDialog(UserSupportActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Enviando datos..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            response = ConexionWebService
                    .getJsonObject("/user/" + idUser, null);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Log.d(this.getClass().getSimpleName(),"-- Response status: " + response.getStatus().toString());

            if (response.getStatus() == 200) {
                try {
                    JSONArray flightsJson = response.getJsonObject().getJSONArray("flights");

                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            .create();

                    flights = new ArrayList<>(Arrays.asList(gson.fromJson(flightsJson.toString(), Vuelo[].class)));
                    cargarVuelosEnSpinner();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo el error:" + response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
