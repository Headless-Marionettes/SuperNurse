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
import com.example.supernurse.models.Record;
import com.example.supernurse.server_connection.GsonRequest;
import com.example.supernurse.server_connection.ServerRequestQueue;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Record>> mRecordList;
    public static final String TAG = "PatientsRecordList";

    public RecordsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Record>> getRecordList(String id) {
        if (mRecordList == null) {
            mRecordList = new MutableLiveData<>();
        }

        return mRecordList;
    }

    public void loadRecords(String id) {
        //Access token from shared pref
        SharedPreferences myPref = getApplication().getSharedPreferences("UserSharedPreferences", getApplication().MODE_PRIVATE);
        final String token = "JWT " + myPref.getString("token", "");

        final List<Record> records = new ArrayList();
        String url = String.format("https://super-nurse.herokuapp.com/patients/%S/records", id);

        GsonRequest<Record[]> recordsRequest = new GsonRequest<Record[]>(url, Record[].class, null, new Response.Listener<Record[]>() {
            @Override
            public void onResponse(Record[] response) {
                for (Record r : response) {
                    records.add(r);
                }
                Collections.reverse(records);
                mRecordList.setValue(records);
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
        recordsRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        ServerRequestQueue.getInstance(getApplication().getApplicationContext()).addToRequestQueue(recordsRequest);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (ServerRequestQueue.getInstance(getApplication().getApplicationContext()).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(getApplication().getApplicationContext()).getRequestQueue().cancelAll(TAG);
        }
    }
}
