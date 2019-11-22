package com.example.supernurse.fragments;

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

public class RecordsFragment extends Fragment {

    private PatientViewModel patientViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Initialise shared ViewModel
        patientViewModel =
                ViewModelProviders.of(requireActivity()).get(PatientViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_records, container, false);

        // Initialise textView UI element
        final TextView textView = root.findViewById(R.id.text_records);

        // Set Observer to patient data stored in shared ViewModel
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {
                textView.setText(p.getFirst_name()+" RECORDS");
            }
        });
        return root;
    }
}