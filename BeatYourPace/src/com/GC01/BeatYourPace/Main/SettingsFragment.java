package com.GC01.BeatYourPace.Main;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
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
	   
	        findPreference("set_target_pace").setOnPreferenceChangeListener(
	                new Preference.OnPreferenceChangeListener() {

						@Override
						public boolean onPreferenceChange(Preference preference, Object newValue) {
							//add code to check if the value is between 5 and 15 and only increments in 0.5
							//enclose reading preference is try/catch NumberFormatException. If exception, I return default value
							
							if (newValue == null) {
				                Toast.makeText(getActivity(), "Default pace cannot be blank",
				                		Toast.LENGTH_SHORT).show();
				                return false;
				            }
				            return true;
						}
	            });
	    }

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub
			
		}


}
