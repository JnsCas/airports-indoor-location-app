package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.User;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JSONfunctionsResponse;

public class SignInActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText emailEditText;
    private EditText contrasenaEditText;
    private EditText confirmarContrasenaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.nombreEditText                 = (EditText) findViewById(R.id.signin_nombre_et);
        this.emailEditText                  = (EditText) findViewById(R.id.signin_email_et);
        this.contrasenaEditText             = (EditText) findViewById(R.id.signin_contrasena_et);
        this.confirmarContrasenaEditText    = (EditText) findViewById(R.id.signin_confirmar_contrasena_et);

        buttonRegistrarUsuario();

    }

    private void buttonRegistrarUsuario() {
        Button btnRegistrar = (Button) findViewById(R.id.signin_registrar_usuario_btn);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                try {
                    User user = obtenerDatosDeUsuarioAInsertar();
                    new PostUser(user).execute();

                } catch (Throwable e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private User obtenerDatosDeUsuarioAInsertar() throws Throwable {
        User user = new User();
        user.setNombre(this.nombreEditText.getText().toString());
        user.setEmail(this.emailEditText.getText().toString());
        user.setContrasena(this.contrasenaEditText.getText().toString(), this.confirmarContrasenaEditText.getText().toString());
        return user;
    }

    private class PostUser extends AsyncTask<Void, Void, Void> {
        private User user;
        private JSONfunctionsResponse response;
        private JSONObject jsonObject;

        public PostUser(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           JSONObject json = new JSONObject();

            try {
                json.put("name", user.getNombre());
                json.put("email", user.getEmail());
                json.put("password", user.getContrasena());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            response = ConexionWebService
                    .postJSONfromURL("/user", json);

            jsonObject = response.getJsonObject();
            Integer status = response.getStatus();

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (response.getStatus() == 201) {
                Toast.makeText(getApplicationContext(), "Alta hecha!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo un error distinto a 201.", Toast.LENGTH_LONG).show(); //FIXME
            }
        }
    }

}
