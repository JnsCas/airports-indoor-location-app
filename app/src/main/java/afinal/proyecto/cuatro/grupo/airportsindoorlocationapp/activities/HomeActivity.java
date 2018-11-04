package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;

import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application.MyApplication;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import java.util.Calendar;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.alarm.AlarmReceiver;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Vuelo> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        flights = getIntent().getParcelableArrayListExtra("flights");
      
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
        setFlightsAlarm();
    }

    private void setFlightsAlarm() {
        Context context = getApplicationContext();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Alarma dentro de 2 minutos
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 30);

        Log.d("HomeActivity", "La alarma va a sonar a las: " + calendar.get(Calendar.HOUR_OF_DAY) +
                ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

        // Repeticiones en intervalos de 5 minutos
        alarmMgr.set(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);

    }

    private void buttonMapa() {
        ImageButton btnMapa = findViewById(R.id.mapa_imgbtn);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMapa = new Intent(getApplicationContext(), MapaActivity.class);
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
                intentVuelo.putParcelableArrayListExtra("flights", flights);
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
}
