package com.GC01.BeatYourPace.MusicPlayer;
 
import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */
import android.net.Uri;


/** 
 * <p> This class is used to create the MusicPlayer object </p>
 * <p> The MusicPlayer object is created using a TrackList object and is used to fulfil
 * the functions of a media player, such as playing a song and skipping to the next. </p>
 */
 
public class MusicPlayer implements OnCompletionListener, OnErrorListener {	 

	/** Uses a MediaPlayer object from the android.media package as a base for playback functions */
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	/** A TrackList object is used to supply the MusicPlayer with the song information it needs */
	private TrackList trackList;
	

	
	/** Constructor for MusicPlayer and assigning the TrackList it receives to a TrackList object
	 @return
	 @param trackListToPlay A TrackList determined by the target pace and a database query for appropriate songs for that target pace
	 */
	public MusicPlayer(TrackList trackListToPlay){
		
		mediaPlayer.setOnCompletionListener(this);
		
		trackList = trackListToPlay; // this needs to be the trackList that the Player is using.
	}
	
	
	/** Plays a random song from the current trackList by manipulating what the current song is and playing it
	 @return
	 @param
	 */
	public void play() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
	
		trackList.setSong("play");
		playCurrentSong();
		
	}
	
	
	/** Skips to the next song in the Tracklist ArrayList and plays that song
	 @return
	 @param
	 */
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		
		trackList.setSong("skip");
		playCurrentSong();
		
 	}
 	
	/** Reverts to the previous song in the TrackList ArrayList and plays that song
	 @return
	 @param
	 */
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("previous");
		playCurrentSong();	
 	}
	
	
	// Attempting to reduce reuse of code as all 3 buttons use this function.
	private void playCurrentSong() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		 
		// testing values
		String testTrack = trackList.getSongPath();
		int testIndex = trackList.getSongIndex();
		System.out.println(testTrack + testIndex);
		
		try {
		if (mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
		}
		catch(Exception e){
			System.out.println("Error in trying to stop the media player while playing");
		}
		
		
		
		mediaPlayer.reset();
		mediaPlayer.setDataSource(testTrack);
		mediaPlayer.prepare();
		mediaPlayer.start();
		
		}


	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
		try {
			skip();
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


	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}
	}


