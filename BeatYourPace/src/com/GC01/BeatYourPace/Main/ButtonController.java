package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import android.view.View;

import com.GC01.BeatYourPace.ArchiveFiles.DatabaseActivity;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.example.beatyourpace.R;

public class ButtonController {
	
	static TrackList trackList = new TrackList(TargetPace.getTargetPace()); // This has the Target CurrentPace as its parameter
	static MusicPlayer musicPlayer = new MusicPlayer(trackList); // This has the tracklist object as the parameter

public static void buttonFunction(View v) {
		

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
				TargetPace.setTargetPace(false); 
				String tarPace = String.valueOf(TargetPace.getTargetPace());
				TrainingModeActivity.targetPaceText.setText(tarPace);
	            break;
	            
			case R.id.bIncTarget:
	            	TargetPace.setTargetPace(true);
	            	String tarPace1 = String.valueOf(TargetPace.getTargetPace());
		    	    TrainingModeActivity.targetPaceText.setText(tarPace1);
	            break;
	            
	       //place holder buttons
			case R.id.placeHolderPause:
				musicPlayer.pause();
				break;
				
			case R.id.placeHolderStop:
				musicPlayer.stop();
				break;
	            
	            
		}
	  }
}
