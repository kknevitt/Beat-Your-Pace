package com.GC01.BeatYourPace.PaceCalculator;

import android.annotation.TargetApi;
import android.os.Build;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * This class has methods to calculate and add the preferred pace meta data for a track based on the bpm of the track
 * @author Sarah Nicholson
 * @version 22/01/2014
 *
 */

public class InitialPrefPaceNavMap {

	public InitialPrefPaceNavMap() {
	}	

	
	/**
	 * This method is used to add the values for the initial pace based on bpm
	 * It calculates pace in miles and kilometres
	 * @param bpm  Integer that is the auto-calculated BPM for a track
	 * @return initialPrefPaceVals	 Object containing floats that are the initial preferred pace that this track should be used for in Miles and Km
	 */
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public InitPrefPaceVals calcInitPrefPace(int bpm){

		float initialPrefPaceM;
		float initialPrefPaceKm;

		if (bpm == 0) {
			initialPrefPaceM = (float) 10.0;
			initialPrefPaceKm = (float) 7.0;
			return new InitPrefPaceVals(initialPrefPaceM, initialPrefPaceKm);
		} else {
		
		NavigableMap<Integer, Float> nMapMiles = new TreeMap<Integer, Float>();
		nMapMiles.put(45, (float) 20.5);
		nMapMiles.put(50, (float) 20.0);
		nMapMiles.put(55, (float) 19.5);
		nMapMiles.put(60, (float) 19.0);
		nMapMiles.put(65, (float) 18.5);
		nMapMiles.put(70, (float) 18.0);
		nMapMiles.put(75, (float) 17.5);
		nMapMiles.put(80, (float) 17.0);
		nMapMiles.put(85, (float) 16.5);
		nMapMiles.put(90, (float) 16.0);
		nMapMiles.put(95, (float) 15.5);
		nMapMiles.put(100, (float) 15.0);
		nMapMiles.put(105, (float) 14.5);
		nMapMiles.put(110, (float) 14.0);
		nMapMiles.put(115, (float) 13.5);
		nMapMiles.put(120, (float) 13.0);
		nMapMiles.put(125, (float) 12.5);
		nMapMiles.put(130, (float) 12.0);
		nMapMiles.put(135, (float) 11.5);
		nMapMiles.put(140, (float) 11.0);
		nMapMiles.put(145, (float) 10.5);
		nMapMiles.put(150, (float) 10.0);
		nMapMiles.put(155, (float) 9.5);
		nMapMiles.put(160, (float) 9.0);
		nMapMiles.put(165, (float) 8.5);
		nMapMiles.put(170, (float) 8.0);
		nMapMiles.put(175, (float) 7.5);
		nMapMiles.put(180, (float) 7.0);
		nMapMiles.put(185, (float) 6.5);
		nMapMiles.put(190, (float) 6.0);
		nMapMiles.put(195, (float) 5.5);
		nMapMiles.put(200, (float) 5.0);
		nMapMiles.put(205, (float) 4.5);
		nMapMiles.put(210, (float) 4.0);
		nMapMiles.put(215, (float) 3.5);
		nMapMiles.put(220, (float) 3.0);


		NavigableMap<Integer, Float> nMapKm = new TreeMap<Integer, Float>();
		nMapKm.put(8, (float) 15.0);
		nMapKm.put(16, (float) 14.5);
		nMapKm.put(24, (float) 14.0);
		nMapKm.put(32, (float) 13.5);
		nMapKm.put(40, (float) 13.0);
		nMapKm.put(48, (float) 12.5);
		nMapKm.put(56, (float) 12.0);
		nMapKm.put(64, (float) 11.5);
		nMapKm.put(72, (float) 11.0);
		nMapKm.put(81, (float) 10.5);
		nMapKm.put(89, (float) 10.0);
		nMapKm.put(97, (float) 9.5);
		nMapKm.put(105, (float) 9.0);
		nMapKm.put(113, (float) 8.5);
		nMapKm.put(121, (float) 8.0);
		nMapKm.put(129, (float) 7.5);
		nMapKm.put(138, (float) 7.0);
		nMapKm.put(145, (float) 6.5);
		nMapKm.put(153, (float) 6.0);
		nMapKm.put(160, (float) 5.5);
		nMapKm.put(169, (float) 5.0);
		nMapKm.put(178, (float) 4.5);
		nMapKm.put(185, (float) 4.0);
		nMapKm.put(193, (float) 3.5);
		nMapKm.put(201, (float) 3.0);

		initialPrefPaceKm = nMapKm.ceilingEntry(bpm).getValue();
		initialPrefPaceM = nMapMiles.ceilingEntry(bpm).getValue();

		return new InitPrefPaceVals(initialPrefPaceM, initialPrefPaceKm);
		}
	}

	/**
	 * Class to allow two values to be returned from calcInitialPrefPace
	 * Functions in the inner class return the initialPrefPaceM and the initialPrefPaceKm
	 */
	public final class InitPrefPaceVals {
		private float initialPrefPaceM;
		private float initialPrefPaceKm;

		protected InitPrefPaceVals (float initialPrefPaceM, float initialPrefPaceKm) {
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