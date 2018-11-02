package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

@SuppressLint("Registered")
public class Details extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_details);

        /*
        Recibiendo el identificador de la imagen
         */
        Intent i = getIntent();
        int position = i.getIntExtra("position", -1);// -1 si no se encontró la referencia
        ImageAdapter adapter = new ImageAdapter(this);

        /*
        Seteando el recurso en el ImageView
         */
        ImageView originalImage = findViewById(R.id.originalImage);
        originalImage.setImageResource(adapter.getThumbId(position));
    }

}