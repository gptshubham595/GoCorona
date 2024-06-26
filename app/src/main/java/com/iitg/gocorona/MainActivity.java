package com.iitg.gocorona;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.iitg.gocorona.aware.Awareness;
import com.iitg.gocorona.food.FoodActivity;
import com.iitg.gocorona.patient.PatientActivity;
import com.iitg.gocorona.query.reportQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String ROOT_URL = "https://thevirustracker.com/free-api?global=stats";
    String total_cases, total_deaths;
    Button food, patient, symptoms, medicalChatbot, live, query, aware, edu;

    private FirebaseAuth mAuth;
    private DatabaseReference storeuserdata;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    private long UPDATE_INTERVAL = 300 * 1000;  /* 60 secs */ //5MIN
    private long FASTEST_INTERVAL = 300 * 1000; /* 5 min */

    private final static String KEY_LOCATION = "location";

    Button live_cases_btn, live_deaths_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        live_cases_btn = findViewById(R.id.live_cases);
        live_deaths_btn = findViewById(R.id.live_deaths);

        food = findViewById(R.id.food);
        symptoms = findViewById(R.id.symptom);
        patient = findViewById(R.id.patient);
        live = findViewById(R.id.live);
        aware = findViewById(R.id.awareness);
        query = findViewById(R.id.query);
        livedataCorona();
        live_cases_btn.setText("Total Live Cases\n" + total_cases);
        live_deaths_btn.setText("Total Live Cases\n" + total_deaths);

        live_cases_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livedataCorona();
                live_cases_btn.setText("Total Live Cases\n" + total_cases);
                live_deaths_btn.setText("Total Live Cases\n" + total_deaths);
            }
        });
        live_deaths_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livedataCorona();
                live_cases_btn.setText("Total Live Cases\n" + total_cases);
                live_deaths_btn.setText("Total Live Cases\n" + total_deaths);
            }
        });


        medicalChatbot = findViewById(R.id.medicalChatbot);

        try {
            livedataCorona();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    livedataCorona();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(i);
            }
        });

        medicalChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.iitg.gocorona.medicalchatbot.MainActivity.class);
                startActivity(i);
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.iitg.gocorona.covid.MainActivity.class);
                startActivity(i);
            }
        });

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Browser = new Intent(getApplicationContext(), Url.class);
                Browser.putExtra("heading", "COVID-19");
                Browser.putExtra("url", "https://www.covid19india.org");
                startActivity(Browser);
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PatientActivity.class);
                startActivity(i);
            }
        });

        aware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Awareness.class);
                startActivity(i);
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, reportQuery.class));

            }
        });

        if (TextUtils.isEmpty(getResources().getString(R.string.google_maps_key))) {
            throw new IllegalStateException("You forgot to supply a Google Maps API key");
        }

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }

    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
            MainActivityPermissionsDispatcher.getMyLocationWithPermissionCheck(this);
            MainActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
        } else {
            Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @SuppressWarnings({"MissingPermission"})
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MainActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Display the connection status

        if (mCurrentLocation != null) {
            try {
                Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
                LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                map.animateCamera(cameraUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
        }
        MainActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // GPS may be turned off
        if (location == null) {
            return;
        }

        // Report to the UI that the location was updated

        mCurrentLocation = location;
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        String device_token = FirebaseInstanceId.getInstance().getToken();
        Date date = new Date();
        long timemill = date.getTime();
        try {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("travel").child(String.valueOf(timemill));
            mDatabase.setValue(Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    private void livedataCorona() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("JSON", jsonObject + "");
                            Log.d("JSON", jsonObject.getString("results") + "");
                            Log.d("JSON", jsonObject.getString("results").split(",")[0].substring(16));
                            Log.d("JSON", jsonObject.getString("results").split(",")[3].substring(15));


                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), "" + jsonObject.getString("results"), Toast.LENGTH_LONG).show();
                                Map address = ((Map) jsonObject.get("results"));


                                // iterating address Map
                                total_cases = jsonObject.getString("results").split(",")[0].substring(16);
                                total_deaths = jsonObject.getString("results").split(",")[3].substring(15);

                                live_cases_btn.setText("Total Live Cases\n" + jsonObject.getString("results").split(",")[0].substring(16));
                                live_deaths_btn.setText("Total Live Cases\n" + jsonObject.getString("results").split(",")[3].substring(15));

                            } else {
                                Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
