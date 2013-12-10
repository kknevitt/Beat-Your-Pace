package com.GC01.BeatYourPace.Main;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.GC01.BeatYourPace.PaceCalculator.*;
import com.GC01.BeatYourPace.MusicPlayer.MusicController;
import com.GC01.BeatYourPace.MusicPlayer.MusicPlayer;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class ButtonController extends Service {
	static Context context;
	static EasyTracker tracker = EasyTracker.getInstance(context);
	//static TrackList trackList = new TrackList(TargetPace.getTargetPace()); // This has the Target CurrentPace as its parameter
	
public static void buttonFunction(View v) {

	
		switch (v.getId()) {		
			case R.id.bPlayAndPause:
				if (MusicPlayer.getInstance().isPlaying())
				MusicController.pressPause();
				else
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
				InitialPrefPace.decPrefPace();
				
				/**Google Analytics tracking code**/
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "songTooSlow", null).build());
	            break;
	            
	            // Increases the user's preferred pace for this track by 0.5.
			case R.id.bSongTooFast:
				InitialPrefPace.incPrefPace(); 
				
				/**Google Analytics tracking code**/
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "songTooFast", null).build());
	            break;
	        
			case R.id.bDecTarget: 
				TargetPace.setTargetPace(false); 
				String tarPaceDec = String.valueOf(TargetPace.getTargetPace());
				TrainingModeActivity.targetPaceText.setText(tarPaceDec);
				MusicController.changeTarPace();
				
				/**Google Analytics tracking code**/
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "decreasePace", null).build());
				break;
	            
			case R.id.bIncTarget:
            	TargetPace.setTargetPace(true);
            	String tarPaceInc = String.valueOf(TargetPace.getTargetPace());
            	TrainingModeActivity.targetPaceText.setText(tarPaceInc);
            	MusicController.changeTarPace();

            	/**Google Analytics tracking code**/
            	tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "increasePace", null).build());               
            	break;
				
			case R.id.bStopSong:
				MusicController.pressStop();
				break;      
	            
		}
	  }

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}
}
