package com.example.supernurse.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.example.supernurse.models.Invoice;

import java.io.Serializable;

public class LoadInvoicesService extends Service {
    public LoadInvoicesService() {
    }

    DoBackgroundTask mTask;


    private static String info_intent = "loadPDF";

    public static String getInfo_intetn() {
        return info_intent;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        info_intent = intent.getExtras().getString("info_intent");

        try {
            mTask = new DoBackgroundTask();
            mTask.execute(
                    new Invoice("Invoice 1", "https://docs.google.com/document/d/1XTgL7f_NkM6Wc_tXH3e8DzRlsx11ZoAtc_FoCgXgYu8/export?format=pdf"),
                    new Invoice("Invoice 2", "https://docs.google.com/document/d/1pd0fc8kQVFUtMoEFhLGoZWhSlds16ouOVD4_YnJjgS4/export?format=pdf"),
                    new Invoice("Invoice 3", "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf"),
                    new Invoice("Invoice 4", "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf"),
                    new Invoice("Invoice 5", "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf"),
                    new Invoice("Invoice 6", "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
//        return START_STICKY;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mTask != null) {
            mTask.cancel(true);
        }
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }


    private class DoBackgroundTask extends AsyncTask<Serializable, Void, Void> {

        @Override
        protected Void doInBackground(Serializable... invoices) {
            int count = invoices.length;

            for (int i = 0; i < count; i++) {

                //simulate taking some time to download an invoice
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(info_intent);
                broadcastIntent.putExtra(info_intent, invoices[i]);

                //broadcasting the intent to a receiver
                sendBroadcast(broadcastIntent);

                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }

    }

}
