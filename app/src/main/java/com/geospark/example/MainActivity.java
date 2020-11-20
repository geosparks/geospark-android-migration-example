package com.geospark.example;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.geospark.example.service.GeoSparkForegroundService;
import com.geospark.lib.GeoSpark;
import com.geospark.lib.GeoSparkTrackingMode;
import com.geospark.lib.callback.GeoSparkCallback;
import com.geospark.lib.models.GeoSparkError;
import com.geospark.lib.models.GeoSparkUser;

import dev.motion.development.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO STEP 9:  DisableBatteryOptimization
        GeoSpark.disableBatteryOptimization();

        //TODO STEP 4:  Create user or GetUser
        findViewById(R.id.txtCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoSpark.createUser("GeoSpark Example", new GeoSparkCallback() {
                    @Override
                    public void onSuccess(GeoSparkUser geoSparkUser) {
                        Log.e("USER", geoSparkUser.getUserId());
                    }

                    @Override
                    public void onFailure(GeoSparkError geoSparkError) {

                    }
                });
            }
        });

        //TODO STEP 5:  Start tracking
        findViewById(R.id.txtStartTracking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking();
            }
        });

        //TODO STEP 10:  Stop tracking
        findViewById(R.id.txtStopTracking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, GeoSparkForegroundService.class));
                GeoSpark.stopTracking();
            }
        });
    }

    private void startTracking() {
        if (!GeoSpark.checkLocationServices()) {
            GeoSpark.requestLocationServices(this);
        } else if (!GeoSpark.checkLocationPermission()) {
            GeoSpark.requestLocationPermission(this);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !GeoSpark.checkBackgroundLocationPermission()) {
                GeoSpark.requestBackgroundLocationPermission(this);
            } else {
                //TODO STEP 6: Call start foreground service and choose any one of the tracking mode
                startService(new Intent(MainActivity.this, GeoSparkForegroundService.class));
               /* //Active
                GeoSpark.startTracking(GeoSparkTrackingMode.ACTIVE);
                //Reactive
                GeoSpark.startTracking(GeoSparkTrackingMode.REACTIVE);
                //Passive
                GeoSpark.startTracking(GeoSparkTrackingMode.PASSIVE);
                //Time based location tracking
                GeoSparkTrackingMode timeBasedTracking = new GeoSparkTrackingMode.Builder(30)
                        .setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH)
                        .build();
                GeoSpark.startTracking(timeBasedTracking);
                //Distance based location tracking
                GeoSparkTrackingMode distanceBasedTracking = new GeoSparkTrackingMode.Builder(100, 60)
                        .setDesiredAccuracy(GeoSparkTrackingMode.DesiredAccuracy.HIGH)
                        .build();
                GeoSpark.startTracking(distanceBasedTracking);*/

                //Reference link: https://docs.geospark.co/android/quickstart#Quickstart(Android)-ConfigureTrackingModes

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GeoSpark.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION:
            case GeoSpark.REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTracking();
                } else {
                    Log.e("GeoSpark Example", "Location permission required.");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GeoSpark.REQUEST_CODE_LOCATION_ENABLED) {
            startTracking();
        }
    }
}
