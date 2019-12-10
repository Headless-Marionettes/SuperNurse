package com.example.supernurse;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class AddressParser extends AsyncTask<Object, Void, String> {

    private String addressString;
    private GoogleMap mMap;
    private FragmentActivity context;
    private LinearLayout mapLinearLayout;

    List<Address> addresses;

    protected String doInBackground(Object... objects) {

        String responseData = "";

        try {
            mMap = (GoogleMap) objects[0];
            addressString = (String) objects[1];
            context = (FragmentActivity) objects[2];
            mapLinearLayout = (LinearLayout) objects[3];

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            addresses  = geocoder.getFromLocationName(addressString, 1);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {
        }

        return responseData;
    }

    protected void onPostExecute(String responseData) {

        // Add a marker in Sydney and move the camera
        LatLng marker = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(marker).title(addressString));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        mapLinearLayout.setVisibility(View.VISIBLE);
    }
}
