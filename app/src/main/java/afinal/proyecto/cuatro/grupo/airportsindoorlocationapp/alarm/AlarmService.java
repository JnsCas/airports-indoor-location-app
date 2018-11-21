package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.HomeActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;

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
        new PostEnvioPromo().execute();
        Log.d("AlarmService", "Notification sent.");
    }

    private class PostEnvioPromo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            JsonObjectResponse response;
            try {
                JSONObject body = new JSONObject();
                body.put("idUsuario", Long.parseLong(String.valueOf(HomeActivity.getIdUser())));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                body.put("momentoEnvio",  format.format(new Date()));
                body.put("beacon", null);
                body.put("flagPromo", 0);
                body.put("textoMultiuso", null);
                response = ConexionWebService.postJson("/envioPromo", body);
                if (response.getStatus() == 201) {
                    Log.d("AlarmService", "Post envioPromo OK.");
                } else {
                    Log.d("AlarmService", "Error in post envioPromo.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
