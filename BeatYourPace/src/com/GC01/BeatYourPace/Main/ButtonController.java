package com.GC01.BeatYourPace.Main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.GC01.BeatYourPace.Database.DatabaseAdapter1;
import com.GC01.BeatYourPace.MusicPlayer.CurrentSong;
import com.GC01.BeatYourPace.MusicPlayer.MusicController;
import com.GC01.BeatYourPace.MusicPlayer.TrackList;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;
import com.example.beatyourpace.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

/** 
 * @author Laura Barbosa &  Kristian Knevitt
 * @version 1.0, Updated 12/12/2013
 */

/** Receives views and handles where the requests should be sent, e.g the play/pause button is pressed then a request
 * is sent to the MusicController to handle what will happen.
 * The class implements the EasyTracker Google Analytics object in order to gather insights into user activity.
 */
public class ButtonController extends Service {
	
	static EasyTracker tracker = EasyTracker.getInstance(ContextProvider.getContext());
	
	public static void buttonFunction(View v) {

		switch (v.getId()) {		
			case R.id.bPlayAndPause:
				MusicController.pressPlay_Pause();
			    break;
			    
			case R.id.bSkipTrack: 
	    	    MusicController.pressSkip();
	            break; 
	           
			case R.id.bPreviousTrack: 
				MusicController.pressPrevious();
	            break;
	            
			case R.id.bSongTooSlow:
				if (!TrackList.getInstance().isEmpty()) {
				DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());
				db.addPrefPace((float) 0.5, CurrentSong.getInstance().getSongPath());
				db.closeDb();
				
				MusicController.pressSkip();
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "songTooSlow", null).build());
				}
				
				break;
	            
	            
			    case R.id.bSongTooFast:
					if (!TrackList.getInstance().isEmpty()) {
					DatabaseAdapter1 db2 = new DatabaseAdapter1(ContextProvider.getContext());
					db2.addPrefPace((float) -0.5, CurrentSong.getInstance().getSongPath());
					db2.closeDb();
					MusicController.pressSkip();
					
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "songTooFast", null).build());
				}
					
	            break;
	        
			    case R.id.bDecTarget: 
					TargetPace.setTargetPace(false); 
					String tarPaceDec = String.valueOf(TargetPace.getTargetPace());
					if (TrainingModeActivity.onScreen == true) {
					TrainingModeActivity.targetPaceText.setText(tarPaceDec);					
					MusicController.changeTarPace();
			
				tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "decreasePace", null).build());
				}
					
				break;
	            
			case R.id.bIncTarget:
            	TargetPace.setTargetPace(true);
            	String tarPaceInc = String.valueOf(TargetPace.getTargetPace());
            	if (TrainingModeActivity.onScreen == true) {
            		TrainingModeActivity.targetPaceText.setText(tarPaceInc);	
            		MusicController.changeTarPace();

            	tracker.send(MapBuilder.createEvent("UI_Action", "button_press", "increasePace", null).build());               
            	}
            	
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
