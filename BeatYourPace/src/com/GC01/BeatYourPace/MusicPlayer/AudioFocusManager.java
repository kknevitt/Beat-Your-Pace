package com.GC01.BeatYourPace.MusicPlayer;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.Main.TrainingModeActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.IBinder;
import android.widget.Toast;

// see android audio focus tutorial


/* Create as singleton which can take in a parameter and uses a boolean flag to test whether it has focus or not
 * 
 * 
 * 
 */

public class AudioFocusManager{
	

	private  int result;
	private AudioManager audioMan;
	private static AudioFocusManager _instance;
	
	private AudioFocusManager(){

	audioMan = (AudioManager) ContextProvider.getContext().getSystemService(Context.AUDIO_SERVICE);
		
	}
	public static AudioFocusManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new AudioFocusManager();
		}
		return _instance;
	}
	
	
	
	
	public void requestFocus() {	
		
		result = audioMan.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		
		}
	
	public void abandonFocus() {
		
		AudioManager audioMan = (AudioManager)  ContextProvider.getContext().getSystemService(Context.AUDIO_SERVICE);
		audioMan.abandonAudioFocus(focusChangeListener);
		MusicController.pressStop();	
	}
	
	
	public boolean focusTest() {
		
		if (result == AudioManager.AUDIOFOCUS_GAIN) {
			System.out.println("AudioFocus Granted");
			return true;
		
		}
		else {
			System.out.println("AudioFocus Denied");
			return false;
		}
		
	}
		
		
		private OnAudioFocusChangeListener focusChangeListener = new OnAudioFocusChangeListener () {
			
			public void onAudioFocusChange(int focusChange) {
				
				switch (focusChange) {
				
				case(AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) : 
					
					if (MusicPlayer.getInstance().isPlaying() && TrainingModeActivity.onScreen == false){
					MusicController.pressPause();
					}
					Toast.makeText(ContextProvider.getContext(), "Focus Interrupted", Toast.LENGTH_LONG).show();
					
				case(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) : 
					
					if (MusicPlayer.getInstance().isPlaying() && TrainingModeActivity.onScreen == false) {
					MusicController.pressPause();
					}
					Toast.makeText(ContextProvider.getContext(), "Focus Resumed", Toast.LENGTH_LONG).show();
				
					
				case (AudioManager.AUDIOFOCUS_LOSS) :
					
					if (MusicPlayer.getInstance().isPlaying() && TrainingModeActivity.onScreen == false) {
					audioMan.abandonAudioFocus(focusChangeListener);
					MusicController.pressStop();
					}
					Toast.makeText( ContextProvider.getContext(), "Focus Lost", Toast.LENGTH_LONG).show();
								
					
				case (AudioManager.AUDIOFOCUS_GAIN) :
							
					Toast.makeText(ContextProvider.getContext(), "Focus Gained", Toast.LENGTH_LONG).show();

				}
			}
		};
		
		
			
}