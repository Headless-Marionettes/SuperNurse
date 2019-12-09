package com.example.supernurse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.supernurse.models.Invoice;
import com.example.supernurse.models.Patient;
import com.example.supernurse.services.LoadInvoicesService;
import com.example.supernurse.view_models.PatientViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PatientProfileActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private String patientId;
    private Patient thePatient;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        //Get Patient from previous intent
        Intent detailsIntent = getIntent();
        thePatient = (Patient) detailsIntent.getSerializableExtra("patient");

        //  Initialise ViewModel here
        patientViewModel =
                ViewModelProviders.of(this).get(PatientViewModel.class);

        //  Set patient to ViewModel, which will be shared between fragments
        patientViewModel.setPatient(thePatient);

        //  Setting bottom navigation bar:
        //
        //  Below code came from navigation bar template
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //  Passing each menu ID as a set of Ids because each
        //  menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_records, R.id.navigation_invoices)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

       // if (thePatient.getInvoices().size() == 0) {
        Intent serviceIntent =  new Intent(getBaseContext(), LoadInvoicesService.class);
        serviceIntent.putExtra("info_intent", thePatient.get_id());
        startService(serviceIntent);
     //   }
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String getInfo_intetn = thePatient.get_id();

            if (action.equals(getInfo_intetn)) {
                Invoice info = (Invoice)intent.getSerializableExtra(getInfo_intetn);

                patientViewModel.addInvoice(info);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //This needs to be in the activity that will end up receiving the broadcast
        registerReceiver(receiver, new IntentFilter(thePatient.get_id()));


        //Get patient ID to pass to newRecordActivity
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {
                patientId = p.get_id();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PatientProfileActivity.this, NewRecordActivity.class);
        intent.putExtra("patientId", patientId);
        switch (item.getItemId()) {
            case R.id.add_record:
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        stopService(new Intent(getBaseContext(), LoadInvoicesService.class));
        super.onDestroy();
    }
}
