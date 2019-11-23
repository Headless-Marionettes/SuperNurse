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
        TextView textViewFloor= (TextView) rowView.findViewById(R.id.floor);
        TextView textViewDateOfBirth= (TextView) rowView.findViewById(R.id.date_of_birth);

        //Assigning Patients data to UI elements
        textViewName.setText(patients.get(position).getFirst_name() + " " + patients.get(position).getLast_name());
        textViewFloor.setText(patients.get(position).getRoom());
        textViewDateOfBirth.setText(patients.get(position).getDate_of_birth());

        return rowView;
    }
}

