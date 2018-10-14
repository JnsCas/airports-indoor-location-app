package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;

import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.Details;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.ImageAdapter;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap.NewMapManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class MapaActivity extends AppCompatActivity {

    private NewMapManager newMapManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        /* Set image map as a grid view */
        GridView gridview = findViewById(R.id.newmapgridview);
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        /* Beacon listener */
        newMapManager = new NewMapManager(this, imageAdapter);

        /* Grid listener */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                /* Do this when you select an image */
                Intent i = new Intent(MapaActivity.this, Details.class);
                i.putExtra("position",position); // Image position
                startActivity(i);
            }
        });

        /* Wizard to check bluetooth connection for example */
        RequirementsWizardFactory
            .createEstimoteRequirementsWizard()
            .fulfillRequirements(this,
                new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        Log.d("*** MapaActivity", "Requirements fulfilled");
                        newMapManager.startMonitoring();
                        return null;
                    }
                },
                new Function1<List<? extends Requirement>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends Requirement> requirements) {
                        Log.e("*** MapaActivity", "Requirements missing: " + requirements);
                        return null;
                    }
                },
                new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("*** MapaActivity", "Requirements error: " + throwable);
                        return null;
                    }
                });
    }
}
