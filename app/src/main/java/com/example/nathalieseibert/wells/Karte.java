package com.example.nathalieseibert.wells;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Karte extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private View rootView;
    private DatabaseHelper myDB;
    private static final int MY_REQUEST_INT = 200;
    private LatLng GRAZ = new LatLng(47.071349, 15.440457);
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean permissionsGranted = false;
    int nummer;
    Float [] longitude = {
            47.115089f, 47.045327f, 47.10901f, 47.05199f, 47.096147f, 47.071981f, 47.048412f, 47.07825f, 47.066614f, 47.075883f,
            47.072459f, 47.066627f, 47.09328f, 47.070637f, 47.063031f, 47.06186f, 47.02739f, 47.076479f, 47.06895f, 47.059141f, 47.066898f,
            47.062052f, 47.076082f, 47.053402f, 47.050887f, 47.083247f, 47.071227f, 47.071055f, 47.070949f, 47.067908f, 47.082082f, 47.083574f,
            47.072382f, 47.072292f, 47.067499f, 47.066009f, 47.068385f, 47.048859f, 47.070638f, 47.073893f, 47.051998f, 47.035932f, 47.076371f,
            47.075162f, 47.068645f, 47.0771f, 47.083555f, 47.070963f, 47.02409f, 47.063859f, 47.063315f, 47.050065f, 47.095426f, 47.095075f, 47.044487f,
            47.125381f, 47.066532f, 47.104295f, 47.094226f, 47.072855f, 47.032149f, 47.06873f, 47.070815f, 47.073891f, 47.080962f, 47.054426f, 47.061674f,
            47.075087f, 47.064635f, 47.070939f, 47.076406f, 47.044583f, 47.076365f, 47.072823f, 47.073364f, 47.054417f, 47.101121f};

    Float [] latitude = {15.42118f,15.44534f,15.412398f,15.438784f,15.411977f,15.422085f,15.47099f,15.422457f,15.446348f,15.458124f,15.395426f,15.453491f,
            15.411475f,15.435787f,15.436526f,15.436911f,15.45207f,15.395841f,15.434699f,15.434386f,15.431779f,15.453553f,15.450246f,15.463884f,15.456462f,15.433708f,
            15.438135f,15.438419f,15.438617f,15.397388f,15.46014f,15.46047f,15.402819f,15.44164f,15.442456f,15.422729f,15.446924f,15.426019f,15.435743f,15.440225f,
            15.445772f,15.445371f,15.453286f,15.430721f,15.435614f,15.44205f,15.451863f,15.451177f,15.424811f,15.427498f,15.443722f,15.468962f,15.417544f,15.416739f,
            15.441898f,15.442234f,15.439245f,15.420142f,15.474925f,15.436251f,15.396315f,15.459086f,15.456367f,15.437379f,15.431217f,15.47439f,15.472214f,15.445712f,
            15.451561f,15.433647f,15.458355f,15.443198f,15.404832f,15.427923f,15.427451f,15.403278f,15.411737f};

    String [] name = {"Am Eichengrund","Andersengasse","Andritzer Reichsstraße","Angergasse","Augasse","Babenbergerstraße","Banngrabenweg","Darmstadtgasse","Dietrichsteinplatz",
            "Dürergasse","Eggenberger Allee","Felix-Dahn Platz","Fischeraustraße","Franziskanerplatz","Friedrichgasse 1","Friedrichgasse 2","Gasometerweg","Georgigasse","Grieskai 1",
            "Grieskai 2","Griesplatz","Hafnerriegel","Halbärthgasse","Harmsdorfgasse 1","Harmsdorfgasse 2","Hasnerplatz","Hauptplatz 1","Hauptplatz 2","Hauptplatz 3","Hauseggerstraße",
            "Hilmteichstraße 1","Hilmteichstraße 2","Hofbauerplatz","Hofgasse","Jakominiplatz","Josef-Huber Gasse","Kaiser-Josef-Platz","Kantgasse","Kapistran-Pieller-Platz",
            "Karmeliterplatz","Kasernstraße","Lagergasse","Lendplatz","Marburger Kai","Maria-Theresia-Allee","Max-Mell- Allee","Naglergasse","Nippelgasse","Oeverseegasse","Ortweinplatz",
            "Petrifelderstraße","Pongratz-Moore-Steg 1","Pongratz-Moore-Steg 2","Puchsteg","Radegunder Straße","Radetzkystraße","Rohrbachergasse","Roseggerweg","Sackstraße","Salfeldstraße",
            "Schillerplatz","Schillerstraße","Schlossberg","Schwimmschulkai","St. Peter Hauptstraße","St.-Peter-Pfarrweg","Stadtpark","Stremayrgasse","Südtiroler Platz","Tegetthoffplatz",
            "Theyergasse","Vinzenzgasse","Volksgartenstraße 1","Volksgartenstraße 2","Wachtelgasse","Wasserwerkgasse"};

    public Karte() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_karte, container, false);

        mMapView = rootView.findViewById(R.id.mapView);

        // mMapView.onCreate(savedInstanceState);

        //  mMapView.onResume(); // needed to get the map to display immediately

        Button liste = rootView.findViewById(R.id.liste);
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trinkbrunnen listefragment = new Trinkbrunnen();
                FragmentManager manager = getFragmentManager();
                try {
                    assert manager != null;
                    manager.beginTransaction().replace(R.id.mainLayout, listefragment, listefragment.getTag()).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Error",
                            Toast.LENGTH_LONG).show();
                }


            }
        });


        return rootView;
    }


    private void getLocationPermission() {
        String[] permissions =
                {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

        if (ContextCompat.checkSelfPermission(getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionsGranted = true;

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        MY_REQUEST_INT);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    MY_REQUEST_INT);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        getLocationPermission();

        if (permissionsGranted) {


            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {

                googleMap.setMyLocationEnabled(true);
                LatLng currentloc = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentloc, 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(currentloc)      // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }else{
                CameraPosition Graz = CameraPosition.builder().target(GRAZ).zoom(13).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Graz));

            }

        } else {

            CameraPosition Graz = CameraPosition.builder().target(GRAZ).zoom(13).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Graz));
        }


        for (int i = 1; i < 76; i++) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(longitude[i], latitude[i]))
                    .title(name [i]));
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = rootView.findViewById(R.id.mapView);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
            // TODO Auto-generated method stub

        }
    };


}

