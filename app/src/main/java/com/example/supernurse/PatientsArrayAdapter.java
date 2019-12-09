package com.example.supernurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
        TextView textViewRoom = (TextView) rowView.findViewById(R.id.room);
        TextView textViewDateOfBirth = (TextView) rowView.findViewById(R.id.date_of_birth);

        //Assigning Patients data to UI elements
        textViewName.setText(patients.get(position).getFirst_name() + " " + patients.get(position).getLast_name());
        textViewRoom.setText(patients.get(position).getRoom());
        textViewDateOfBirth.setText(patients.get(position).getDate_of_birth());

        ImageView image = rowView.findViewById(R.id.appCompatImageView);

        if (position % 3 == 0) {
            image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.three));
            patients.get(position).setImageId(R.drawable.three);
        } else if (position % 2 == 0) {
            image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.two));
            patients.get(position).setImageId(R.drawable.two);
        } else {
            image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.one));
            patients.get(position).setImageId(R.drawable.one);
        }

        return rowView;
    }
}

