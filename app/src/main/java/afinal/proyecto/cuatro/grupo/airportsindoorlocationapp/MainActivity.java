package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.LoginActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application.MyApplication;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requirementsEstimote();

        int timeout = 10; // make the activity visible for 10 miliseconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginPage);
            }
        }, timeout);

    }

    private void requirementsEstimote() {
        final MyApplication application = (MyApplication) getApplication();

        RequirementsWizardFactory
            .createEstimoteRequirementsWizard()
            .fulfillRequirements(this,
                new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                    Log.d("app", "requirements fulfilled");
                    application.enableBeaconNotifications();
                    return null;
                    }
                },
                new Function1<List<? extends Requirement>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends Requirement> requirements) {
                    Log.e("app", "requirements missing: " + requirements);
                    return null;
                    }
                },
                new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "requirements error: " + throwable);
                        return null;
                    }
                }
            );
    }
}