package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.application;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager;

public class MyApplication extends Application {

    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("utn-proximity-beacons-bcr", "c62d2fc621d3f4809a2d9eb87f77d111");
    private NotificationsManager notificationsManager;

    public void enableBeaconNotifications() {
        notificationsManager = new NotificationsManager(this);
        notificationsManager.startMonitoring();
    }

}
