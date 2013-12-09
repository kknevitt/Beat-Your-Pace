package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;

public class MusicController {
	
	public static void pressPlay() {

		try {
			
			MusicPlayer.getInstance().play();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void pressSkip() {
	
	try {
		
		MusicPlayer.getInstance().skip();
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
	
	public static void pressPrevious() {
		
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
	
	public static void changeTarPace() {
		
		TrackList.getInstance().updateTrackList((float) TargetPace.getTargetPace());
		
		if (MusicPlayer.getInstance().isPlaying()){
			
		MusicPlayer.getInstance().stop();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
		
	public static void pressPause() {
		
		MusicPlayer.getInstance().pause();
		
	}
	
	public static void pressStop() {
		
		MusicPlayer.getInstance().stop();
	}
}
	
