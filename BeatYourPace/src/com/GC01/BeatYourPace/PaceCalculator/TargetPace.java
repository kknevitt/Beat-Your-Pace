package com.GC01.BeatYourPace.PaceCalculator;

import com.GC01.BeatYourPace.Main.LetsRunModeActivity;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

<<<<<<< HEAD
=======

>>>>>>> 4d6909d6d94a639180c7de8cc1ad60dd3dce0a4e
/**
 * Calculates the target pace that is changed when the user selects increase or decrease target pace from the run modes
 * 
 * @author Laura Barbosa, Kristian Knevitt 
 * @version 3.0 2014/01/22
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
=======

>>>>>>> 4d6909d6d94a639180c7de8cc1ad60dd3dce0a4e

/** 
 *Retrieves the TargetPace from the training mode class if previously set. 
 */
	
<<<<<<< HEAD
=======

>>>>>>> 4d6909d6d94a639180c7de8cc1ad60dd3dce0a4e

	/**
	 * Sets the new target pace
	 * @return targetPace  Float value for the revised target pace
	 */

	public static float getTargetPace() {
		if (TrainingModeActivity.onScreen == true) {
		return TrainingModeActivity.targetPace;
		}
		else return LetsRunModeActivity.targetPace;

	}
	
}
