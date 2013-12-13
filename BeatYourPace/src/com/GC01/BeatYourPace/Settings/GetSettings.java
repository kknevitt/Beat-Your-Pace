package com.GC01.BeatYourPace.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.GC01.BeatYourPace.Main.ContextProvider;

public class GetSettings {

		/**
		 * Check the default unit stored in the shared preferences file
		 * @return int unitType  This returns 1 for miles and 2 for kilometres
		 */
		public int getUnitType() {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
			int unitType = Integer.parseInt(preferences.getString("unitType", "1"));
			return unitType;
		}
	
		/**
		 * Check the default target pace stored in the shared preferences file
		 * @return float defaultTargetPace  This returns the minutes to achieve one unit 
		 *         e.g. 6.0 mins per mile if defaultTargetPace is 6.0 and unitType is 1
		 */
		public float getDefaultTargetPace() {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ContextProvider.getContext());
			float defaultTargetPace = Float.parseFloat(preferences.getString("set_target_pace", "9.0"));
			return defaultTargetPace;
		}
}
