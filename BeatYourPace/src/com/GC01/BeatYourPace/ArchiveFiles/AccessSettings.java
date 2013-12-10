package com.GC01.BeatYourPace.ArchiveFiles;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> This class is a placeholder pending a solution to retrieving the users preferences
 * 
 * 	<dt> Description:
 * 	<dd> This class does not really belong in the database class. It is here only as a temporary
 * 	<dd> workaround to allow the other classes to call the shared preferences.
 * </dl>
 * 
 * @version $Date: 2013/11/14
 * @author snichols
 *
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class AccessSettings extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		/** Access the shared prefs for this app, this can only be called when an activity is created */
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		/** Field which calls the defaultTargetPace from settings */
		double defaultTargetPace = sharedPref.getFloat("set_target_pace", (float) 6.0);
	
		/** Field which calls the defaultUnitType from settings */
		int unitType = sharedPref.getInt("unitType", 1);
	}

	/**
	 * Method to return the default preferred pace from settings
	 * Investigation needed to see if there is a way to return the the default target pace for other classes to access
	 * @return defaultTargetPace   Double with the default preferred pace
	 */
	public static double getDefaultTargetPace(){
		double defaultTargetPace = 6.0;
		return defaultTargetPace;
	}
	
	/**
	 *  Method to return the unit (miles or kilometres) from settings
	 * Investigation needed to see if there is a way to return the unitType for other classes to access
	 * @return unitType   The unit (integer 1 = miles or 2 = kilometres) from settings
	 */
	public static int getUnitType(){
		int unitType = 1;
		return unitType;
	}
}
