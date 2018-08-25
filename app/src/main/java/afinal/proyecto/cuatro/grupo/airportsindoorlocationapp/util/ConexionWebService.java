package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by JnsCas on 4/5/18.
 */

public class ConexionWebService {

    private static final String TAG = ConexionWebService.class.getName();

    /* ESTE ENDPOINT SE UTILIZA PARA PROBAR LOCALMENTE CORRIENDO DESDE EL EMULADOR*/
    private static final String ENDPOINT = "http://10.0.2.2:8080";
    private static final String API_KEY_VALUE = "12345678";

    public static JsonArrayResponse getJson(String resource) {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;

        JsonArrayResponse jsonArrayResponse = new JsonArrayResponse();

        String endpoint = ENDPOINT + resource;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(endpoint);
            httpget.addHeader("X-Api-Key", API_KEY_VALUE);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            //set status
            jsonArrayResponse.setStatus(response.getStatusLine().getStatusCode());
            is = entity.getContent();

        } catch (Exception e) {
            Log.e(TAG, "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }

        try {
            jArray = new JSONArray(result);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
        jsonArrayResponse.setJsonArray(jArray);

        return jsonArrayResponse;
    }

    public static JsonObjectResponse postJson(String resource, JSONObject json) {
        InputStream is = null;
        String result = "";
        JSONObject jObject = null;
        JsonObjectResponse jsonObjectResponse = new JsonObjectResponse();

        HttpPost httppost = getHttpPost(resource);


        // Download JSON data from URL
        try {

            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType("application/json");
            httppost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);

            is = response.getEntity().getContent();
            //set status
            jsonObjectResponse.setStatus(response.getStatusLine().getStatusCode());

        } catch (Exception e) {
            Log.e(TAG, "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }

        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
        jsonObjectResponse.setJsonObject(jObject);
        return jsonObjectResponse;
    }

    @NonNull
    private static HttpPost getHttpPost(String resource) {
        // Para que esta funcion pueda ser invocada para consumir apis externas.
        HttpPost httppost;
        if (resource.contains("http")) {
            httppost = new HttpPost(resource);
        } else {
            httppost = new HttpPost(ENDPOINT + resource);
            httppost.addHeader("X-Api-Key", API_KEY_VALUE);
        }
        //
        return httppost;
    }
}
