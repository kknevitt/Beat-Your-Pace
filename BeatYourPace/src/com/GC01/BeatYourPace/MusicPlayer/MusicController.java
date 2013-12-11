package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.widget.Toast;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;

public class MusicController {
	
	public static void pressPlay() {
		
		if (!TrackList.getInstance().isEmpty()) {
 
		if (!MusicPlayer.getInstance().isPlaying()){		
		try {	
			MusicPlayer.getInstance().play();
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
		
	
		if (!TrackList.getInstance().isEmpty()) {
			
			if (MusicPlayer.getInstance().isPlaying()){
	try {
		MusicPlayer.getInstance().skip();
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
		if (!TrackList.getInstance().isEmpty()) {
			
		if (MusicPlayer.getInstance().isPlaying()){
		try {
        	MusicPlayer.getInstance().previous();	
        	
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
		
		
		
		TrackList.getInstance().updateTrackList((float) TargetPace.getTargetPace());
		
		if (!TrackList.getInstance().isEmpty()) {
				
		if (MusicPlayer.getInstance().isPlaying()){
			
		MusicPlayer.getInstance().stop();
		
		}
		
		System.out.println("TrackList wasn't null");
		
		try {
			MusicPlayer.getInstance().play();
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
		
		if (!TrackList.getInstance().isEmpty()) {
		MusicPlayer.getInstance().pause();
		
		}
		
	}
	
	public static void pressStop() {
		
		MusicPlayer.getInstance().stop();
	}
	
}
	
