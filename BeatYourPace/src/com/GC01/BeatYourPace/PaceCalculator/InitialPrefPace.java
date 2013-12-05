package com.GC01.BeatYourPace.PaceCalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class has methods to calculate and add the preferred pace meta data for a track
 * @author sarahnicholson
 *
 */

public class InitialPrefPace {
	
	public InitialPrefPace() {
	}
	
	/**
	 * This method is used to add the value for the initial pace based on bpm
	 * It calculates pace in miles
	 * @param bpm  Integer that is the auto-calculated BPM for a track
	 * @return initialPrefPace	 Float that is the initial preferred pace that this track should be used for
	 */
	public float calcInitPrefPace(int bpm){
		float initialPrefPace;
			if (bpm < 150) {
				initialPrefPace = (float) 10.0;
			} else if (bpm > 150 && bpm < 153) {
				initialPrefPace = (float) 9.0;
			} else if (bpm > 153 && bpm < 156) {
				initialPrefPace = (float) 8.0;
			} else if (bpm > 156 && bpm < 160) {
				initialPrefPace = (float) 7.0;
			} else if (bpm > 160 && bpm < 163) {
				initialPrefPace = (float) 6.0;
			} else if (bpm > 163 && bpm < 166) {
				initialPrefPace = (float) 6.0;
			} else if (bpm > 166 && bpm < 171) {
				initialPrefPace = (float) 6.0;
			} else {
				initialPrefPace = (float) 7.0;
			}		
		return initialPrefPace;
	}
	
	/**
	 * This method is used to add the value for the initial pace based on bpm
	 * The method needs additional work to allow for values in a range rather than specific values
	 * @param bpm  Integer that is the autocalculated BPM for a track
	 * @return initialPrefPace	 Float that is the preferred pace that this track should be used for
	 */
	public float setInitPrefPace(int bpm){
		SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(null);
		int unitType = sPref.getInt("unitType", 1);
		float initialPrefPace;

		if (unitType == 1) {
			if (bpm < 150) {
				initialPrefPace = (float) 10.0;
			} else if (bpm > 150 && bpm < 153) {
				initialPrefPace = (float) 9.0;
			} else if (bpm > 153 && bpm < 156) {
				initialPrefPace = (float) 8.0;
			} else if (bpm > 156 && bpm < 160) {
				initialPrefPace = (float) 7.0;
			} else if (bpm > 160 && bpm < 163) {
				initialPrefPace = (float) 6.0;
			} else if (bpm > 163 && bpm < 166) {
				initialPrefPace = (float) 6.0;
			} else if (bpm > 166 && bpm < 171) {
				initialPrefPace = (float) 6.0;
			} else {
				initialPrefPace = (float) 7.0;
			}		
		} else {
			if (bpm < 150) {
				initialPrefPace = (float) 16.0;
			} else if (bpm > 150 && bpm < 153) {
				initialPrefPace = (float) 14.0;
			} else if (bpm > 153 && bpm < 156) {
				initialPrefPace = (float) 12.0;
			} else if (bpm > 156 && bpm < 160) {
				initialPrefPace = (float) 10.0;
			} else if (bpm > 160 && bpm < 163) {
				initialPrefPace = (float) 9.0;
			} else if (bpm > 163 && bpm < 166) {
				initialPrefPace = (float) 8.0;
			} else if (bpm > 166 && bpm < 171) {
				initialPrefPace = (float) 5.0;
			} else {
				initialPrefPace = (float) 10.0;
			}
	}
		return initialPrefPace;

	}
	
	/**
	 * This method increases the preferred pace by 0.5
	 * Check if the user default is km or m, default database value is in m
	 * Convert from km to m if needed
	 * Identify which track object is current
	 * Then it changes the defaultPace value for that track
	 * The new value then needs to be written back to the database
	 * NOT YET WRITTEN
	 */
	public static void decPrefPace() {
		
	}
	
	/**
	 * This method decreases the preferred pace by 0.5
	 * It needs to identify which track object is current
	 * Then it changes the defaultPace value for that track
	 * The new value then needs to be written back to the database
	 * NOT YET WRITTEN
	 */
	public static void incPrefPace() {
		
	}
		
}
