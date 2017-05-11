package com.aplana.ivan.learnview;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by 1 on 11.05.2017.
 */

public class MyLocation extends Activity {
    public String latitude;
    public String longitude;
    public String time;
    public int type;
    Context mContext;
    LocationManager locationManager;

    public MyLocation(Context mContext) {
        this.mContext = mContext;
    }

    public MyLocation() {

    }

    public Location getLocation()
    {
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)mContext.getSystemService(context);

        String provider = LocationManager.GPS_PROVIDER;
        try {
            Location location = locationManager.getLastKnownLocation(provider);
            return location;
        }
        catch (SecurityException ex)
        {
            ex.printStackTrace();
            return null;
        }

    }
    public String updateWithNewLocation(Location location) {
        String latLongString;

        if (location != null){
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        }else{
            latLongString = "No Location";
        }

        return latLongString;
    }

  /*    @Override

  protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);

    }*/
}
