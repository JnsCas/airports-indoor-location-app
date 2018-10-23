package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.Vuelo;

public class UserSupportActivity extends AppCompatActivity {

    ArrayAdapter<String> comboAdapter;
    ArrayList<Vuelo> listaVuelos;
    Spinner vuelosSpinner;
    private ArrayList<Vuelo> flights;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_support);

        vuelosSpinner = findViewById(R.id.usersupport_vuelos_sp);
        cargarVuelosEnSpinner();
        vuelosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Vuelo vuelo = listaVuelos.get(position);

                //TextView numeroTV = findViewById(R.id.usersupport_result_numero);
                //numeroTV.setText(vuelo.getNumber());

                TextView destinoTV;
                destinoTV = findViewById(R.id.usersupport_result_destino);
                destinoTV.setText(vuelo.getDestination().toString());

                TextView fechaEmbarqueTV;
                fechaEmbarqueTV = findViewById(R.id.usersupport_result_fechaEmbarque);
                android.text.format.DateFormat df = new android.text.format.DateFormat();
                fechaEmbarqueTV.setText(df.format("yyyy-MM-dd hh:mm A", vuelo.getBoardingDateTime()));

                TextView puertaEmbarqueTV;
                puertaEmbarqueTV = findViewById(R.id.usersupport_result_puertaEmbarque);
                puertaEmbarqueTV.setText(vuelo.getBoardingGate().toString());

                TextView estadoTV;
                estadoTV = findViewById(R.id.usersupport_result_estado_de_vuelo);
                estadoTV.setText(vuelo.getStateFlight().getDescription());

                Log.d("------ UserSupport", vuelo.getStateFlight().getDescription());

                if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("CANCELADO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorCancelado));
                } else if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("EN HORARIO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorEnHorario));
                } else if (vuelo.getStateFlight().getDescription().equalsIgnoreCase("DEMORADO")) {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorDemorado));
                } else {
                    estadoTV.setTextColor(getResources().getColor(R.color.colorBeacon1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void cargarVuelosEnSpinner() {
        listaVuelos = getIntent().getParcelableArrayListExtra("flights");
        List<String> listaVuelosStr = new ArrayList<>();
        for (Vuelo v: listaVuelos) {
            listaVuelosStr.add(v.toString());
        }
        comboAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaVuelosStr);
        //Cargo el spinner con los datos
        vuelosSpinner.setAdapter(comboAdapter);
    }
}
