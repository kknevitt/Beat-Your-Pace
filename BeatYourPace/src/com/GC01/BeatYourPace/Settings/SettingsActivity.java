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
 * @version $Date: 2014/01/19
 * @author Sarah Nicholson
 *
 */

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.view.Menu;
import com.example.beatyourpace.R;



public class SettingsActivity extends PreferenceActivity {
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //calls the preferences for the first time, 'false' indicates to only call if prefs not already set
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);    
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
		
		/**Google Analytics tracking code  - starts tracking user's session**/
		EasyTracker.getInstance(this).activityStart(this);
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	 @Override
	    public void onResume() {
	        super.onResume(); 
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        /**Google Analytics tracking code - stop tracking user's session**/
			EasyTracker.getInstance(this).activityStop(this);
	       
	    }

}
