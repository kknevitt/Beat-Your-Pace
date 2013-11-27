package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import android.view.View;

import com.GC01.BeatYourPace.Database.DatabaseActivity;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.example.beatyourpace.R;

public class ButtonController {

public static void buttonFunction(View v) {
		
		TrackList trackList = new TrackList(getTargetPace()); // This has the Target Pace as its parameter
		MusicPlayer musicPlayer = new MusicPlayer(trackList); // This has the tracklist object as the parameter

		switch (v.getId()) {		
			case R.id.bPlaySong: 	    		  
					try {
						musicPlayer.play();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			    break;
			
			case R.id.bSkipTrack: 
	    	    	try {
						musicPlayer.skip();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            break; 
	                 
			case R.id.bPreviousTrack:
	                try {
						musicPlayer.previous();	
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 	
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
				setTargetPace(false); 
				String tarPace = String.valueOf(getTargetPace());
				TrainingModeActivity.targetPaceText.setText(tarPace);
	            break;
	            
			case R.id.bIncTarget:
	            	setTargetPace(true);
	            	String tarPace1 = String.valueOf(getTargetPace());
		    	    TrainingModeActivity.targetPaceText.setText(tarPace1);
	            break;
		}
	  }
	
		public static void setTargetPace(boolean increment){
			
			if (increment == true){
				TrainingModeActivity.targetPace += 0.5;
			}
				
			else {
				TrainingModeActivity.targetPace -= 0.5;
			}
			
			
		}
		
		// retrieving the targetPace
		public static double getTargetPace(){
			return TrainingModeActivity.targetPace;
	}

	
	
	
	
}
