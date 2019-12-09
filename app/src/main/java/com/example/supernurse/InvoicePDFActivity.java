package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.supernurse.models.Invoice;

public class InvoicePDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_pdf);
        Intent intent = getIntent();
        Invoice invoice = (Invoice)intent.getSerializableExtra("invoice");

        // Setting title to the Activity
        this.setTitle(invoice.getName());

        WebView webView = findViewById(R.id.invoiceWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);

        String url = invoice.getUrl();
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
    }
}
