package com.GC01.BeatYourPace.PaceCalculator;

import java.util.List;

import com.GC01.BeatYourPace.Main.ContextProvider;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.*;
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
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 * @param <LocationResult>
 */
public class CurrentPace extends Service {
	private static Context context;
	private static LocationManager locationManager;
	private static float currentPace;
    boolean gps_enabled=false;
    boolean network_enabled=false;
	private LocationListener locationListenerGps;

	
	@Override
	public void onCreate() {
      super.onCreate();  
      // Acquire a reference to the system Location Manager
      locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
      startService(context);

   
      
	}

	/**Stating the onLocationChange method in order to retrieve the associated getSpeed() 
	 * provided by the Android API. The method takes into account the user speed unit preferences (defined on the Settings classes: 
	 * miles per minute or kilometres per hour. **/
	
	private void startService(Context context){

	        if(locationManager==null)
	        	locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	        try{
	        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	        }catch(Exception ex){}
	        try{
	        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	        }catch(Exception ex){}
		
	        if(gps_enabled)
	            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
	        if(network_enabled)
	        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
	    	
			List<String> locationProviders = locationManager.getAllProviders();
			Criteria c = new Criteria();
			c.setAccuracy(Criteria.ACCURACY_FINE);
			c.setAltitudeRequired(false);
			c.setBearingRequired(false);
			c.setCostAllowed(true);
			c.setPowerRequirement(Criteria.POWER_HIGH);
			
			String bestProvider = locationManager.getBestProvider(c, true);
	
	        
		Log.d(null, "testing - startService() for GPS is being called");
		
		
		  LocationListener locationListenerGps = new LocationListener() {
		        public void onLocationChanged(Location location) {
		        	currentPace = location.getSpeed();
		        	sendGPSInfo();
		        	locationManager.removeUpdates(this);

		        	
		        	
		    		//temporary toast message to verify if the GPS is working
		    		Toast.makeText(getApplicationContext(), "The GPS is working",
		 				   Toast.LENGTH_LONG).show();
		
		        }
		        
		        public void onProviderDisabled(String provider) {}
		        public void onProviderEnabled(String provider) {}
		        public void onStatusChanged(String provider, int status, Bundle extras) {}
		    };
		
		    LocationListener locationListenerNetwork = new LocationListener() {
		        public void onLocationChanged(Location location) {
		        	currentPace = location.getSpeed();
		        	sendGPSInfo();
		        	locationManager.removeUpdates(this);
		        	
		    		//temporary toast message to verify if the Network is working
		    		Toast.makeText(getApplicationContext(), "The network is working",
		 				   Toast.LENGTH_LONG).show();

		        }
		        public void onProviderDisabled(String provider) {}
		        public void onProviderEnabled(String provider) {}
		        public void onStatusChanged(String provider, int status, Bundle extras) {}
		    };
		
			
				 }
			
	        public void onStatusChanged(String provider, int status, Bundle extras) {}
	        public void onProviderEnabled(String provider) {}
	        public void onProviderDisabled(String provider) {}
	    
	    
	    
	

			    						
	private float getGPSInfo(){
		return currentPace;
	}
	

	private void sendGPSInfo() {
		
		//temporary toast message to test that the GPS data is being sent to the training mode class
		Toast.makeText(getApplicationContext(), "send GPSInfo intent is being called.",
				   Toast.LENGTH_LONG).show();
		
		  Intent intent = new Intent("Current Pace Event");
		  
		  intent.putExtra("GPS Current Pace Info", getGPSInfo());
		  LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
		  Toast.makeText(getApplicationContext(),"GPS Sent",
				   Toast.LENGTH_LONG).show();

	}
	
	
    public void onDestroy()
    {
      super.onDestroy();
      stopService(null);
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}