package com.example.supernurse.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.supernurse.R;
import com.example.supernurse.models.Patient;
import com.example.supernurse.view_models.PatientViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    private PatientViewModel patientViewModel;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialise shared ViewModel
        patientViewModel =
                ViewModelProviders.of(requireActivity()).get(PatientViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final TextView patientFullName = root.findViewById(R.id.patient_full_name);
        final TextView patientBirthday = root.findViewById(R.id.patient_birthday);
        final TextView patientRoom = root.findViewById(R.id.patient_room);

        // Set Observer to patient data stored in shared ViewModel
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {
                patientFullName.setText(p.getFirst_name() + " " + p.getLast_name());
                patientBirthday.setText(p.getDate_of_birth() + " " + p.getDate_of_birth());
                patientRoom.setText(p.getRoom());
            }
        });

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Address> addresses = new ArrayList<Address>();
        try
        {
            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
            addresses = geocoder.getFromLocationName("8 Woodhall Rd, Markham, Ontario", 1);
        }catch ( IOException ex) {
            ex.printStackTrace();
        }


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("8 Woodhall Rd, Markham, Ontario"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}