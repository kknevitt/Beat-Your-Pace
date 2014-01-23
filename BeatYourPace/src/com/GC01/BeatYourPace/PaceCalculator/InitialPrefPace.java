package com.GC01.BeatYourPace.PaceCalculator;

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
	 * It calculates pace in miles or kilometres
	 * @param bpm  Integer that is the auto-calculated BPM for a track
	 * @return initialPrefPaceVals	 Object containing floats values in Miles and Km that are the initial preferred pace that this track should be used for
	 */
	public InitPrefPaceVals calcInitPrefPace(int bpm){
		float initialPrefPaceM = calcInitPrefPaceM(bpm);
		float initialPrefPaceKm = calcInitPrefPaceKm(bpm);
		return new InitPrefPaceVals(initialPrefPaceM, initialPrefPaceKm);
	}
	
	protected float calcInitPrefPaceM(int bpm) {
	
		float initialPrefPaceM;
		
		if (bpm > 215 && bpm <= 220){
			initialPrefPaceM = (float) 3.0;
		} else if (bpm > 210 && bpm <= 215) {
			initialPrefPaceM = (float) 3.5;
		}else if (bpm > 205 && bpm <= 210) {
			initialPrefPaceM = (float) 4.0;
		} else if (bpm > 200 && bpm <= 205) {
			initialPrefPaceM = (float) 4.5;
		} else if (bpm > 195 && bpm <= 200) {
			initialPrefPaceM = (float) 5.0;
		} else if (bpm > 190 && bpm <= 195) {
			initialPrefPaceM = (float) 5.5;
		} else if (bpm > 185 && bpm <= 190) {
			initialPrefPaceM = (float) 6.0;
		} else if (bpm > 180 && bpm <= 185) {
			initialPrefPaceM = (float) 6.5;
		} else if (bpm > 175 && bpm <= 180) {
			initialPrefPaceM = (float) 7.0;
		} else if (bpm > 170 && bpm <= 175) {
			initialPrefPaceM = (float) 7.5;
		} else if (bpm > 165 && bpm <= 170) {
			initialPrefPaceM = (float) 8.0;
		} else if (bpm > 160 && bpm <= 165) {
			initialPrefPaceM = (float) 8.5;
		} else if (bpm > 155 && bpm <= 160) {
			initialPrefPaceM = (float) 9.0;
		} else if (bpm > 150 && bpm <= 155) {
			initialPrefPaceM = (float) 9.5;
		} else if (bpm > 145 && bpm <= 150) {
			initialPrefPaceM = (float) 10.0;
		} else if (bpm > 140 && bpm <= 145) {
			initialPrefPaceM = (float) 10.5;
		} else if (bpm > 135 && bpm <= 140) {
			initialPrefPaceM = (float) 11.0;
		} else if (bpm > 130 && bpm <= 135) {
			initialPrefPaceM = (float) 11.5;
		} else if (bpm > 125 && bpm <= 130) {
			initialPrefPaceM = (float) 12.0;
		} else if (bpm > 120 && bpm <= 125) {
			initialPrefPaceM = (float) 12.5;
		} else if (bpm > 115 && bpm <= 120) {
			initialPrefPaceM = (float) 13.0;
		} else if (bpm > 110 && bpm <= 115) {
			initialPrefPaceM = (float) 13.5;
		} else if (bpm > 105 && bpm <= 110) {
			initialPrefPaceM = (float) 14.0;
		} else if (bpm > 100 && bpm <= 105) {
			initialPrefPaceM = (float) 14.5;
		} else if (bpm > 95 && bpm <= 100) {
			initialPrefPaceM = (float) 15.0;
		} else if (bpm > 90 && bpm <= 95) {
			initialPrefPaceM = (float) 15.5;
		} else if (bpm > 85 && bpm <= 90) {
			initialPrefPaceM = (float) 16.0;
		} else if (bpm > 80 && bpm <= 85) {
			initialPrefPaceM = (float) 16.5;
		} else if (bpm > 75 && bpm <= 80) {
			initialPrefPaceM = (float) 17.0;
		} else if (bpm > 70 && bpm <= 75) {
			initialPrefPaceM = (float) 17.5;
		} else if (bpm > 65 && bpm <= 70) {
			initialPrefPaceM = (float) 18.0;
		} else if (bpm > 60 && bpm <= 65) {
			initialPrefPaceM = (float) 18.5;
		} else if (bpm > 55 && bpm <= 60) {
			initialPrefPaceM = (float) 19.0;
		} else if (bpm > 50 && bpm <= 55) {
			initialPrefPaceM = (float) 19.5;
		} else if (bpm > 45 && bpm <= 50) {
			initialPrefPaceM = (float) 20.0;
		} else if (bpm > 40 && bpm <= 45) {
			initialPrefPaceM = (float) 20.5;
		} else {
			initialPrefPaceM = (float) 10.0;
		}
		
		return initialPrefPaceM;
	}
	
	protected float calcInitPrefPaceKm(int bpm) {
		
		float initialPrefPaceKm;
		
		if (bpm > 193 && bpm <= 201){
			initialPrefPaceKm = (float) 3.0;
		} else if (bpm > 185 && bpm <= 193) {
			initialPrefPaceKm = (float) 3.5;
		}else if (bpm > 178 && bpm < 185) {
			initialPrefPaceKm = (float) 4.0;
		} else if (bpm > 169 && bpm < 178) {
			initialPrefPaceKm = (float) 4.5;
		} else if (bpm > 160 && bpm < 169) {
			initialPrefPaceKm = (float) 5.0;
		} else if (bpm > 153 && bpm < 160) {
			initialPrefPaceKm = (float) 5.5;
		} else if (bpm > 145 && bpm < 153) {
			initialPrefPaceKm = (float) 6.0;
		} else if (bpm > 138 && bpm < 145) {
			initialPrefPaceKm = (float) 6.5;
		} else if (bpm > 129 && bpm < 138) {
			initialPrefPaceKm = (float) 7.0;
		} else if (bpm > 121 && bpm < 129) {
			initialPrefPaceKm = (float) 7.5;
		} else if (bpm > 113 && bpm < 121) {
			initialPrefPaceKm = (float) 8.0;
		} else if (bpm > 105 && bpm < 113) {
			initialPrefPaceKm = (float) 8.5;
		} else if (bpm > 97 && bpm < 105) {
			initialPrefPaceKm = (float) 9.0;
		} else if (bpm > 89 && bpm < 97) {
			initialPrefPaceKm = (float) 9.5;
		} else if (bpm > 81 && bpm < 89) {
			initialPrefPaceKm = (float) 10.0;
		} else if (bpm > 72 && bpm < 81) {
			initialPrefPaceKm = (float) 10.5;
		} else if (bpm > 64 && bpm < 72) {
			initialPrefPaceKm = (float) 11.0;
		} else if (bpm > 56 && bpm < 64) {
			initialPrefPaceKm = (float) 11.5;
		} else if (bpm > 48 && bpm < 56) {
			initialPrefPaceKm = (float) 12.0;
		} else if (bpm > 40 && bpm < 48) {
			initialPrefPaceKm = (float) 12.5;
		} else if (bpm > 32 && bpm < 40) {
			initialPrefPaceKm = (float) 13.0;
		} else if (bpm > 24 && bpm < 32) {
			initialPrefPaceKm = (float) 13.5;
		} else if (bpm > 16 && bpm < 24) {
			initialPrefPaceKm = (float) 14.0;
		} else if (bpm > 8 && bpm < 16) {
			initialPrefPaceKm = (float) 14.5;
		} else {
			initialPrefPaceKm = (float) 7.0;
		}
		
		return initialPrefPaceKm;
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
