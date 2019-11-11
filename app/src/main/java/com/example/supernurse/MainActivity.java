package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText loginEditText = findViewById(R.id.login_edit_text);
        final EditText passwordEditText = findViewById(R.id.password_edit_text);
        final Button authButton = findViewById(R.id.authenticate_button);
        final TextView authErrorTextView = findViewById(R.id.auth_error_text);

        authErrorTextView.setVisibility(View.INVISIBLE);


        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray arr =
                this.obtainStyledAttributes(typedValue.data, new int[]{
                        android.R.attr.textColorPrimary});
        final int primaryColor = arr.getColor(0, -1);
        arr.recycle();

        TextWatcher loginAndPasswordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginEditText.setTextColor(primaryColor);
                passwordEditText.setTextColor(primaryColor);
                authErrorTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginEditText.getText().toString().equals("User")
                        || !passwordEditText.getText().toString().equals("password")) {

                    authErrorTextView.setVisibility(View.VISIBLE);
                    loginEditText.setTextColor(0xFFFF0000);
                    passwordEditText.setTextColor(0xFFFF0000);

                } else {
                    Toast.makeText(MainActivity.this, "Authentication is successful", Toast.LENGTH_LONG).show();
                }

            }
        });


        loginEditText.addTextChangedListener(loginAndPasswordTextWatcher);
        passwordEditText.addTextChangedListener(loginAndPasswordTextWatcher);



        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        authErrorTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                authErrorTextView.setText("That didn't work!");
            }
        });

       // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
