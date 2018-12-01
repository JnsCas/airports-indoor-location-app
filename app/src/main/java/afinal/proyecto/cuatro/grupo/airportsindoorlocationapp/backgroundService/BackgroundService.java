package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.backgroundService;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.TimeZone;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.HomeActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities.MapaActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Util;


public class BackgroundService extends IntentService {

    private static Context context;

    public BackgroundService() {
        super("BackgroundService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            context = getApplicationContext();
            new Thread(new Runnable(){
                public void run() {
                    while(true) {
                        try {
                            checkMethod();
                            //Thread.sleep(60000); // cada 1 minutos
                            Thread.sleep(300000); // cada 5 minutos
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();


        }
        Log.d("---> BackgroundService","> MyIntentService onHandleIntent() method is invoked.");
    }

    public void checkMethod() {

        /* 1   -> Consultar demora.
         * 2   -> Consultar hora actual.
         * 3   -> Consultar vuelo.
         * 3.1 -> Traer vuelo.puertaDeEmbarque.
         * 3.1 -> Traer vuelo.horaDeSalida.
         * 4   -> Cheque si hay que salir ((HoraActual + Demora) - HoraDeSalida > 10).
         * 5   -> Si hay que salir, notificar y sumar en la notificacion trigger para wayfinding.
         */

        new GetDemoraRecorrido().execute();

        Log.d("---> BackgroundService","> Checking demoraRecorrido...");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("---> BackgroundService","> MyIntentService onCreate() method is invoked.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("---> BackgroundService","> MyIntentService onDestroy() method is invoked.");
    }


    private static class GetDemoraRecorrido extends AsyncTask<Void, Void, Void> {

        private JsonObjectResponse response;
        private NotificationManager notificationManager;

        public GetDemoraRecorrido() {
            this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            response = ConexionWebService.getJsonObject("/demoraRecorrido/" + HomeActivity.getIdUser());

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            Log.d("---> BackgroundService","> Response status: " + response.getStatus().toString());

            if (response.getStatus() == 200) {
                try {

                    Double demoraRecorrido = response.getJsonObject().getDouble("demoraRecorrido");
                    Log.d("---> BackgroundService","> DemoraRecorrido: " + demoraRecorrido + " minutos.");

                    String flightString= response.getJsonObject().getJSONObject("flight").toString();
                    Vuelo flight = new Gson().fromJson(flightString, Vuelo.class);
                    Log.d("---> BackgroundService", "> Hora del pr\u00f3ximo vuelo: " + flight.getBoardingDateTime());

                    Calendar nowPlusDemora = Calendar.getInstance(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
                    int demoraRecorridoSeconds= redondear(demoraRecorrido);
                    nowPlusDemora.add(Calendar.SECOND, demoraRecorridoSeconds);

                    Calendar boardingCalendar = Calendar.getInstance();
                    boardingCalendar.setTime(flight.getBoardingDateTime());

                    long tiempoSobrante = Util.differenceMinutes(nowPlusDemora, boardingCalendar);
                    Log.d("---> BackgroundService","> Tiempo sobrante: " + tiempoSobrante + "min.");

                    if (tiempoSobrante > 0 && tiempoSobrante < 20) {

                        Intent intentMapa = new Intent(context, MapaActivity.class);
                        intentMapa.putExtra("beaconTag",flight.getBoardingGate().getBeaconTag());
                        intentMapa.putExtra("fromBackgroundService",true);
                        intentMapa.putExtra("idUser", HomeActivity.getIdUser());

                        // send notification
                        notificationManager.notify(
                                1,
                                buildNotification(
                                    "No llegues tarde!!",
                                    String.format("Tu vuelo sale en %smin y ten\u00e9s %smin de recorrido",
                                            Util.differenceMinutes(Calendar.getInstance(), boardingCalendar),
                                            demoraRecorrido.intValue()
                                    ),
                                    intentMapa
                                )
                        );
                        Log.d("---> BackgroundService"," > Notificacion enviada.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("---> BackgroundService", " > Se obtuvo el error: " + response.getStatus());
            }
        }

        private Notification buildNotification(String title, String text, Intent notificationIntent) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel contentChannel = new NotificationChannel(
                        "content_channel",
                        "Things near you",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(contentChannel);
            }

            return new NotificationCompat.Builder(context, "content_channel")
                    .setSmallIcon(R.drawable.beacon_candy_small)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(PendingIntent.getActivity(
                            context,
                            0,
                            notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT)
                    )
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
        }
    }

    /**
     * Convierto los minutos en segundos, y luego redondeo.
     * @param minutes
     * @return seconds
     */
    private static int redondear(Double minutes) {
        Double seconds = minutes * 60;
        return (int) Math.round(seconds);
    }
}
