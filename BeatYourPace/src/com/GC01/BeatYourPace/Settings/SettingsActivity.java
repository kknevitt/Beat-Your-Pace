package com.GC01.BeatYourPace.Settings;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Calls the settings fragment when the user selects the settings menu.
 * 
 * 	<dt> Description:
 * 	<dd> Provides default values for the pace to run (6.0 mins per unit) at and the units to use (m)
 * 	<dd> Calls the settings fragment which allow the user to change these
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author snichols
 *
 */

import com.example.beatyourpace.R;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;


public class SettingsActivity extends PreferenceActivity {

    Context context = getApplicationContext(); 
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); 
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //calls the preferences for the first time, 'false' indicates to only call if prefs not already set
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);    
		// Displays the settings fragment as the main content
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	 @Override
	    public void onResume() {
	        super.onResume();
	        
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	       
	    }

	    
	   // @Override
	   // public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	    /*
	    	SharedPreferences.Editor prefEditor = prefs.edit();
	    	if ("set_target_pace".equals(key)) {
	    		Boolean rtnval = true;
	    		String value = sharedPreferences.getString(key, "6.0");
	    		double newTargetPace = Double.parseDouble(key);
	    		
	    		//need to add a second test to make sure it end 0 or 0.5 - use the string value and check the last 2 chars?
	    		
	    		if(newTargetPace < 4.0 || newTargetPace > 30.0) {
	    				Toast.makeText(context, "Default pace must be between 4.0 and 30.0", Toast.LENGTH_SHORT).show();
	    				prefEditor.commit();
	    			}
	    			else if (value.equals("")){
	    				Toast.makeText(context, "Default pace cannot be blank", Toast.LENGTH_SHORT).show();
	    				prefEditor.commit();
	    			}
	    			else {
	    				prefEditor.commit();
	    			}
	    	}
	    	*/


	    //}
}
