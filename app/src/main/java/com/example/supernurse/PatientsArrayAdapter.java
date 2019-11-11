package com.example.supernurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.supernurse.models.Patient;
import java.util.List;

public class PatientsArrayAdapter extends ArrayAdapter<Patient> {
    private final Context context;
    private final List<Patient> patients;


    public PatientsArrayAdapter(@NonNull Context context, @NonNull List<Patient> objects) {
        super(context, R.layout.patient_rowlayout, objects);

        this.context = context;
        this.patients = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Pushing row to the list view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.patient_rowlayout, parent, false);

        //Assigning row UI elements to appropriate references
        TextView textViewName = (TextView) rowView.findViewById(R.id.name);
        TextView textViewLastName = (TextView) rowView.findViewById(R.id.last_name);

        //Assigning Patients data to UI elements
        textViewName.setText(patients.get(position).getFirst_name());
        textViewLastName.setText(patients.get(position).getLast_name());
        return rowView;
    }
}

