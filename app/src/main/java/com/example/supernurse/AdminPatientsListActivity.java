package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.supernurse.models.Patient;
import com.example.supernurse.server_connection.GsonRequest;
import com.example.supernurse.server_connection.PatientsArrayAdapter;
import com.example.supernurse.server_connection.ServerRequestQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPatientsListActivity extends AppCompatActivity {

    public static final String TAG = "AdminPatientsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_patients_list);

        //Creates reference of ListView
        ListView listview = findViewById(R.id.patientsList);

        //Access token
        SharedPreferences myPref = getSharedPreferences("UserSharedPreferences", MODE_PRIVATE);
        final String token = myPref.getString("token", "");


        List<Patient> patients = new ArrayList();
        String url = "http://10.0.2.2:5000/patients";

        GsonRequest<Patient[]> patientsRequest = new GsonRequest<Patient[]>(url, Patient[].class, null, new Response.Listener<Patient[]>() {
            @Override
            public void onResponse(Patient[] response) {
                List<Patient> patients = Arrays.asList(response);
                patients.size();
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
        };

        // Set the tag on the request.
        patientsRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        ServerRequestQueue.getInstance(this).addToRequestQueue(patientsRequest);

        //Creating adapter variable and passing carIds array
        PatientsArrayAdapter adapter = new PatientsArrayAdapter(this, patients);
        listview.setAdapter(adapter);
    }
}
