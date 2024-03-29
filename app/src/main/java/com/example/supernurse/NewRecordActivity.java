package com.example.supernurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.supernurse.server_connection.ServerRequestQueue;
import com.example.supernurse.view_models.PatientViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class NewRecordActivity extends AppCompatActivity implements TextWatcher{

    public static final String TAG = "AddNewRecord";

    private String patientId;

    private EditText bloodPressureText;
    private EditText respiratoryText;
    private EditText bloodOxygenText;
    private EditText heartBeatText;

    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        bloodPressureText = findViewById(R.id.blood_pressure_text);
        respiratoryText = findViewById(R.id.respiratory_text);
        bloodOxygenText = findViewById(R.id.blood_oxygen_text);
        heartBeatText = findViewById(R.id.heart_beat_text);
        errorText = findViewById(R.id.new_record_error);

        bloodPressureText.addTextChangedListener(this);
        respiratoryText.addTextChangedListener(this);
        bloodOxygenText.addTextChangedListener(this);
        heartBeatText.addTextChangedListener(this);

        patientId = getIntent().getStringExtra("patientId");
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        bloodPressureText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        respiratoryText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        bloodOxygenText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        heartBeatText.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));

        errorText.setVisibility(View.INVISIBLE);
    }

    public void addReportPressed(View view) {

        String bloodPressure = bloodPressureText.getText().toString();
        String respiratory = respiratoryText.getText().toString();
        String bloodOxygen = bloodOxygenText.getText().toString();
        String heartBeat = heartBeatText.getText().toString();

        if (!bloodPressure.equals("") && !respiratory.equals("") && !bloodOxygen.equals("") && !heartBeat.equals("")) {
            try {
                //create body for the request

                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = dateFormatter.format(date);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("date", formattedDate);
                jsonBody.put("blood_pressure", bloodPressure);
                jsonBody.put("respiratory_rate", respiratory);
                jsonBody.put("blood_oxygen_level", bloodOxygen);
                jsonBody.put("heart_beat_rate", heartBeat);
                final String mRequestBody = jsonBody.toString();

                SharedPreferences myPref = getApplication().getSharedPreferences("UserSharedPreferences", getApplication().MODE_PRIVATE);
                final String token = "JWT " + myPref.getString("token", "");

                String url = "https://super-nurse.herokuapp.com/patients/" + patientId + "/records";
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(mRequestBody);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "No RESPONSE");

                    }
                }) {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", token);
                        return headers;
                    }

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
                ServerRequestQueue.getInstance(NewRecordActivity.this).addToRequestQueue(postRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            errorText.setVisibility(View.VISIBLE);
            setRedIfEmpty(bloodPressureText);
            setRedIfEmpty(respiratoryText);
            setRedIfEmpty(bloodOxygenText);
            setRedIfEmpty(heartBeatText);
        }
    }

    protected void setRedIfEmpty(EditText view) {
        if ((view.getText().toString()).equals("")) {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.colorError));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ServerRequestQueue.getInstance(this).getRequestQueue() != null) {
            ServerRequestQueue.getInstance(this).getRequestQueue().cancelAll(TAG);
        }
    }

}
