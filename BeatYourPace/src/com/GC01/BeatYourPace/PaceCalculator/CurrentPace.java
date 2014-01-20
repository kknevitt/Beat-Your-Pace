package com.GC01.BeatYourPace.PaceCalculator;



import com.GC01.BeatYourPace.Main.ContextProvider;
import com.example.beatyourpace.R;

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
import android.widget.TextView;
import android.widget.Toast;

/** 
 * @author Laura Barbosa & Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 */
public class CurrentPace extends Service implements LocationListener { 

static double speedDouble = 0;
static String speedString = "";
LocationManager locationManager;
LocationListener locationListener;
Intent intent;
private final double MPS_TO_MINS_PER_MILE = 0.0372822715;
private final double MPS_TO_PER_KILOMETRES = 0.06;
String point = ".";
String index = "";


SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());

public void onCreate() { 
   
    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    
     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
    
    }

    public void onLocationChanged(Location location) {
        speedDouble = location.getSpeed();

        if (speedDouble == 0){
        	speedString = "0.00";
        }
        
        
        if (Integer.parseInt(sp.getString("unitType", "1")) == 1) {
        	speedDouble = speedDouble / MPS_TO_MINS_PER_MILE; 
        } else {
				speedDouble = speedDouble / MPS_TO_PER_KILOMETRES;
        }
        
        
        index = Integer.toString(speedString.indexOf(point));
        speedString = Double.toString(speedDouble);
        
        //index.compareTo(index); compare index to 2 or 1
        
        
        if (index.equals("2")){
        		speedString = speedString.substring(0, 6);
        		Log.i("index 2 is called","");
    		} if (index.equals("1")) {
    			speedString = speedString.substring(0, 3);
    				Log.i("index 1 is called","");
    			} else {
    				speedString = "0.0";
    			}
        
        
    	
        
        
        Toast.makeText(getBaseContext(), "current speed string " + speedString + "and speed double is" + speedDouble, Toast.LENGTH_SHORT).show();
        Log.i("current speed ", speedString);
        sendGPSInfo(speedString);
        
    }

	public void onProviderDisabled(String provider) {
		Toast.makeText(getBaseContext(), "Provider: " + provider + " disabled", Toast.LENGTH_SHORT).show(); 
		
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(getBaseContext(), "Provider: " + provider + " enabled", Toast.LENGTH_SHORT).show();
		
	}

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
	
	public void onDestroy(){
		super.onDestroy();;

	}
	

	
	
	public void sendGPSInfo(String string) {        
           Intent intent = new Intent("Track Info Event");
           intent.putExtra("GPS", speedString);
           LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
   }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
		
}