package com.swc.onestop.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.kml.KmlLayer;
import com.swc.onestop.Models.SampleModel;
import com.swc.onestop.R;
import com.swc.onestop.directionhelpers.FetchURL;
import com.swc.onestop.directionhelpers.TaskLoadedCallback;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,TaskLoadedCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior sheetBehavior;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    SupportMapFragment mapFragment;
    Marker prevMarker;
    MarkerOptions place,mylocation;
    LatLng latLng;
    TextView Place_name;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,Main2Activity.class));
            }
        });


        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);


        Place_name = (TextView) findViewById(R.id.place_name); 

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(MapsActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleModel>() {
                            @Override
                            public void onSelected(
                                    BaseSearchDialogCompat dialog,
                                    SampleModel item, int position
                            ) {
                                switch (item.getTitle()) {
                                    case "IITG Main Gate" :
                                        place = new MarkerOptions().position(new LatLng(26.196499, 91.686506)).title("IITG Main Gate");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Food Court" :
                                        place = new MarkerOptions().position(new LatLng(26.192541, 91.699330)).title("Food Court");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Guest House" :
                                        place = new MarkerOptions().position(new LatLng(26.195710, 91.695624)).title("Guest House");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Khoka Market" :
                                        place = new MarkerOptions().position(new LatLng(26.185640, 91.701119)).title("Khoka Market");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Lecture Hall" :
                                        place = new MarkerOptions().position(new LatLng(26.189096, 91.691463)).title("Lecture Hall");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 1" :
                                        place = new MarkerOptions().position(new LatLng(26.187741, 91.691554)).title("Core 1");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 2" :
                                        place = new MarkerOptions().position(new LatLng(26.186639, 91.691438)).title("Core 2");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 3" :
                                        place = new MarkerOptions().position(new LatLng(26.185691, 91.691667)).title("Core 3");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;
                                    case "Core 4" :
                                        place = new MarkerOptions().position(new LatLng(26.184709, 91.691506)).title("Core 4");
                                        Place_name.setText(item.getTitle());
                                        mMap.clear();
                                        mMap.addMarker(place);
                                        new FetchURL(MapsActivity.this).execute(getUrl(mylocation.getPosition(), place.getPosition(), "driving"), "driving");
                                        break;



                                }
                                dialog.dismiss();
                            }
                        }
                );
                dialog.show();
                dialog.getSearchBox().setTypeface(Typeface.SERIF);
            }
        });


        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        sheetBehavior.setHideable(false);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        sheetBehavior.setHideable(false);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });



    }


    


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    private ArrayList<SampleModel> createSampleData() {
        ArrayList<SampleModel> items = new ArrayList<>();
        items.add(new SampleModel("IITG Main Gate"));
        items.add(new SampleModel("Food Court"));
        items.add(new SampleModel("Guest House"));
        items.add(new SampleModel("Khoka Market"));
        items.add(new SampleModel("Lecture Hall"));
        items.add(new SampleModel("Core 1"));
        items.add(new SampleModel("Core 2"));
        items.add(new SampleModel("Core 3"));
        items.add(new SampleModel("Core 4"));
        return items;
    }



    private class DownloadKmlFileDayOne extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFileDayOne(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {


            try {
                InputStream is =  new URL(mUrl).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(byte[] byteArr) {
            try {

                KmlLayer kmlLayerD1 = new KmlLayer(mMap, new ByteArrayInputStream(byteArr), MapsActivity.this.getApplicationContext());


                // kmlLayerD1.addLayerToMap();


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }





//        // Setting a click event handler for the map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                // Clears the previously touched position
//                Place_name.setText(item.getTitle());
                                        mMap.clear();
//
//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//            }
//        });




//get latlong for corners for specified place
        LatLng one = new LatLng(26.201932, 91.684568);
        LatLng two = new LatLng(26.183738, 91.705171);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);


    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude

            latLng = new LatLng(location.getLatitude(), location.getLongitude());

            mylocation = new MarkerOptions().position(latLng).title("Place");
            mMap.addMarker(mylocation);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        mylocation = new MarkerOptions().position(latLng).title("Place");
        mMap.addMarker(mylocation);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();

            Geocoder geocoder = new Geocoder(MapsActivity.this.getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }


    }

}
