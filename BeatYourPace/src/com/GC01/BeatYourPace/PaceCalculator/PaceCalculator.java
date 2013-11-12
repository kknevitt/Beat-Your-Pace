package com.GC01.BeatYourPace.PaceCalculator;

import com.example.beatyourpace.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class PaceCalculator extends Activity {
	
	// Declaring Fields.
	String pace;
	// paceCalc has to be a float as this is the variable that the getSpeed() method returns from the Location object.
	float paceCalc;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pace);
	}


	
	public PaceCalculator(){
		
		// setting default values, with No Movement to be different to 0.0 which will displayed if the LM is
		// active but is not moving or currently working.
		
		pace = "No Movement";
		paceCalc = 0;
		
		
		
	}
	
	public void setPace(){
		
		// Setting Up the Location Manager and Location Listener using Android guide on implementing Location.
		
		// Acquire a reference to the system Location Manager
		LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locLis = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	
		      // Called when a new location is found by the network location provider.
		    	
		     // makeUseOfNewLocation(location);  -- results in errors.
		      
		      //	location.getLatitude();	needed? 
		      
		      // Critical method for getting the speed
				paceCalc = (location.getSpeed());
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

	
	
	public String getPace(){
		
		pace = Float.toString(paceCalc);
	
		return pace;
		
	}
	
}
