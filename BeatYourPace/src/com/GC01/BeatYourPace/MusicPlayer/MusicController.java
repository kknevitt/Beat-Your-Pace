package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.util.Log;

import com.GC01.BeatYourPace.PaceCalculator.TargetPace;


/** 
 * @author Kristian Knevitt
 * @version 2.0, Updated 22/01/2014
 */


/** Controls the logic of what will occur to the MusicPlayer and TrackList based on what button's the user has
 * pressed
 */
public class MusicController {
	
	private static TrackList trackList = TrackList.getInstance();
	private static MusicPlayer musicPlayer = MusicPlayer.getInstance();

	
	/** Starts play-back of a track if there is one to be played and it is not currently playing, if it
	 * is not playing but has already been started it will resume that song, otherwise it will pause the
	 * play-back of the current song.
	 */
	public static void pressPlay_Pause() {
		
		// Conditions put in place to ensure media player cannot be playing more than one song at a time and properly resumes or pauses based on the state of the music player.
		// 
		if (!trackList.isEmpty() && !musicPlayer.currentlyPlaying()) {
			
			if (musicPlayer.getPosition() < 1000) {
				
			musicPlayer.play();
			}
	
			else if (musicPlayer.getPosition() >= 1000) {
				
				musicPlayer.resumeTrack();
				
			}
		}
			else if (!trackList.isEmpty() && musicPlayer.currentlyPlaying()){
				
				musicPlayer.pausePlayback();
		}
		
		
			 
	}
	
	/** Requests that the Music Player skips to the next song */
	public static void pressSkip() {
		
		if (!trackList.isEmpty() && musicPlayer.currentlyPlaying()) {
				
			try {
				musicPlayer.skip();
				System.out.println("skipping as music is thought to be playing");
			} 
			catch (IOException e) {
				Log.d("Music Controller", "IO Exception at Skip request");
				e.printStackTrace();
			}
		}

		
	}
	
	/** Requests that the Music Player reverts to playback of the previous song */
	
	public static void pressPrevious() {
		
		if (!trackList.isEmpty() && musicPlayer.currentlyPlaying()) {
				
			try {
				musicPlayer.previous();	

			} catch (IOException e) {
				Log.d("Music Controller", "IO Exception at PreviousTrack request");
				e.printStackTrace();
			}
		}
	
	}
	
	
	/** Handles a request of the target pace being changed by updating the tracklist based on the new
	 * variable
	 */
	public static void changeTarPace() {
		

		// Sends the Target Pace variable to the tracklist for it to be processed.
		trackList.updateTrackList((float) TargetPace.getTargetPace());
		
		// Stops current playback to ensure it will be playing the correct song.
		if (!trackList.isEmpty() && musicPlayer.currentlyPlaying()) {

				musicPlayer.stopPlayback();		
		}

		try {
			// resets to the first index of the tracklist to avoid out of bounds errors.
			trackList.setTrackIndex("reset");
			musicPlayer.play();
		}
			catch (Exception e) {
				Log.d("Music Controller", "Failure to restart playback of updated TrackList");
			
		}
		
	}
		
	/** Stops the Music Player, setting it to an unintialised state */
	public static void pressStop() {
		
		musicPlayer.stopPlayback();
	}
	
	/** Test for other areas of the system to check if there is currently music playing
	 * 
	 * @return True if there is music playing
	 */
	public static boolean isMusicPlaying() {
		
		return musicPlayer.currentlyPlaying();
	}
	
	/** Test  for other areas of the system to check if the Track-List if empty
	 * 
	 * @return True if the Track-List is empty
	 */
	public static boolean isTrackListEmpty(){
		
		return trackList.isEmpty();
	}
	
}
	
