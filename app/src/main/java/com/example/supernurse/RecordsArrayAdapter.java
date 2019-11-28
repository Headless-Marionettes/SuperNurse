package com.example.supernurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.supernurse.models.Record;

import java.util.List;

public class RecordsArrayAdapter extends ArrayAdapter<Record> {
    private final Context context;
    private final List<Record> records;

    public RecordsArrayAdapter(@NonNull Context context, @NonNull List<Record> objects) {
        super(context, R.layout.record_rowlayout, objects);
        this.context = context;
        this.records = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Pushing row to the list view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.record_rowlayout, parent, false);

        //Assigning row UI elements to appropriate references
        TextView textViewDate = rowView.findViewById(R.id.dateTextView);
        TextView textViewBloodPressure = rowView.findViewById(R.id.bloodPressureTextView);
        TextView textViewRespiratory = rowView.findViewById(R.id.respiratoryTextView);
        TextView textViewBloodOxygen = rowView.findViewById(R.id.bloodOxygenTextView);
        TextView textViewHeartBeat = rowView.findViewById(R.id.heartBeatTextView);

        //Assigning Patients data to UI elements
        textViewDate.setText(String.format("Record from %S",records.get(position).getDate()));
        textViewBloodPressure.setText(String.format("Blood Pressure: %S", records.get(position).getBlood_pressure()));
        textViewRespiratory.setText(String.format("Respiratory Rate: %S", records.get(position).getRespiratory_rate()));
        textViewBloodOxygen.setText(String.format("Blood Oxygen Level: %S", records.get(position).getBlood_oxygen_level()));
        textViewHeartBeat.setText(String.format("Heart Beat Rate: %S", records.get(position).getHeart_beat_rate()));

        return rowView;
    }
}
