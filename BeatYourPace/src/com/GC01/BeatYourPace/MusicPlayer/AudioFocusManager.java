package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.util.Log;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

// see android audio focus tutorial

/** 
 * @author Kristian Knevitt & Laura Barbosa
 * @version 1.0, Updated 12/12/2013
 */


/** Manages Audio Focus, by handling requests and listeners to changes in focus, created using the Android "Managing Audio
 * Focus" Tutorial - http://developer.android.com/training/managing-audio/audio-focus.html and also:
 * Professional Android 4 Application Development, 3rd Edition - Chapter 15.
 */
public class AudioFocusManager{
	

	private  int result;
	private AudioManager audioMan;
	private static AudioFocusManager _instance;
	
	
	
	private AudioFocusManager(){
		
	// Uses the Android Audio Manager
	audioMan = (AudioManager) ContextProvider.getContext().getSystemService(Context.AUDIO_SERVICE);
		
	}
	
	
	// Singleton creator method
	public static AudioFocusManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new AudioFocusManager();
		}
		return _instance;
	}
	
	
	
	/** 
	 * Requests audio focus through the audio manager by passing in the listener, the type of stream, and the expected
	 * duration of focus (in this case unknown as it is to handle the playback of songs).
	 * Sets the result variable to the outcome of this request.
	 */
	public void requestFocus() {	
		
		result = audioMan.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		
		}
	
	/** Once Focus is no longer needed, it is abandoned, and the Music Player is stopped */
	public void abandonFocus() {
		
		audioMan = (AudioManager)  ContextProvider.getContext().getSystemService(Context.AUDIO_SERVICE);
		audioMan.abandonAudioFocus(focusChangeListener);
		MusicController.pressStop();	
	}
	
	
	/** Checks result of a request, returns true if it was successful */
	public boolean focusTest() {
		
		if (result == AudioManager.AUDIOFOCUS_GAIN) {
			Log.d("Audio Focus Manager", "Request Successful");
			return true;
		
		}
		else {
			Log.d("Audio Focus Manager", "Request Failed");
			return false;
		}
		
	}
		
		// Creating the listener needed.
		private OnAudioFocusChangeListener focusChangeListener = new OnAudioFocusChangeListener () {
			
			
			/** If the listener detects a change it will handle each type in a specific way */
			public void onAudioFocusChange(int focusChange) {
				
				switch (focusChange) {
				
				// If temporarily lost and not on the screen, it will pause playback
				case(AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) : 
					
					if (MusicPlayer.getInstance().currentlyPlaying() && TrainingModeActivity.onScreen == false){
					MusicController.pressPlay_Pause();
					}
					Log.d("Audio Focus Manager", "Focus Interrupted temporarily");
					
					
					// If quickly regained and not on the screen, it will resume playback
				case(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) : 
					
					if (MusicPlayer.getInstance().currentlyPlaying() && TrainingModeActivity.onScreen == false) {
					MusicController.pressPlay_Pause();
					
					}
					Log.d("Audio Focus Manager", "Focus regained temporarily");
				
					
					// If Focus lost for an unknown amount, it will stop playback indefinitely
				case (AudioManager.AUDIOFOCUS_LOSS) :
					
					if (MusicPlayer.getInstance().currentlyPlaying() && TrainingModeActivity.onScreen == false) {
					audioMan.abandonAudioFocus(focusChangeListener);
					MusicController.pressStop();
					}
					Log.d("Audio Focus Manager", "Focus lost indefinitely");
								
					
				case (AudioManager.AUDIOFOCUS_GAIN) :
							
					Log.d("Audio Focus Manager", "Focus regained while needed.");

				}
			}
		};
		
		
			
}