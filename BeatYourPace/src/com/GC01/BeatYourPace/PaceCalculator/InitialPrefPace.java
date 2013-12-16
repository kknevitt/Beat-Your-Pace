package com.GC01.BeatYourPace.PaceCalculator;

import android.util.Log;

/**
 * This class has methods to calculate and add the preferred pace meta data for a track
 * @author sarahnicholson
 *
 */

public class InitialPrefPace {
	
	private static final String LOG_CAT = "InitialPrefPace";
	
	public InitialPrefPace() {
	}
	
	/**
	 * This method is used to add the value for the initial pace based on bpm
	 * It calculates pace in miles
	 * @param bpm  Integer that is the auto-calculated BPM for a track
	 * @return initialPrefPace	 Float that is the initial preferred pace that this track should be used for
	 */
	public InitPrefPaceVals calcInitPrefPace(int bpm){
		float initialPrefPaceM;
		float initialPrefPaceKm;
			if (bpm < 150) {
				initialPrefPaceM = (float) 10.0;
				initialPrefPaceKm = (float) 16.0;
			} else if (bpm > 150 && bpm < 153) {
				initialPrefPaceM = (float) 9.0;
				initialPrefPaceKm = (float) 14.0;
			} else if (bpm > 153 && bpm < 156) {
				initialPrefPaceM = (float) 8.0;
				initialPrefPaceKm = (float) 12.0;
			} else if (bpm > 156 && bpm < 160) {
				initialPrefPaceM = (float) 7.0;
				initialPrefPaceKm = (float) 10.0;
			} else if (bpm > 160 && bpm < 163) {
				initialPrefPaceM = (float) 6.0;
				initialPrefPaceKm = (float) 9.0;
			} else if (bpm > 163 && bpm < 166) {
				initialPrefPaceM = (float) 6.0;
				initialPrefPaceKm = (float) 8.0;
			} else if (bpm > 166 && bpm < 171) {
				initialPrefPaceM = (float) 5.0;
				initialPrefPaceKm = (float) 6.0;
			} else {
				initialPrefPaceM = (float) 7.0;
				initialPrefPaceKm = (float) 10.0;
			}		
			Log.d(LOG_CAT,"Pace preference added to db");
		return new InitPrefPaceVals(initialPrefPaceM, initialPrefPaceKm);
	}
	
	/**
	 * Class to allow two values to be returned from calcInitialPrefPace
	 * 
	 */
	public final class InitPrefPaceVals {
	    private float initialPrefPaceM;
	    private float initialPrefPaceKm;

	    public InitPrefPaceVals (float initialPrefPaceM, float initialPrefPaceKm) {
	        this.initialPrefPaceM = initialPrefPaceM;
	        this.initialPrefPaceKm = initialPrefPaceKm;
	    }

		public float getIPPM() {
	        return initialPrefPaceM;
	    }

	    public float getIPPKM() {
	        return initialPrefPaceKm;
	    }
	}
		
}
