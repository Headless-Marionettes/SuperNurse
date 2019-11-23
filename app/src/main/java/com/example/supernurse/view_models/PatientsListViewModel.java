package com.example.supernurse.view_models;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class PatientsListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Patient>> mPatientList;
    public static final String TAG = "AdminPatientsList";

    public PatientsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Patient>> getPatientList() {
        if (mPatientList == null) {
            mPatientList = new MutableLiveData<>();
            loadPatients();
        }
        return mPatientList;
    }

    /**
     * ASYNC function to load Patients data from the server,
     * using volley's GSON request
     */
    private void loadPatients() {
        //Access token from shared pref
        SharedPreferences myPref = getApplication().getSharedPreferences("UserSharedPreferences", getApplication().MODE_PRIVATE);
        final String token = "JWT " + myPref.getString("token", "");


        final List<Patient> patients = new ArrayList();
        String url = "http://10.0.2.2:5000/patients";

        GsonRequest<Patient[]> patientsRequest = new GsonRequest<Patient[]>(url, Patient[].class, null, new Response.Listener<Patient[]>() {
            @Override
            public void onResponse(Patient[] response) {
                for (Patient p : response) {
                    patients.add(p);
                }
                mPatientList.setValue(patients);
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
        ServerRequestQueue.getInstance(getApplication().getApplicationContext()).addToRequestQueue(patientsRequest);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (ServerRequestQueue.getInstance(getApplication().getApplicationContext()).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(getApplication().getApplicationContext()).getRequestQueue().cancelAll(TAG);
        }
    }
}
