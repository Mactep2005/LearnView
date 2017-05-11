package com.aplana.ivan.learnview;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by 1 on 11.05.2017.
 */

public class Location {
    public String latitude;
    public String longitude;
    public String time;

    public Location(String[] getLocation) {

    }

    public String[] getLocation() {
      String lat="",lon="";
        private LocationManager locationManager;
        StringBuilder sbGPS = new StringBuilder();
        StringBuilder sbNet = new StringBuilder();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
}
