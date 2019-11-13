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
import com.example.supernurse.server_connection.ServerRequestQueue;

import java.util.ArrayList;
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
        final ListView listview = findViewById(R.id.patientsList);


        //Access token from shared pref
        SharedPreferences myPref = getSharedPreferences("UserSharedPreferences", MODE_PRIVATE);
        final String token = "JWT " + myPref.getString("token", "");


        final List<Patient> patients = new ArrayList();

        //Using below url to request patients list
        String url = "http://10.0.2.2:5000/patients";

        GsonRequest<Patient[]> patientsRequest = new GsonRequest<Patient[]>(url, Patient[].class, null, new Response.Listener<Patient[]>() {
            @Override
            public void onResponse(Patient[] response) {
                for (Patient p : response) {
                    patients.add(p);
                }

                //Creating adapter variable and passing patients array
                PatientsArrayAdapter adapter = new PatientsArrayAdapter(AdminPatientsListActivity.this, patients);
                listview.setAdapter(adapter);
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
    }
}
