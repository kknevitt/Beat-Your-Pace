package com.GC01.BeatYourPace.PaceCalculator;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/** 
 * @author Laura Barbosa & Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 * @param <LocationResult>
 */
public class CurrentPace extends Activity {
	
	private LocationManager locationManager;
	private LocationListener locationListener;
	private double speed = 0.00;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		List<String> locationProviders = locationManager.getAllProviders(); 
			for (String provider : locationProviders){
				Log.d("LocationProviders", provider);
			}
			
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		
		
	}
	
	public void onResume(){
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
	}
	
	public void onDestroy(){
		locationManager.removeUpdates(locationListener);
	}
	
	private class MyLocationListener implements LocationListener{


		@Override
		public void onLocationChanged(Location location) {			
		 if (location !=null){
			 
			 speed = location.getSpeed();
			 location.getAltitude();
			 
		 	}
		
		}

		@Override
		public void onProviderDisabled(String provider) {			
			Toast.makeText(getBaseContext(), "Provider: " + provider + " disabled", Toast.LENGTH_SHORT).show(); 
		}

		@Override
		public void onProviderEnabled(String provider) {			
			Toast.makeText(getBaseContext(), "Provider: " + provider + " enabled", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {			
			String statusString = "";
			switch(status) {
			case android.location.LocationProvider.AVAILABLE:
				statusString = "available";
			case android.location.LocationProvider.OUT_OF_SERVICE:
				statusString = "out of service";
			case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
				statusString = "temporarily unavailable";
			}
			
			Toast.makeText(getBaseContext(), provider + " " + statusString, Toast.LENGTH_SHORT).show();
			
			
			}
		}
		
		
	}


