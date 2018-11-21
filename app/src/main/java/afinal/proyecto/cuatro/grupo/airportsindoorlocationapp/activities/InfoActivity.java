package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;

public class InfoActivity extends AppCompatActivity {

    private TextView resultTV;
    private TextView resultDuracionEstadiaTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        resultTV = findViewById(R.id.info_people_quantity_result);
        resultDuracionEstadiaTV = findViewById(R.id.info_duracion_estadia);

        new PostPeopleQuantity().execute();
        new GetEsperaActual().execute();

    }

    private class PostPeopleQuantity extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private JsonObjectResponse response;

        public PostPeopleQuantity() {
            this.progressDialog = new ProgressDialog(InfoActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Enviando datos..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("zone", "lemon-2"); //FIXME hardcodeo fuerte. lemon-2 es la puerta de embarque
            Calendar fiveMinutesBefore = Calendar.getInstance();
            fiveMinutesBefore.add(Calendar.MINUTE, -5);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            requestParam.put("since", sdf.format(fiveMinutesBefore.getTime()));
            //requestParam.put("until", "");
            response = ConexionWebService
                    .getJsonObject("/flowAnalysis/peopleQuantity", requestParam);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            try {
                int result = response.getJsonObject().getInt("peopleQuantity");
                resultTV.setText(String.valueOf(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetEsperaActual extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private JsonObjectResponse response;

        public GetEsperaActual() {
            this.progressDialog = new ProgressDialog(InfoActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("zone", "lemon-2"); //FIXME hardcodeo fuerte. lemon-2 es la puerta de embarque
            response = ConexionWebService.getJsonObject("/flowAnalysis/duracionEstadia", requestParam);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            try {
                Double result = response.getJsonObject().getDouble("duracionEstadia");
                String msgResult= String.format("%.2f", result) + " Minutos";
                resultDuracionEstadiaTV.setText(msgResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
