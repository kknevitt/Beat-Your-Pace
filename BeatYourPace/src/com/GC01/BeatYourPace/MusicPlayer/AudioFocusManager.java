package com.GC01.BeatYourPace.MusicPlayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.IBinder;
import android.widget.Toast;

// see android audio focus tutorial

public class AudioFocusManager{
	
	private Context context;
	private  int result;
	private AudioManager audioMan;

	
	
	
	public AudioFocusManager(Context context){
		
	this.context = context;
		
	audioMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
	}
	
	public void requestFocus() {	
		
		result = audioMan.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		
		}
	
	public void abandonFocus() {
		
		AudioManager audioMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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
							
					MusicController.pressPause();
					Toast.makeText(context, "Focus Interrupted", Toast.LENGTH_LONG).show();
					
				case(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) : 
					
					MusicController.pressPause();
					Toast.makeText(context, "Focus Resumed", Toast.LENGTH_LONG).show();
				
					
				case (AudioManager.AUDIOFOCUS_LOSS) :
			
					audioMan.abandonAudioFocus(focusChangeListener);
					MusicController.pressStop();
					Toast.makeText(context, "Focus Lost", Toast.LENGTH_LONG).show();
								
					
				case (AudioManager.AUDIOFOCUS_GAIN) :
							
				
					Toast.makeText(context, "Focus Gained", Toast.LENGTH_LONG).show();

				}
			}
		};
		
		
			
}