package com.example.supernurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;


public class InvoicesArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> invoices;

    public InvoicesArrayAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.invoice_row, objects);

        this.context = context;
        this.invoices = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Pushing row to the list view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.invoice_row, parent, false);

        //Assigning row UI elements to appropriate references
        TextView textViewInvoice = (TextView) rowView.findViewById(R.id.invoiceId);

        //Assigning Invoices data to UI elements
        textViewInvoice.setText(invoices.get(position));

        return rowView;
    }
}
