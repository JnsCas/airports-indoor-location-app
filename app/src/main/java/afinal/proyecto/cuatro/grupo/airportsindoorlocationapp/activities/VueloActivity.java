package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonResponse;

public class VueloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo);

        buttonBuscar();
    }

    private void buttonBuscar() {
        Button btnBuscar = (Button) findViewById(R.id.usersupport_buscar_btn);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numeroVueloET  = (EditText) findViewById(R.id.usersupport_nro_vuelo_et);
                EditText dateET         = (EditText) findViewById(R.id.usersupport_date_et);

                JSONObject request = new JSONObject();
                try {
                    request.put("NumeroDeVuelo", numeroVueloET.getText().toString());
                    request.put("Date", dateET.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new BuscarEstadoDeVueloPost().execute(request);

            }
        });
    }


    private class BuscarEstadoDeVueloPost extends AsyncTask<JSONObject, Void, Void> {
        private JsonResponse jsonResponse;
        private ProgressDialog progressDialog;

        public BuscarEstadoDeVueloPost() {
            this.progressDialog = new ProgressDialog(VueloActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Realizando consulta..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            jsonResponse = ConexionWebService.postJson(
                    "http://www.aerolineas.com.ar/es-ar/reservas_servicios/estado_de_vuelo",
                    jsonObjects[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (jsonResponse == null) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show(); //FIXME
            } else if (jsonResponse.getStatus() == 200) {
                Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();

                TextView resultSalidaTV = (TextView) findViewById(R.id.usersupport_result_salida);
                TextView resultLlegadaTV = (TextView) findViewById(R.id.usersupport_result_llegada);
                TextView resultEstadoTV = (TextView) findViewById(R.id.usersupport_result_estado_de_vuelo);

                Document document = null;
                try {
                    document = Jsoup.parse(jsonResponse.getJsonObject().get("view").toString());

                    //<li class="text-departure"> <i class="fa-li fa fa-plane fa-plane-departure more-info-tooltip" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Salida"> <span class="sr-only">;Salida</span> </i> <span>Buenos Aires (Ezeiza) (EZE)</span> Mayo 08 2018 </li>
                    Element elementSalida = document.select("li[class=text-departure]").first();
                    resultSalidaTV.setText(elementSalida.text().replace(";", ""));

                    //<li class="text-arrive"> <i class="fa-li fa fa-plane fa-plane-arrive more-info-tooltip" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Llegada"> <span class="sr-only">;Llegada</span> </i> <span>Bariloche (BRC)</span> Mayo 08 2018 </li>
                    Element elementLlegada = document.select("li[class=text-arrive]").first();
                    resultLlegadaTV.setText(elementLlegada.text().replace(";", ""));

                    //<p class="text-status takeoff"> <i class="fa fa-plane fa-plane-takeoff"></i> <span>&nbsp;&nbsp;Despegado</span> </p>
                    Element elementEstadoDeVuelo = document.select("p[class=text-status takeoff]").first();
                    resultEstadoTV.setText(elementEstadoDeVuelo.text());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo un error distinto a 200.", Toast.LENGTH_LONG).show(); //FIXME
            }

        }
    }
}
