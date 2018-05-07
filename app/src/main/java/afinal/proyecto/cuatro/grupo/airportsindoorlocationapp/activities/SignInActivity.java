package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.FormatoEmailInvalidoException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.User;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonResponse;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

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
                    new PostUser(user, SignInActivity.this).execute();

                } catch (Throwable e) {
                    setearError(e);
                }
            }
        });
    }

    private void setearError(Throwable e) {
        if (e instanceof EditTextVacioException) {
            if (Validaciones.isNullOrEmpty(this.nombreEditText.getText().toString())) {
                this.nombreEditText.setError(e.getMessage());
            } else {
                this.emailEditText.setError(e.getMessage());
            }
        } else if (e instanceof FormatoEmailInvalidoException) {
            this.emailEditText.setError(e.getMessage());
        }
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
        private JsonResponse response;
        private JSONObject jsonObject;
        private ProgressDialog progressDialog;

        public PostUser(User user, SignInActivity signInActivity) {
            this.user = user;
            this.progressDialog = new ProgressDialog(signInActivity);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Enviando datos..");
            progressDialog.show();
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
                    .postJson("/user", json);

            jsonObject = response.getJsonObject();
            Integer status = response.getStatus();



            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (response.getStatus() == 201) {
                Toast.makeText(getApplicationContext(), "Alta hecha!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo un error distinto a 201.", Toast.LENGTH_LONG).show(); //FIXME
            }
        }
    }

}
