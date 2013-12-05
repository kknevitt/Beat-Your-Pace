package com.GC01.BeatYourPace.PaceCalculator;


import com.GC01.BeatYourPace.Database.DataModel;
import com.GC01.BeatYourPace.Settings.AccessSettings;
import com.example.beatyourpace.R;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
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
	public static float currentPace;
	
	/** the pace String is used in the getPace() method to be displayed on the screen.*/
	private static String pace;
	
	
	/** constants for converting from metres per seconds, to minutes per mile, or kilometres per mile **/
	private final double MPS_TO_MINS_PER_MILE = 0.0372822715;
	private final double MPS_TO_PER_KILOMETRES = 0.06;
	
	
	/** Constructor for CurrentPace setting the pace and paceCalc to default values 
	 @return
	 @param
	 */

	@Override
    public void onCreate()
    {
      super.onCreate();  
   // Acquire a reference to the system Location Manager
      locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
      startService(context);
    }

				
	public void startService(Context context){
		 LocationListener locationListener = new LocationListener(){
		        
		
			public void onLocationChanged(Location location){
		       // Called when a new location is found by the network location provider.
				 if (location.hasAccuracy() && location !=null) {    	
			    	// An if else selection is used in order to be using the correct prefences for miles or kilometres
				    // for the preferred distance unit.	
				   if (AccessSettings.getUnitType() == 1) {
			    		currentPace = (float) (location.getSpeed() * MPS_TO_MINS_PER_MILE);
			    		location.setSpeed(currentPace);
			    			if (AccessSettings.getUnitType() == 2) {
			    				currentPace = (float) (location.getSpeed() * MPS_TO_PER_KILOMETRES);
			    				location.setSpeed(currentPace);
			    				}
			    			}  
				 }
				 else if( location == null ) {
						 currentPace = 0;
							Toast.makeText(getApplicationContext(), "Your GPS is not responding, it may be off.",
									   Toast.LENGTH_LONG).show();	
				   } 				   	
		      }
			 
			 //Unimplemented methods
		        public void onStatusChanged(String provider, int status, Bundle extras) {}
		        public void onProviderEnabled(String provider) {}
		        public void onProviderDisabled(String provider) {}
		    };
		    
		    //Setting location updates
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
		} 
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//gets called when app is no longer used
	private void stopService()
    {
     //stop class when user stops using the app
    }
	
	//kills thread when app is no longer used
    public void onDestroy()
    {
      super.onDestroy();
      stopService(null);
    }
	
}
