package com.GC01.BeatYourPace.PaceCalculator;

import com.GC01.BeatYourPace.Main.LetsRunModeActivity;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

/** 
 * @author Laura Barbosa,  Kristian Knevitt 
 * @version 1.0, Updated 22/01/2014
 */

/**
 * Handles the user's preferred target pace options. 
 */




=======
/**
 * Calculates the target pace that is changed when the user selects increase or decrease target pace from the run modes
 * 
 * @author Laura Barbosa, Kristian Knevitt 
 * @version 2014/01/22
 */

public class TargetPace {


	/**
	 * Sets the new target pace
	 * @param incremenet Boolean value that sets if the amount is to be incremented or decremented
	 */
	public static void setTargetPace(boolean increment){
		
		if (increment == true){
			
			if (TrainingModeActivity.onScreen == true) {
			TrainingModeActivity.targetPace += 0.5	;
			}
			else {
				LetsRunModeActivity.targetPace += 0.5;
				System.out.println(LetsRunModeActivity.targetPace);
			}
			
		}
			
		else {
			if (TrainingModeActivity.onScreen == true && TrainingModeActivity.targetPace != 0.5) {
				TrainingModeActivity.targetPace -= 0.5;
			}
			else if (LetsRunModeActivity.targetPace != 0.5) {
				
				LetsRunModeActivity.targetPace -= 0.5;
				System.out.println(LetsRunModeActivity.targetPace);
			}
		}
		
	}
	
<<<<<<< HEAD


/** 
 *Retrieves the TargetPace from the training mode class if previously set. 
 */
	
=======
	/**
	 * Sets the new target pace
	 * @return targetPace  Float value for the revised target pace
	 */
>>>>>>> 4bdb1a8c0adb6ab2bce6db3d04c7d6959821af48
	public static float getTargetPace() {
		if (TrainingModeActivity.onScreen == true) {
		return TrainingModeActivity.targetPace;
		}
		else return LetsRunModeActivity.targetPace;

	}
	
}
