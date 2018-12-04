package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private TextView resultTV1;
    private TextView resultTV2;
    private TextView resultDuracionEstadiaPE1;
    private TextView resultDuracionEstadiaPE2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        resultTV1 = findViewById(R.id.info_people_quantity_result1);
        resultTV2 = findViewById(R.id.info_people_quantity_result2);
        resultDuracionEstadiaPE1 = findViewById(R.id.info_duracion_estadia1);
        resultDuracionEstadiaPE2 = findViewById(R.id.info_duracion_estadia2);

        new PostPeopleQuantity().execute();
        new GetEsperaActual().execute();

    }

    private class PostPeopleQuantity extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private JsonObjectResponse responsePE1;
        private JsonObjectResponse responsePE2;

        public PostPeopleQuantity() {
            this.progressDialog = new ProgressDialog(InfoActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Enviando datos...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Calendar fiveMinutesBefore = Calendar.getInstance();
            fiveMinutesBefore.add(Calendar.MINUTE, -5);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Map<String, String> requestParamPE1 = new HashMap<>();
            requestParamPE1.put("zone", "beetroot-1"); //FIXME: Hardcodeo fuerte
            requestParamPE1.put("since", sdf.format(fiveMinutesBefore.getTime()));
            responsePE1 = ConexionWebService.getJsonObject("/flowAnalysis/peopleQuantity", requestParamPE1);

            Map<String, String> requestParamPE2 = new HashMap<>();
            requestParamPE2.put("zone", "candy-1"); //FIXME: Hardcodeo fuerte
            requestParamPE2.put("since", sdf.format(fiveMinutesBefore.getTime()));

            System.out.println(">>>> requestParamPE1: " + requestParamPE1);

            responsePE2 = ConexionWebService.getJsonObject("/flowAnalysis/peopleQuantity", requestParamPE2);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            try {
                int result1 = responsePE1.getJsonObject().getInt("peopleQuantity");
                resultTV1.setText(String.valueOf(result1));
                resultTV1.setTypeface(null, Typeface.BOLD);

                int result2 = responsePE2.getJsonObject().getInt("peopleQuantity");
                resultTV2.setText(String.valueOf(result2));
                resultTV2.setTypeface(null, Typeface.BOLD);

                Log.d("---> InfoActivity","> peopleQuantity1: " + result1 + "> peopleQuantity2: " + result2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetEsperaActual extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private JsonObjectResponse responsePE1;
        private JsonObjectResponse responsePE2;

        GetEsperaActual() {
            this.progressDialog = new ProgressDialog(InfoActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Map<String, String> requestParamPE1 = new HashMap<>();
            requestParamPE1.put("zone", "br1"); //FIXME: Hardcodeo fuerte
            responsePE1 = ConexionWebService.getJsonObject("/duracionEstadia", requestParamPE1);

            Map<String, String> requestParamPE2 = new HashMap<>();
            requestParamPE2.put("zone", "ca1"); //FIXME: Hardcodeo fuerte
            responsePE2 = ConexionWebService.getJsonObject("/duracionEstadia", requestParamPE2);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                Double resultPE1 = responsePE1.getJsonObject().getDouble("duracionEstadia");
                @SuppressLint("DefaultLocale") String msgResult1= String.format("%.2f", resultPE1) + " Minutos";
                resultDuracionEstadiaPE1.setText(msgResult1);
                resultDuracionEstadiaPE1.setTypeface(null, Typeface.BOLD);

                Double resultPE2 = responsePE2.getJsonObject().getDouble("duracionEstadia");
                @SuppressLint("DefaultLocale") String msgResult2= String.format("%.2f", resultPE2) + " Minutos";
                resultDuracionEstadiaPE2.setText(msgResult2);
                resultDuracionEstadiaPE2.setTypeface(null, Typeface.BOLD);

                Log.d("---> InfoActivity","> msgResult1: " + msgResult1 + " > msgResult2: " + msgResult2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
