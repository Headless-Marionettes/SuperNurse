package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.supernurse.models.Patient;
import com.example.supernurse.server_connection.GsonRequest;
import com.example.supernurse.server_connection.ServerRequestQueue;
import com.example.supernurse.view_models.PatientsListViewModel;

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

        // Setting title to the Activity
        this.setTitle(R.string.label_list);

        // Creates reference of ListView
        final ListView listview = findViewById(R.id.patientsList);

        // Initialize progressBar and set in visible
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        PatientsListViewModel model = ViewModelProviders.of(this).get(PatientsListViewModel.class);

        model.getPatientList().observe(this, patientlist -> {

            //Create Custom Array adapter and set it to listview
            PatientsArrayAdapter adapter = new PatientsArrayAdapter(AdminPatientsListActivity.this, patientlist);
            listview.setAdapter(adapter);

            //Remove progressBar
            progressBar.setVisibility(View.GONE);

            //Move to Patient Profile activity on row click
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent detailsIntent = new Intent(AdminPatientsListActivity.this, PatientProfileActivity.class);
                    detailsIntent.putExtra("patient", patientlist.get(position));

                    startActivity(detailsIntent);
                }
            });
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Cancel request with specified TAG
        if (ServerRequestQueue.getInstance(this).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(this).getRequestQueue().cancelAll(TAG);
        }
    }
}
