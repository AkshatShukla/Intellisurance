package com.example.viteck.viteckchallenge;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import eu.amirs.JSON;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5;
    private GoogleMap map;
    MapView mapView;
    View mview;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview =  inflater.inflate(R.layout.fragment_map, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map1);
//        mapFragment.getMapAsync(this);
        return mview;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mview.findViewById(R.id.map1);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer .initialize(getContext());
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        zoomToLocation();
//        CameraPosition somePosition = CameraPosition.builder().target(new LatLng(41.316324, -72.922343)).zoom(16).bearing(0).tilt(45).build();
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(somePosition));

//        LatLng pp = new LatLng();

    }

    private void zoomToLocation() {
        if (ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) mview.getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = null;
            try {
                String provider = locationManager.getBestProvider(criteria, false);
                location = locationManager.getLastKnownLocation(provider);
            } catch (NullPointerException e) {
                return;
            }

            if (location != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude())));

//                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                List<Address> addresses = null;
//                try {
//                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String stateName = addresses.get(0).getAdminArea();

                /*
                //Toast.makeText(getContext(), stateName, Toast.LENGTH_LONG).show();
                try {
                    //Load File
                    //BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R..localjsonfile)));
                    BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().getAssets().open("vitechMap.json")));
                    StringBuilder jsonBuilder = new StringBuilder();
                    for (String line = null; (line = jsonReader.readLine()) != null; ) {
                        jsonBuilder.append(line).append("\n");
                    }

                    //Parse Json

                    JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
                    JSONObject someState = (JSONObject) jsonObject.get(stateName);
                    JSONArray cities = someState.names();
                    String blh = "";

                    if(Geocoder.isPresent()){
                        try {
                            for (int i = 0; i <= cities.length(); i++) {
                                String c = cities.getString(i);
                                String loc = "theNameOfTheLocation";
                                Geocoder gc = new Geocoder(getContext());
                                List<Address> address = gc.getFromLocationName(c, 5, location.getLatitude(), location.getLongitude(),location.getLatitude(), location.getLongitude()); //
                                // get the found Address Objects

                                List<LatLng> ll = new ArrayList<LatLng>(address.size()); // A list to save the coordinates if they are available
                            for (Address a : address) {
                                if (a.hasLatitude() && a.hasLongitude()) {
                                    ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                }
                            }
                        }
                        } catch (IOException e) {
                            // handle the exception
                        }
                    }
                   for (int i=0; i<=cities.length(); i++)
                   {
                       JSONObject j = (JSONObject) cities.get(i);

                       String cv ="";
                   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
            else {
                requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            zoomToLocation();
        }
    }

}
