package com.example.supernurse.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class LoadInvoicesService extends Service {
    public LoadInvoicesService() {
    }

    public static final String INFO_INTENT = "loadPDF";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        try {
            new DoBackgroundTask().execute(
                    new String("Invoice 1"),
                    new String("Invoice 2"),
                    new String("Invoice 3"),
                    new String("Invoice 4"),
                    new String("Invoice 5"),
                    new String("Invoice 6"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
//        return START_STICKY;

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private class DoBackgroundTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... invoices) {
            int count = invoices.length;

            for (int i = 0; i < count; i++) {

                //simulate taking some time to download an invoice
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(INFO_INTENT);
                broadcastIntent.putExtra(INFO_INTENT, invoices[i]);

                //broadcasting the intent to a receiver
                sendBroadcast(broadcastIntent);
            }
            return null;
        }

    }

}
