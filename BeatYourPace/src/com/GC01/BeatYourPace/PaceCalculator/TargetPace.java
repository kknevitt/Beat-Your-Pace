package com.GC01.BeatYourPace.PaceCalculator;

import com.GC01.BeatYourPace.Main.LetsRunModeActivity;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

public class TargetPace {


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
	
	// retrieving the targetPace
	public static float getTargetPace() {
		if (TrainingModeActivity.onScreen == true) {
		return TrainingModeActivity.targetPace;
		}
		else return LetsRunModeActivity.targetPace;

	}
	
}
