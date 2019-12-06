package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.supernurse.server_connection.ServerRequestQueue;
import com.example.supernurse.view_models.PatientViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class NewRecordActivity extends AppCompatActivity {

    public static final String TAG = "AddNewRecord";

    private String patientId;

    private EditText bloodPressureText;
    private EditText respiratoryText;
    private EditText bloodOxygenText;
    private EditText heartBeatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        bloodPressureText = findViewById(R.id.blood_pressure_text);
        respiratoryText = findViewById(R.id.respiratory_text);
        bloodOxygenText = findViewById(R.id.blood_oxygen_text);
        heartBeatText = findViewById(R.id.heart_beat_text);

        patientId = getIntent().getStringExtra("patientId");
    }

    public void addReportPressed(View view) {

        String bloodPressure = bloodPressureText.getText().toString();
        String respiratory = respiratoryText.getText().toString();
        String bloodOxygen = bloodOxygenText.getText().toString();
        String heartBeat = heartBeatText.getText().toString();

        try {
            //create body for the request

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = dateFormatter.format(date);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("date", formattedDate);
            jsonBody.put("blood_pressure", bloodPressure);
            jsonBody.put("respiratory_rate", respiratory);
            jsonBody.put("blood_oxygen_level", bloodOxygen);
            jsonBody.put("heart_beat_rate", heartBeat);
            final String mRequestBody = jsonBody.toString();

            SharedPreferences myPref = getApplication().getSharedPreferences("UserSharedPreferences", getApplication().MODE_PRIVATE);
            final String token = "JWT " + myPref.getString("token", "");

            String url = "https://super-nurse.herokuapp.com/patients/" + patientId + "/records";
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(mRequestBody);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", "No RESPONSE");

                }
            }) {
                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", token);
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };


            // Set the tag on the request.
            postRequest.setTag(TAG);

            // Add the request to the RequestQueue.
            ServerRequestQueue.getInstance(NewRecordActivity.this).addToRequestQueue(postRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ServerRequestQueue.getInstance(this).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(this).getRequestQueue().cancelAll(TAG);
        }
    }

}
