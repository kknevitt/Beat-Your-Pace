package com.GC01.BeatYourPace.PaceCalculator;

import com.GC01.BeatYourPace.Main.LetsRunModeActivity;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

public class TargetPace {


	public static void setTargetPace(boolean increment){
		
		if (increment == true){
			
			if (TrainingModeActivity.onScreen == true) {
			TrainingModeActivity.targetPace += 0.5	;
			}
			
		}
			
		else {
			if (TrainingModeActivity.onScreen == true) {
				TrainingModeActivity.targetPace -= 0.5;
			}
		
		}
		
	}
	
	// retrieving the targetPace
	public static float getTargetPace() {
		return TrainingModeActivity.targetPace;

	}
	
}
