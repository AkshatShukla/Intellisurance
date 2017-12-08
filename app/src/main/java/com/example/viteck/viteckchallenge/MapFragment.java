package com.example.viteck.viteckchallenge;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import eu.amirs.JSON;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5;
    private GoogleMap map;
    MapView mapView;
    private String stateName;
    View mview;
    private static final int ALT_HEATMAP_RADIUS = 10;
    private static final double ALT_HEATMAP_OPACITY = 0.4;
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.rgb(255, 215, 0), //silver
            Color.rgb(211, 211, 211),//gold
            Color.rgb(212, 175, 55)//bronze,
            ,Color.rgb(160, 170, 191)//platinum
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f,0.30f
    };
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    HashMap<LatLng, Gradient> theMap = new HashMap<>();
    Thread thread;
    Location location = null;
    private int numCities = 50;
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


        location = getLastKnownLocation();
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateName = addresses.get(0).getAdminArea();
        theMap = processMapLatLng(numCities);
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
        try {
            zoomToLocation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        CameraPosition somePosition = CameraPosition.builder().target(new LatLng(41.316324, -72.922343)).zoom(16).bearing(0).tilt(45).build();
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(somePosition));

//        LatLng pp = new LatLng();

    }

    private void zoomToLocation() throws InterruptedException {
        if (ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            int count = 0;
            for (LatLng latLng : theMap.keySet())
            {
                if (count == numCities)
                {
                    break;
                }
                count++;
                List<LatLng> list = new ArrayList<>();
                list.add(latLng);
                mProvider = new HeatmapTileProvider.Builder()
                        .data(list)
                        .radius(50)
                        .gradient(theMap.get(latLng))
                        .build();
                // Add a tile overlay to the map, using the heat map tile provider.
                mOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
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
            try {
                zoomToLocation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<LatLng, Gradient> processMapLatLng(int count)
    {
        HashMap<LatLng, Gradient> locationMap = new HashMap<>();
        try {
            //Load File
            //BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R..localjsonfile)));
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().getAssets().open("vitechMap.json")));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null; ) {
                jsonBuilder.append(line).append("\n");
            }


            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            JSONObject someState = (JSONObject) jsonObject.get(stateName);
            JSONArray cities = someState.names();
            int bronzesum = 0;
            int silversum= 0;
            int goldsum= 0;
            int platinumsum= 0;
            for (int i = 0; i < count; i++)
            {
                JSONObject cityObject = (JSONObject) someState.get((String) cities.get(i));
                try
                {
                    int bronze = (int) cityObject.get("Bronze");
                    int silver = (int) cityObject.get("Silver");
                    int gold = (int) cityObject.get("Gold");
                    int platinum = (int) cityObject.get("Platinum");
                    bronzesum+=bronze;
                    silversum+=silver;
                    goldsum+=gold;
                    platinumsum+=platinum;
                }
                catch (Exception e)
                {
                    continue;
                }


            }
            String blh = "";

            if(Geocoder.isPresent()){
                try {
                    for (int i = 0; i <= count; i++) {
                        String c = cities.getString(i);
                        JSONObject cityObject = (JSONObject) someState.get((String) cities.get(i));
                        Geocoder gc = new Geocoder(getContext());
                        List<Address> address = gc.getFromLocationName(c + stateName, 5); //
                        // get the found Address Objects
                        try
                        {
                            int bronze = (int) cityObject.get("Bronze");
                            int silver = (int) cityObject.get("Silver");
                            int gold = (int) cityObject.get("Gold");
                            int platinum = (int) cityObject.get("Platinum");

                            for (Address a : address) {
                                if (a.hasLatitude() && a.hasLongitude()) {
                                    LatLng someLatLng = new LatLng(a.getLatitude(), a.getLongitude());
                                    float[] intensityPoints = {
                                            (float)silver/silversum, (float)gold/goldsum, (float)bronze/bronzesum,(float)platinum/platinumsum
                                    };
                                    Arrays.sort(intensityPoints);//I am sorting this which is going to put the order of silver,
                                    //gold,bronze, and platinum out of order i can fix this by also sorting the gradient_colors based
                                    //off of a key value pair where i sort the keys where the keys are the start points and the values
                                    //will then be in order!
                                    Pair<Float, Integer> testing = new Pair<Float, Integer>( (float) silver/  silversum, Color.rgb(255, 215, 0)); //silver
                                    Pair<Float, Integer> testing2 = new  Pair<Float, Integer>( (float) gold/  goldsum, Color.rgb(211, 211, 211));//gold
                                    Pair<Float, Integer> testing3 =  new Pair<Float, Integer>( (float) bronze/  bronzesum, Color.rgb(212, 175, 55));//bronze,
                                    Pair<Float, Integer> testing4 =  new Pair<Float, Integer>( (float) platinum/  platinumsum, Color.rgb(160, 170, 191));
                                    ArrayList<Pair<Float, Integer>>  health_gradient = new ArrayList<>();
                                    health_gradient.add(testing);
                                    health_gradient.add(testing2);
                                    health_gradient.add(testing3);
                                    health_gradient.add(testing4);
                                    health_gradient.sort(new Comparator<Pair<Float, Integer>>() {
                                        @Override
                                        public int compare(Pair<Float, Integer> o1, Pair<Float, Integer> o2) {
                                            return Float.compare(o1.first,o2.first);
                                        }
                                    });
                                     int[] final_gradient = new int [4];

                                    for (int k = 0; k < health_gradient.size(); k++)
                                    {
                                        final_gradient[k]= health_gradient.get(k).second;
                                    }




                                    Gradient someGradient = new Gradient(final_gradient,
                                            intensityPoints);
                                    locationMap.put(someLatLng, someGradient);


                                }
                            }
                        }
                        catch (Exception e)

                        {
                            e.printStackTrace();
                            continue;
                        }


                    }
                } catch (IOException e) {
                    // handle the exception
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return locationMap;

    }

    private Location getLastKnownLocation() {

        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mview.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager mLocationManager = (LocationManager) mview.getContext().getSystemService(LOCATION_SERVICE);

            mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);

            List<String> providers = mLocationManager.getProviders(true);
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        }
        return bestLocation;


    }



}
