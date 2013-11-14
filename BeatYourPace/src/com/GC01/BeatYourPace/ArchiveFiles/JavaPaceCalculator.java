package com.GC01.BeatYourPace.ArchiveFiles;

public class JavaPaceCalculator {
	
	// setting  fields
	String pace;
	float paceCalc;
	
public JavaPaceCalculator(){
		
	// setting default values, with No Movement to be different to 0.0 which will displayed if the LM is
	// active but is not moving or currently working.
	
	pace = "No Movement";
	paceCalc = 0;

}

	public void setPace(){
		
		// Use the Location class from Android API
		// See Pace.java for attempted implementation
		// Location Manager and Location Listener needed.
		
		
		// paceCalc = calculate using location.getSpeed() method.
		
	}
	
	public String getPace(){
		
		// converting to string so it can be passed to a text view.
		pace = Float.toString(paceCalc);
		return pace;
	}
}
	

/*
 * 
 * Permissions to add
 * 
android:name="android.permission.INTERNET" 
android:name="android.permission.ACCESS_NETWORK_STATE"
android:name="android.permission.READ_PHONE_STATE"
android:name="android.permission.ACCESS_FINE_LOCATION" (coarse location isn't needed as it is covered by including fine - Android developer guide)
*/

