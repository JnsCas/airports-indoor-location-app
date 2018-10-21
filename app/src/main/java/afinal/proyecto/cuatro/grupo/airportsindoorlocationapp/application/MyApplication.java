package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;

public class MyApplication extends Application {

    /* Prod credentials  */
    private static final String APPID = "utn-proximity-beacons-bcr";
    private static final String APPTK = "c62d2fc621d3f4809a2d9eb87f77d111";

    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials(APPID, APPTK);

    public void enableBeaconNotifications() {

        final NotificationsManager notificationsManager = NotificationsManager.getInstance();
        notificationsManager.NotificationsManager(this);

        notificationsManager.startMonitoring();

    }

}
