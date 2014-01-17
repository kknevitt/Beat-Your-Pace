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
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The CurrentPace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 */
public class CurrentPace extends Service {
	Context context;
	LocationManager locationManager;
	Location location;
	
	/** the currentPace double is used to retrieve the speed value to be displayed on the UI*/
	private static float currentPace;
	
	/** constants for converting from metres per seconds, to minutes per mile, or kilometres per mile **/
	private final double MPS_TO_MINS_PER_MILE = 0.0372822715;
	private final double MPS_TO_PER_KILOMETRES = 0.06;
	
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
	
		Log.d(null, "testing - startService() for GPS is being called");
		
		LocationListener locationListener = new LocationListener(){		        
			public void onLocationChanged(Location location){
				
				// Called when a new location is found by the network location provider.
				 if (location !=null) {    		
			    		currentPace = location.getSpeed();
			    		addSpeedUnit(currentPace);
			    		
			    		//temporary toast message to verify if the GPS is working
			    		Toast.makeText(getApplicationContext(), "The GPS is working",
			 				   Toast.LENGTH_LONG).show();
			    		
			    	 } else if(location == null){
						 currentPace = 0;
							Toast.makeText(getApplicationContext(), "Your GPS is not responding, it may be off.",
									   Toast.LENGTH_LONG).show();
					 }
				 }
			/**Unimplemented methods**/
	        public void onStatusChanged(String provider, int status, Bundle extras) {}
	        public void onProviderEnabled(String provider) {}
	        public void onProviderDisabled(String provider) {}
	    };
	    
	    /**Setting the location provider and the updates interval time **/
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
	} 

	/** Method created to adjust the currentPace retrieve to the preferred speed unit of the user.
	 * The getSpeed() method retrieves speed at metres per second.*/
	private void addSpeedUnit(float currentPace){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
		
		if (unitType == 1){
			
			currentPace = (float) (currentPace / MPS_TO_MINS_PER_MILE);
			sendGPSInfo();
			Toast.makeText(ContextProvider.getContext(), "GPS Data was sent with Miles", Toast.LENGTH_SHORT).show();
			  
			}if (unitType == 2) {
				currentPace = (float) (currentPace / MPS_TO_PER_KILOMETRES);
				sendGPSInfo();
				Toast.makeText(ContextProvider.getContext(), "GPS Data with KM", Toast.LENGTH_SHORT).show();
				  
			}
			
	}	
			    				
			
		
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**Get the GPS current pace value**/
	private float getGPSInfo(){
		return currentPace;
	}
	
	/**Sets the broadcast message for the GPS current pace**/
	private void sendGPSInfo() {
		
		//temporary toast message to test that the GPS data is being sent to the training mode class
		Toast.makeText(getApplicationContext(), "send GPSInfo intent is being called.",
				   Toast.LENGTH_LONG).show();
		
		  Intent intent = new Intent("Current Pace Event");
		  
		  intent.putExtra("GPS Current Pace Info", getGPSInfo());
		  LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
		  System.out.println("GPS Sent");
	}
	
	
    public void onDestroy()
    {
      super.onDestroy();
      stopService(null);
    }
	
}