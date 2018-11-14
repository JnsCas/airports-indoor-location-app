package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.FormatoEmailInvalidoException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model.User;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ConexionWebService;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.ExceptionUtil;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.JsonObjectResponse;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Security;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText contrasenaEditText;
    List<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailEditText      = findViewById(R.id.login_email_et);
        this.contrasenaEditText = findViewById(R.id.login_contrasena_et);

        cargarEditTextsObligatorios();

        buttonLogIn();
        buttonSignIn();
    }

    private void buttonSignIn() {
        Button btnSignIn = findViewById(R.id.login_signin_btn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);
            }
        });
    }

    private void cargarEditTextsObligatorios() {
        editTexts = new ArrayList<>();
        editTexts.add(emailEditText);
        editTexts.add(contrasenaEditText);
    }

    private void buttonLogIn() {
        Button btnLogin = findViewById(R.id.login_login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    User user = new User();
                    user.setEmail(emailEditText.getText().toString());
                    user.setContrasena(Security.getSHA512SecurePassword(contrasenaEditText.getText().toString()));
                    new PostUserLogin(user).execute();

                } catch (EditTextVacioException e) {
                    ExceptionUtil.setearErrorEditTextsCamposObligatorios(editTexts, e.getMessage());

                } catch (FormatoEmailInvalidoException emailException) {
                    emailEditText.setError(emailException.getMessage());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class PostUserLogin extends AsyncTask<Void, Void, Void> {
        private User user;
        private JsonObjectResponse response;
        private JSONObject jsonObject;
        private ProgressDialog progressDialog;

        public PostUserLogin(User user) {
            this.user = user;
            this.progressDialog = new ProgressDialog(LoginActivity.this);
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
                json.put("email", user.getEmail());
                json.put("password", user.getContrasena());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            response = ConexionWebService
                    .postJson("/user/login", json);

            jsonObject = response.getJsonObject();
            Integer status = response.getStatus();

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Log.d("LoginActivity","-- Response status: "+ response.getStatus().toString());

            if (response.getStatus() == 200) {
                try {

                    int idUser = response.getJsonObject().getInt("id");

                    Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    homeActivityIntent.putExtra("idUser", idUser);
                    startActivity(homeActivityIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se obtuvo el error:" + response.getStatus(), Toast.LENGTH_LONG).show(); //FIXME
            }
        }
    }
}
