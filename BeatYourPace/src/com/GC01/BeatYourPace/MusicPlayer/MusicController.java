package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.widget.Toast;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;

public class MusicController {
	
	private static TrackList trackList = TrackList.getInstance();
	private static MusicPlayer musicPlayer = MusicPlayer.getInstance();
	
	public static void pressPlay() {
		
		if (!trackList.isEmpty()) {
 
		if (!musicPlayer.isPlaying()){		
		try {	
			musicPlayer.play();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		}
		}
		
		}
	}
	
	public static void pressSkip() {
		
	
		if (!trackList.isEmpty()) {
			
			if (musicPlayer.isPlaying()){
	try {
		musicPlayer.skip();
	}
	catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (IllegalStateException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	}

		
	}
	
	public static void pressPrevious() {
		if (!trackList.isEmpty()) {
			
		if (musicPlayer.isPlaying()){
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
			}
	}
	}
	
	public static void changeTarPace() {
		
		
		
		trackList.updateTrackList((float) TargetPace.getTargetPace());
		
		if (!trackList.isEmpty()) {
				
			if (musicPlayer.isPlaying()){
			
				musicPlayer.stop();
				
		
		}
		
		System.out.println("TrackList wasn't null");
		
		try {
			trackList.setSong("reset");
			musicPlayer.play();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
		
	public static void pressPause() {
		
		if (!trackList.isEmpty()) {
		musicPlayer.pause();
		
		}
		
	}
	
	public static void pressStop() {
		
		musicPlayer.stop();
	}
	
}
	
