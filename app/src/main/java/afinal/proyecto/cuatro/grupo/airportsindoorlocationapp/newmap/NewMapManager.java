package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.content.Context;
import android.util.Log;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.Arrays;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NewMapManager {

    /* Beacon recognition distance  */
    private static final Double DISTANCE = 2.0;

    /* Prod credentials  */
    private static final String APPID = "utn-proximity-beacons-bcr";
    private static final String APPTK = "c62d2fc621d3f4809a2d9eb87f77d111";

    private EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials(APPID, APPTK);

    private ImageAdapter imageAdapter;

    private Context context;

    public NewMapManager(Context context, ImageAdapter imageAdapter) {
        this.context = context;
        this.imageAdapter = imageAdapter;
    }

    public void startMonitoring() {
        ProximityObserver newMapProximityObserver =
            new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("*** NewMapManager", "Proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .withEstimoteSecureMonitoringDisabled()
                .withTelemetryReportingDisabled()
                .build();

        ProximityZone zoneCandy2 = createZoneFromBeacon("candy-2");
        ProximityZone zoneLemon2 = createZoneFromBeacon("lemon-2");
        ProximityZone zoneBeetroot2 = createZoneFromBeacon("beetroot-2");

        newMapProximityObserver.startObserving(Arrays.asList(zoneCandy2,zoneLemon2,zoneBeetroot2));
    }

    private ProximityZone createZoneFromBeacon(final String tag){

        return new ProximityZoneBuilder()
            .forTag(tag)
            .inCustomRange(DISTANCE)
            .onEnter(new Function1<ProximityZoneContext, Unit>() {
                @Override
                public Unit invoke(ProximityZoneContext proximityContext) {

                    Log.i("*** NewMapManager","Beacon coming-in: "+tag);

                    ContentZone contentZone = new ContentZone(tag, true, proximityContext);

                    imageAdapter.adjustMapWith(contentZone);
                    imageAdapter.notifyDataSetChanged();

                    return null;
                }
            })
            .onExit(new Function1<ProximityZoneContext, Unit>() {
                @Override
                public Unit invoke(ProximityZoneContext proximityContext) {

                    Log.i("*** NewMapManager","Beacon coming-out: "+tag);

                    ContentZone contentZone = new ContentZone(tag, false, proximityContext);

                    imageAdapter.adjustMapWith(contentZone);
                    imageAdapter.notifyDataSetChanged();

                    return null;
                }
            })
            .build();
    }


}