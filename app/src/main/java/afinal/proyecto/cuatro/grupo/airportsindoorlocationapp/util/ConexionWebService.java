package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

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
    public static final String ENDPOINT = "http://10.0.2.2:8080";

    public static JSONArray getJson(String resource) {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;
        String endpoint = ENDPOINT + resource;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            /*HttpPost httppost = new HttpPost(url);*/
            HttpGet httpget = new HttpGet(endpoint);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
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
        return jArray;
    }

    public static JsonResponse postJson(String resource, JSONObject json) {
        InputStream is = null;
        String result = "";
        JSONObject jObject = null;
        JsonResponse jsonResponse = new JsonResponse();
        String endpoint = ENDPOINT + resource;

        // Download JSON data from URL
        try {

            HttpPost httppost = new HttpPost(endpoint);
            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType("application/json");
            httppost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);

            is = entity.getContent();
            //set status
            jsonResponse.setStatus(response.getStatusLine().getStatusCode());

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
        jsonResponse.setJsonObject(jObject);
        return jsonResponse;
    }
}
