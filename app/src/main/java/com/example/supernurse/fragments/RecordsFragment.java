package com.example.supernurse.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.supernurse.R;
import com.example.supernurse.RecordsArrayAdapter;
import com.example.supernurse.models.Patient;
import com.example.supernurse.models.Record;
import com.example.supernurse.view_models.PatientViewModel;
import com.example.supernurse.view_models.RecordsListViewModel;

import java.io.Console;
import java.util.List;

public class RecordsFragment extends Fragment {

    private PatientViewModel patientViewModel;
    private RecordsListViewModel recordsListViewModel;
    private ListView recordsListView;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Initialise shared ViewModel
        patientViewModel =
                ViewModelProviders.of(requireActivity()).get(PatientViewModel.class);

        recordsListViewModel = ViewModelProviders.of(this).get(RecordsListViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_records, container, false);
        recordsListView = root.findViewById(R.id.recordsList);

        // Initialise textView UI element
        textView = root.findViewById(R.id.text_records);

        // Set Observer to patient data stored in shared ViewModel
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {
                textView.setText(p.getFirst_name()+" RECORDS");

                getRecordsList(p.get_id());
            }
        });
        return root;
    }

    private void getRecordsList(String id) {
        recordsListViewModel.getRecordList(id).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                RecordsArrayAdapter adapter = new RecordsArrayAdapter(getActivity(), records);
                recordsListView.setAdapter(adapter);
            }
        });
    }
}