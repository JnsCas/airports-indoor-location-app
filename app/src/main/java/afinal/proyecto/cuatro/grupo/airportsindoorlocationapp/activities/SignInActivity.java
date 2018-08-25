package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.ContrasenasDistintasException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.FormatoEmailInvalidoException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.User;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ExceptionUtil;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;

public class SignInActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText emailEditText;
    private EditText contrasenaEditText;
    private EditText confirmarContrasenaEditText;

    List<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.nombreEditText                 = findViewById(R.id.signin_nombre_et);
        this.emailEditText                  = findViewById(R.id.signin_email_et);
        this.contrasenaEditText             = findViewById(R.id.signin_contrasena_et);
        this.confirmarContrasenaEditText    = findViewById(R.id.signin_confirmar_contrasena_et);

        cargarEditTextsObligatorios();
        buttonRegistrarUsuario();
    }

    private void cargarEditTextsObligatorios() {
        editTexts = new ArrayList<>();
        editTexts.add(nombreEditText);
        editTexts.add(emailEditText);
        editTexts.add(contrasenaEditText);
        editTexts.add(confirmarContrasenaEditText);
    }

    private void buttonRegistrarUsuario() {
        Button btnRegistrar = findViewById(R.id.signin_registrar_usuario_btn);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                try {
                    User user = obtenerDatosDeUsuarioAInsertar();
                    new PostUser(user).execute();

                } catch (EditTextVacioException e) {
                    ExceptionUtil.setearErrorEditTextsCamposObligatorios(editTexts, e.getMessage());
                } catch (FormatoEmailInvalidoException emailException) {
                    emailEditText.setError(emailException.getMessage());
                } catch (ContrasenasDistintasException c) {
                    contrasenaEditText.setError(c.getMessage());
                    confirmarContrasenaEditText.setError(c.getMessage());
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private User obtenerDatosDeUsuarioAInsertar() throws EditTextVacioException, FormatoEmailInvalidoException, UnsupportedEncodingException, ContrasenasDistintasException {
        User user = new User();
        user.setNombre(this.nombreEditText.getText().toString());
        user.setEmail(this.emailEditText.getText().toString());
        user.setContrasena(this.contrasenaEditText.getText().toString(), this.confirmarContrasenaEditText.getText().toString());
        return user;
    }

    private class PostUser extends AsyncTask<Void, Void, Void> {
        private User user;
        private JsonObjectResponse response;
        private JSONObject jsonObject;
        private ProgressDialog progressDialog;

        public PostUser(User user) {
            this.user = user;
            this.progressDialog = new ProgressDialog(SignInActivity.this);
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
                Toast.makeText(getApplicationContext(), "Usuario registrado.", Toast.LENGTH_LONG).show();
                Intent loginActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivityIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo el error." + response.getStatus(), Toast.LENGTH_LONG).show(); //FIXME
            }
        }
    }


}
