package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.backgroundService;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;


public class backgroundService extends IntentService {


    public backgroundService() {
        super("backgroundService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            new Thread(new Runnable(){
                public void run() {

                    while(true) {

                        try {

                            Thread.sleep(60000); // cada 1 minutos

                            // Thread.sleep(300000); // cada 5 minutos

                            checkMethod();

                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }
                }
            }).start();


        }
        Log.d("---> backgroundService","> MyIntentService onHandleIntent() method is invoked.");
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

        Log.d("---> backgroundService","> MyIntentService checkMethod() is invoked.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("---> backgroundService","> MyIntentService onCreate() method is invoked.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("---> backgroundService","> MyIntentService onDestroy() method is invoked.");
    }

}
