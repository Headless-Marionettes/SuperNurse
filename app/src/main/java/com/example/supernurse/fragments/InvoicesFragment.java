package com.example.supernurse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.supernurse.AdminPatientsListActivity;
import com.example.supernurse.InvoicePDFActivity;
import com.example.supernurse.InvoicesArrayAdapter;
import com.example.supernurse.PatientProfileActivity;
import com.example.supernurse.R;
import com.example.supernurse.models.Patient;
import com.example.supernurse.view_models.PatientViewModel;

public class InvoicesFragment extends Fragment {

    private PatientViewModel patientViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Initialise shared ViewModel
        patientViewModel =
                ViewModelProviders.of(requireActivity()).get(PatientViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_invoices, container, false);

        final ListView listview = root.findViewById(R.id.invoicesList);
        //Create Custom Array adapter and set it to listview
       InvoicesArrayAdapter adapter = new InvoicesArrayAdapter(root.getContext(), patientViewModel.getPatient().getValue().getInvoices());
       listview.setAdapter(adapter);

        //Move to Patient Profile activity on row click
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent detailsIntent = new Intent(root.getContext(), InvoicePDFActivity.class);
                detailsIntent.putExtra("invoice", patientViewModel.getPatient().getValue().getInvoices().get(position));

                startActivity(detailsIntent);
            }
        });

        // Set Observer to patient data stored in shared ViewModel
        patientViewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient p) {

                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }
}