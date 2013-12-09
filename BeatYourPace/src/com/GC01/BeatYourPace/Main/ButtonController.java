package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import android.view.View;

import com.GC01.BeatYourPace.ArchiveFiles.DatabaseActivity;
import com.GC01.BeatYourPace.MusicPlayer.MusicController;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.example.beatyourpace.R;

public class ButtonController {
	
	//static TrackList trackList = new TrackList(TargetPace.getTargetPace()); // This has the Target CurrentPace as its parameter
	
public static void buttonFunction(View v) {

	
		switch (v.getId()) {		
			case R.id.bPlaySong: 	 
				MusicController.pressPlay();
			    break;
			    
			case R.id.bSkipTrack: 
	    	    MusicController.pressSkip();
					
	            break; 
	           
			case R.id.bPreviousTrack: 
				MusicController.pressPrevious();
	            break;
	            
	            // Decreases the user's preferred pace for this track by 0.5.
			case R.id.bSongTooSlow:
				DatabaseActivity.decPrefPace();
	            break;
	            
	            // Increases the user's preferred pace for this track by 0.5.
			case R.id.bSongTooFast:
				DatabaseActivity.incPrefPace();
	            break;
	        
			case R.id.bDecTarget: 
				TargetPace.setTargetPace(false); 
				String tarPaceDec = String.valueOf(TargetPace.getTargetPace());
				TrainingModeActivity.targetPaceText.setText(tarPaceDec);
				MusicController.changeTarPace();
				break;
	            
			case R.id.bIncTarget:
            	TargetPace.setTargetPace(true);
            	String tarPaceInc = String.valueOf(TargetPace.getTargetPace());
            	TrainingModeActivity.targetPaceText.setText(tarPaceInc);
            	MusicController.changeTarPace();
	            break;
	            
	       //place holder buttons
			case R.id.placeHolderPause:
				MusicController.pressPause();

				break;
				
			case R.id.placeHolderStop:
				MusicController.pressStop();
				break;
	            
	            
		}
	  }
}
