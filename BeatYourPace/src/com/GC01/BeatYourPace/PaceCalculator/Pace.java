package com.GC01.BeatYourPace.PaceCalculator;


import com.GC01.BeatYourPace.Database.AccessSettings;
import com.GC01.BeatYourPace.Database.DataModel;
import com.example.beatyourpace.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the pace object </p>
 * <p> The Pace object uses GPS functions to determine the speed of the user, and a get method to enable the screen
 * to display it. </p>
 */
public class Pace extends Activity {
	Context context;
	
	
	/** the pace String is used in the getPace() method to be displayed on the screen.*/
	private String pace;
	
	/** paceCalc has to be a float as this is the variable that the getSpeed() method returns from 
	 * the Location object.**/
	private float paceCalc;
	
	/** constants for converting from metres per seconds, to minutes per mile, or kilometres per mile **/
	private final double MPS_TO_MINS_PER_MILE = 0.0372822715;
	private final double MPS_TO_PER_KILOMETRES = 0.06;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pace);
	}

	
	/** Constructor for Pace setting the pace and paceCalc to default values 
	 @return
	 @param
	 */
	public Pace(){
		
		// setting default values, with No Movement to be different to 0.0 which will displayed if the LM is
		// active but is not moving or currently working.
		pace = "No Movement";
		paceCalc = 0;	
	}
	
	
	
	
	
	/** Determines the speed of the user, by using the Location Manager from the android API, and specifically the 
	 * Location Strategies guide from the Android API Guides
	 * @return
	 * @param
	 */
	public void setPace(){
		
		// Setting Up the Location Manager and Location Listener using Android guide on implementing Location.
		
		// Acquire a reference to the system Location Manager
		LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Creates a listener that responds to location updates
		LocationListener locLis = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	
		     // Called when a new location is found by the network location provider, though at current the 
		    	// makeUseofNewLocation is currently not working.
		    	
		   //  makeUseOfNewLocation(location); 
    
			    	
		    	// location.get speed is the critical method for getting the speed
		    	// This is casted as a float because you are dividing a float by a double, but only need 
		    	// the accuracy of a float for our purposes.
		    	// An if else selection is used in order to be using the correct prefences for miles or kilometres
		    	// for the preferred distance unit.
		    
		    	if (AccessSettings.getUnitType() == 1)
				paceCalc = (float) (location.getSpeed() * MPS_TO_MINS_PER_MILE);
				else if (AccessSettings.getUnitType() == 2);
				paceCalc = (float) (location.getSpeed() * MPS_TO_PER_KILOMETRES);
				
				

		    }
		    
		    // methods which have to be inherited, though are empty for our purposes.
		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };
		  

		// Register the listener with the Location Manager to receive location updates - 5 second interval between updates
		locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locLis);
		// A second request is sent - using GPS instead of network provider, which should increase accuracy.
		locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locLis);
		
		
		
				
	}

	
	/** Used to receive the pace of the user, returning a String as this is what has to be used to be displayed
	 * on the Android screen
	 * @return pace (String)  The current running speed of the user
	 * @param 
	 */
	public String getPace(){
		
		pace = Float.toString(paceCalc);
	
		return pace;
		
	}
	
}
