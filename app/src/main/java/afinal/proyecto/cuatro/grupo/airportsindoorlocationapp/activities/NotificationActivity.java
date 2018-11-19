package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

import static afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications.NotificationsManager.mapNotificationStatusByTag;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        final Switch comidasRapidas = findViewById(R.id.switchComidasRapidas);
        final Switch indumentariaDeportiva = findViewById(R.id.switchIndumentariaDeportiva);
        final Switch cafe = findViewById(R.id.switchCafe);

        //set switch with actual status value
        comidasRapidas.setChecked(mapNotificationStatusByTag.get("notification-candy-1") && mapNotificationStatusByTag.get("notification-candy-2"));
        indumentariaDeportiva.setChecked(mapNotificationStatusByTag.get("notification-coconut-2"));
        cafe.setChecked(mapNotificationStatusByTag.get("notification-lemon-1"));

        comidasRapidas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                mapNotificationStatusByTag.put("notification-candy-1", status);
                mapNotificationStatusByTag.put("notification-candy-2", status);
            }
        });

        indumentariaDeportiva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                mapNotificationStatusByTag.put("notification-coconut-2", status);
            }
        });

        cafe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                mapNotificationStatusByTag.put("notification-lemon-1", status);
            }
        });
    }
}
