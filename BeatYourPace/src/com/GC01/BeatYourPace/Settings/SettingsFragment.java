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
 * @version $Date: 2014/01/19
 * @author sarahnicholson
 *
 */


import com.example.beatyourpace.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment {
	
	public SettingsFragment() {
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);	
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
