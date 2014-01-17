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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	private OnSharedPreferenceChangeListener listener;
	
	public SettingsFragment() {
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
		//sp.registerOnSharedPreferenceChangeListener(listener);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
	
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		/*
		if ("set_target_pace".equals(key)) {

	    		String value = sharedPreferences.getString(key, "6.0");
	    		double newTargetPace = Double.parseDouble(key);

	    		//need to add a second test to make sure it end 0 or 0.5 - use the string value and check the last 2 chars?

	    		if(newTargetPace < 4.0 || newTargetPace > 30.0) {
	    				Toast toast = Toast.makeText(getActivity(),"Default pace must be between 4.0 and 30.0", Toast.LENGTH_SHORT);
	    				toast.show();
	    			}
	    			else if (value.equals("")){
	    				Toast toast = Toast.makeText(getActivity(), "Default pace cannot be blank", Toast.LENGTH_SHORT);
	    				toast.show();
	    			}
	    			else {

	    			}
	    	}
	    	*/
	}
}
