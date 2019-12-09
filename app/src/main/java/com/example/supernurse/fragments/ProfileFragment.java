package com.example.supernurse.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    private PatientViewModel patientViewModel;
    private GoogleMap mMap;
    private TextView emergencyAddress;

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
        final ImageView patientImage  = root.findViewById(R.id.patient_image);

        final TextView emergencyFullName = root.findViewById(R.id.emergency_fullname);
        final TextView emergencyPhoneNumber = root.findViewById(R.id.emergency_phonenumber);
        final TextView emergencyEmail = root.findViewById(R.id.emergency_email);
        emergencyAddress = root.findViewById(R.id.emergency_address);

        // Set Observer to patient data stored in shared ViewModel
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {
                patientFullName.setText(p.getFirst_name() + " " + p.getLast_name());
                patientBirthday.setText(p.getDate_of_birth() + " " + p.getDate_of_birth());
                patientRoom.setText(p.getRoom());
                patientImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), p.getImageId()));


                emergencyFullName.setText(p.getEmergencyContact().getName());
                emergencyPhoneNumber.setText(p.getEmergencyContact().getPhonenumber());
                emergencyEmail.setText(p.getEmergencyContact().getEmail());
                emergencyAddress.setText(p.getEmergencyContact().getAddress());
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
            addresses = geocoder.getFromLocationName(emergencyAddress.getText().toString(), 1);
        }catch ( IOException ex) {
            ex.printStackTrace();
        }


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(emergencyAddress.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}