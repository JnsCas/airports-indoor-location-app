package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.Arrays;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.MainActivity;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application.MyApplication;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NotificationsManager {

    private Context context;
    private NotificationManager notificationManager;
    private int notificationIdLemon1 = 1;
    private int notificationIdBeetroot1 = 2;
    private int notificationIdCoconut1 = 3;
    private int notificationIdCandy1 = 4;

    public NotificationsManager(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification buildNotification(String title, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        return new NotificationCompat.Builder(context, "content_channel")
                .setSmallIcon(R.drawable.beacon_candy_small)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    public void startMonitoring() {
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(context, ((MyApplication) context).cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();

        ProximityZone zoneLemon1 = new ProximityZoneBuilder()
                .forTag("notification-lemon-1")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationIdLemon1, buildNotification("Hola!",
                                "Estás cerca del beacon lemon1"));
                        return null;
                    }
                })
                .build();

        ProximityZone zoneBeetroot1 = new ProximityZoneBuilder()
                .forTag("notification-beetroot-1") //FIXME Checkear en el cloud, esta seteado Estimote Monitoring -> off
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationIdBeetroot1, buildNotification("Hola!",
                                "Estás cerca del beacon beetroot1"));
                        return null;
                    }
                })
                .build();

        ProximityZone zoneCoconut1 = new ProximityZoneBuilder()
                .forTag("notification-coconut-1")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationIdCoconut1, buildNotification("Hola!",
                                "Estás cerca del beacon coconut1"));
                        return null;
                    }
                })
                .build();

        ProximityZone zoneCandy1 = new ProximityZoneBuilder()
                .forTag("notification-candy-1")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationIdCandy1, buildNotification("Hola!",
                                "Estás cerca del beacon candy1"));
                        return null;
                    }
                })
                .build();

        proximityObserver.startObserving(Arrays.asList(zoneLemon1, zoneBeetroot1, zoneCandy1, zoneCoconut1));
    }

}
