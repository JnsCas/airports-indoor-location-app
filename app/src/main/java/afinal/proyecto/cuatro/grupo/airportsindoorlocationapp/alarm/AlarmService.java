package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.HomeActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;

public class AlarmService extends IntentService {

    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String flightNumber = intent.getStringExtra("flightNumber");
        String flightDestinationName = intent.getStringExtra("flightDestinationName");
        long minutesDifference = intent.getLongExtra("minutesDifference", 0);

        String msg = "El vuelo " + flightNumber + " con destino a " + flightDestinationName +
                " está por despegar" + (minutesDifference > 5 ? " en " + minutesDifference + " minutos" : "") + "!!";
        sendNotificationVuelo(msg);
    }

    private void sendNotificationVuelo(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this, "content_channel")
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.beacon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
