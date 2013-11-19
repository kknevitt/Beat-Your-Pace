package com.GC01.BeatYourPace.Settings;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Fragment which allows the user to set their default preferences
 * 
 * 	<dt> Description:
 * 	<dd> Allows user to set for their target pace to run (e.g. 6.5 mins per unit) at and the units to use (e.g.km)
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author sarahnicholson
 *
 */

import com.example.beatyourpace.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

		public SettingsFragment() {
			// Required empty public constructor
		}
	
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
	    }

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub
			
			 /** Access the shared prefs for this app, this can only be called when an activity is created */
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
			
		     if(key.equals("set_target_pace")){
		          double newValue = sharedPref.getFloat("set_target_pace", (float) 6.0);
		          if (newValue < 4.0 | newValue > 30.0) {
		                Toast.makeText(getActivity(), "Default pace must be between 4.0 and 30.0",
		                		Toast.LENGTH_SHORT).show();
		            }
				}
		}


}
