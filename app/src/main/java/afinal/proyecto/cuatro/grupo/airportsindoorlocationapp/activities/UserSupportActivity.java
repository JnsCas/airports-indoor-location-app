package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonArrayResponse;

public class UserSupportActivity extends AppCompatActivity {

    ArrayAdapter<String> comboAdapter;
    List<Vuelo> listaVuelos;
    Spinner vuelosSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_support);

        new ObtenerVuelos().execute();

        vuelosSpinner = findViewById(R.id.usersupport_vuelos_sp);

        vuelosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Vuelo vuelo = listaVuelos.get(position);

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

    private class ObtenerVuelos extends AsyncTask<JSONObject, Void, Void> {
        private JsonArrayResponse jsonArrayResponse;
        private ProgressDialog progressDialog;

        public ObtenerVuelos() {
            listaVuelos = new ArrayList<>();
            this.progressDialog = new ProgressDialog(UserSupportActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Realizando consulta..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            jsonArrayResponse = ConexionWebService.getJson("/vuelo");
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (jsonArrayResponse == null) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show(); //FIXME
            } else if (jsonArrayResponse.getStatus() == 200) {
                //Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
                cargarVuelosEnSpinner();
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo el error " + jsonArrayResponse.getStatus(), Toast.LENGTH_LONG).show(); //FIXME
            }

        }

        private void cargarVuelosEnSpinner() {

            listaVuelos = Arrays.asList(new Gson().fromJson(jsonArrayResponse.getJsonArray().toString(), Vuelo[].class));

            List<String> listaVuelosStr = new ArrayList<>();

            for (Vuelo v: listaVuelos) {
                listaVuelosStr.add(v.toString());
            }

            comboAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaVuelosStr);
            //Cargo el spinner con los datos
            vuelosSpinner.setAdapter(comboAdapter);
        }
    }
}
