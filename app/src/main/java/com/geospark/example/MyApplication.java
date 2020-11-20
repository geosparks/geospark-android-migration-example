package com.geospark.example;

import android.app.Application;

import com.geospark.lib.GeoSpark;
import com.geospark.lib.GeoSparkTrackingMode;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO STEP 2: Initialize SDK
        GeoSpark.initialize(this, "YOUR-PUBLISHABLE-KEY");
        //TODO STEP 3: After updating SDK from 2.2.5 to 3.0.4
        GeoSpark.setTrackingInAppState(GeoSparkTrackingMode.AppState.ALWAYS_ON);
        GeoSpark.locationPublisher(true);
        GeoSpark.offlineLocationTracking(true);
    }
}
