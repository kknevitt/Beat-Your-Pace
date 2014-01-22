package com.GC01.BeatYourPace.PaceCalculator;

import com.GC01.BeatYourPace.Main.ContextProvider;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/** 
 * @author Laura Barbosa & Kristian Knevitt
 * @version 1.0, Updated 22/01/2014
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS onLocationChanged() and getSpeed() method to determine the speed of the user, and a uses method to 
 * <p> broadcast the String value to the Training Mode class.
 * 
 * </p>
 */


public class CurrentPace extends Service { 
	
public LocationManager locationManager;
public LocationListener locationListener;
private final double MPS_TO_MINS_PER_MILE = 0.0372822715;
private final double MPS_TO_PER_KILOMETRES = 0.06;
static private String index = "";
static private String one = "1";
static private String point = ".";
static private String speedString = "";
static private double speedDouble = 0;
static private String two = "2";


SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

		public void onCreate() { 
		super.onCreate();
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);    
		locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
  }
  
  class MyLocationListener implements LocationListener{
	public void onLocationChanged(Location location) {
        speedDouble = location.getSpeed();

        if (Integer.parseInt(sp.getString("unitType", "1")) == 1) {
            speedDouble = speedDouble / MPS_TO_MINS_PER_MILE;}
		else {speedDouble = speedDouble / MPS_TO_PER_KILOMETRES;}
        
        speedString = Double.toString(speedDouble);
        
        Log.i("Speed string is " + speedString, "weird");
        
        sendGPSInfo(speedString);
                       
    }
    
        public void onProviderDisabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS disabled", Toast.LENGTH_SHORT).show(); }

        public void onProviderEnabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS enabled", Toast.LENGTH_SHORT).show(); }      
   
        public void onStatusChanged(String provider, int status, Bundle extras) {
                String statusString = "";
                switch(status) {
                case android.location.LocationProvider.AVAILABLE:
                        statusString = "available";
                case android.location.LocationProvider.OUT_OF_SERVICE:
                        statusString = "out of service";
                case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
                        statusString = "temporarily unavailable";
                        
                Toast.makeText(getBaseContext(), provider + " " + statusString, Toast.LENGTH_SHORT).show();
                }
        	}
       }
    	
  /** 
   * <p> Method retrieves the String value of the user speed in km/min or m/min, formats the String and sends a broadcast to the Training mode so the text can
   * be displayed.
   */		
      private void sendGPSInfo(String string) {  
  	            index = Integer.toString(speedString.indexOf(point));
  	                 
  	            if (index.equals(one)) {
  	                speedString = speedString.substring(0, 3);
  	                } else if (index.equals(two)){
  	                  speedString = speedString.substring(0, 4);
  	                } else { speedString = "0.0"; }
        	
           Intent intent = new Intent("Track Info Event");
           intent.putExtra("GPS", speedString);
           LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
        }
        

        
       public void onDestroy(){

        	if (locationManager != null){
        		locationManager.removeUpdates(locationListener);
        	} else locationManager.removeUpdates(locationListener);
   
        	stopSelf();
        	super.onDestroy();
        	
        }
     

        @Override
        public IBinder onBind(Intent arg0) {
                // TODO Auto-generated method stub
                return null;
        }
                
}