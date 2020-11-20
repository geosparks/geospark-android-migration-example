package com.geospark.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.geospark.example.NotificationHelper;

//TODO STEP 7:  Add foreground service for continuous tracking, add uses-permission and service in AndroidManifest.xml
/*
<manifest>
......

<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />


<application>
......

<service
android:name="com.geospark.example.service.GeoSparkForegroundService"
android:enabled="true"
android:foregroundServiceType="location" />

</application>
</manifest>
 */
public class GeoSparkForegroundService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NotificationHelper.NOTIFICATION_ID, NotificationHelper.showNotification(this));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationHelper.cancelNotification(this);
    }
}