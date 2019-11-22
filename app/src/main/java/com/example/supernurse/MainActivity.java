package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.supernurse.server_connection.ServerRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Login";
    RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the RequestQueue.
        queue = ServerRequestQueue.getInstance(this.getApplicationContext()).
                getRequestQueue();

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
                loginEditText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                passwordEditText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting email and password from editText
                String email = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                    //create body for the request
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                    final String mRequestBody = jsonBody.toString();

                    String url = "http://10.0.2.2:5000/auth/signin";
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    // Check if response has a token
                                    if (response.has("token")) {
                                        try {
                                            String token = response.getString("token");

                                            //Storing token in shared preferences
                                            SharedPreferences myPreference = getSharedPreferences("UserSharedPreferences", 0);
                                            SharedPreferences.Editor prefEditor = myPreference.edit();
                                            prefEditor.putString("token", token);
                                            prefEditor.apply();

                                            //Starting new intent
                                            Intent patients = new Intent(MainActivity.this, AdminPatientsListActivity.class);
                                            startActivity(patients);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        SharedPreferences myPreference = getSharedPreferences("UserSharedPreferences", 0);
                                        SharedPreferences.Editor prefEditor = myPreference.edit();
                                        prefEditor.putString("token", "");
                                        prefEditor.apply();

                                        authErrorTextView.setVisibility(View.VISIBLE);
                                        authErrorTextView.setText("CREDENTAILS ARE INVALID");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.d("Error.Response", "REQUEST FAILED");
                                    authErrorTextView.setVisibility(View.VISIBLE);
                                    loginEditText.setBackgroundTintList(getResources().getColorStateList(R.color.colorError));
                                    passwordEditText.setBackgroundTintList(getResources().getColorStateList(R.color.colorError));



                                    SharedPreferences myPreference = getSharedPreferences("UserSharedPreferences", 0);
                                    SharedPreferences.Editor prefEditor = myPreference.edit();
                                    prefEditor.putString("token", "");
                                    prefEditor.apply();
                                }
                            }
                    ) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };

                    // Set the tag on the request.
                    postRequest.setTag(TAG);

                    // Add the request to the RequestQueue.
                    ServerRequestQueue.getInstance(MainActivity.this).addToRequestQueue(postRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        loginEditText.addTextChangedListener(loginAndPasswordTextWatcher);
        passwordEditText.addTextChangedListener(loginAndPasswordTextWatcher);


        //STRING REQUEST
//        String url ="http://www.google.com";
        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        authErrorTextView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                authErrorTextView.setText("That didn't work!");
//            }
//        });

//        ////JSON REQUEST
        //       String url = "http://jsonplaceholder.typicode.com/users";
//        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        authErrorTextView.setText("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        authErrorTextView.setText(error.getMessage());
//
//                    }
//                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ServerRequestQueue.getInstance(this).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(this).getRequestQueue().cancelAll(TAG);
        }
    }
}
