package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.alarm.AlarmReceiver;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.backgroundService.BackgroundService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application.MyApplication;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Util;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import static afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.LoginActivity.PREFS_KEY;

public class HomeActivity extends AppCompatActivity {

    private Context context;
    private AlarmManager alarmMgr;

    private ArrayList<Vuelo> flights;
    private static int idUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        idUser = getIntent().getIntExtra("idUser", 0);
      
        final MyApplication application = (MyApplication) getApplication();

        /* Wizard to check bluetooth connection for example */
        RequirementsWizardFactory
            .createEstimoteRequirementsWizard()
            .fulfillRequirements(this,
                new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        Log.d("*** MainActivity", "Requirements fulfilled");
                            application.enableBeaconNotifications();
                            return null;
                    }
                },
                new Function1<List<? extends Requirement>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends Requirement> requirements) {
                        Log.e("*** MainActivity", "Requirements missing: " + requirements);
                        return null;
                    }
                },
                new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("*** MainActivity", "Requirements error: " + throwable);
                        return null;
                    }
                }
            );

        buttonVuelo();
        buttonMapa();
        buttonSupport();
        buttonNotification();

        /* Inicio de servicios en background */
        //FIXME esto estaria bueno iniciarlo solo si el usuario tiene un vuelo cargado para el dia de hoy o mañana
        Intent intentService = new Intent(this, BackgroundService.class);
        startService(intentService);

        new GetFlights().execute();
    }

    public static int getIdUser() {
        return idUser;
    }

    private void loadFlightsAlarm() {
        context = getApplicationContext();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (Vuelo vuelo: flights) {

            Calendar calNow = Calendar.getInstance();
            Calendar calBoardingDate = Calendar.getInstance();
            calBoardingDate.setTime(vuelo.getBoardingDateTime());

            //si el vuelo todavia no despego.
            if (calNow.before(calBoardingDate)) {
                long differenceMinutes = Util.differenceMinutes(calNow, calBoardingDate);
                if (differenceMinutes > 30) { //falta mas de media hora
                    Calendar calPlusHalfHour = Calendar.getInstance();
                    calPlusHalfHour.setTime(calBoardingDate.getTime());
                    calPlusHalfHour.add(Calendar.MINUTE, -30);
                    createNotification(vuelo, calPlusHalfHour, 30);
                }
                if (differenceMinutes > 10) { //falta mas de 10min
                    Calendar calTenMinutesBefore = Calendar.getInstance();
                    calTenMinutesBefore.setTime(calBoardingDate.getTime());
                    calTenMinutesBefore.add(Calendar.MINUTE, -10);
                    createNotification(vuelo, calTenMinutesBefore, 10);
                } else {
                    createNotification(vuelo, calNow, differenceMinutes);
                }
            }
        }
    }

    private void createNotification(Vuelo vuelo, Calendar cal, long minutesDifference) {
        Log.d("HomeActivity", "La alarma asociada al " + vuelo + " va a sonar a las: " + new Date(cal.getTimeInMillis()));

        Intent intent = new Intent(HomeActivity.this, AlarmReceiver.class);
        intent.putExtra("flightNumber", vuelo.getNumber());
        intent.putExtra("flightDestinationName", vuelo.getDestination().getName());
        intent.putExtra("minutesDifference", minutesDifference);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
    }

    /** dejo esta funcion por si se necesita en un futuro */
    private boolean isToday(Calendar now, Calendar calBoardingDate) {
        return now.get(Calendar.DAY_OF_MONTH) == calBoardingDate.get(Calendar.DAY_OF_MONTH) &&
                now.get(Calendar.MONTH) == calBoardingDate.get(Calendar.MONTH) &&
                now.get(Calendar.YEAR) == calBoardingDate.get(Calendar.YEAR);
    }

    private void buttonMapa() {
        ImageButton btnMapa = findViewById(R.id.mapa_imgbtn);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(getApplicationContext(), MapaActivity.class);
                intentMapa.putExtra("idUser", idUser);
                startActivity(intentMapa);
            }
        });
    }

    private void buttonVuelo() {
        ImageButton btnVuelo = findViewById(R.id.vuelo_imgbtn);

        btnVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVuelo = new Intent(getApplicationContext(), UserSupportActivity.class);
                intentVuelo.putExtra("idUser", idUser);
                startActivity(intentVuelo);
            }
        });
    }

    private void buttonSupport() {
        ImageButton btnInfo = findViewById(R.id.support_imgbtn);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intentInfo);
            }
        });
    }

    private void buttonNotification() {
        ImageButton btnInfo = findViewById(R.id.notification_imgbtn);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInfo = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intentInfo);
            }
        });
    }

    @Override
    public void onBackPressed() {
        /** logout: elimino credenciales guardadas */
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.remove("user");
        editor.remove("password");
        editor.apply();

        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }

    private class GetFlights extends AsyncTask<Void, Void, Void> {

        private JsonObjectResponse response;
        private ProgressDialog progressDialog;

        public GetFlights() {
            this.progressDialog = new ProgressDialog(HomeActivity.this);
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
                    loadFlightsAlarm();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo el error:" + response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
