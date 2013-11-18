package com.GC01.BeatYourPace.MusicPlayer;
 
import java.io.IOException;
import android.media.MediaPlayer;
/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */


/** 
 * <p> This class is used to create the MusicPlayer object </p>
 * <p> The MusicPlayer object is created using a TrackList object and is used to fulfil
 * the functions of a media player, such as playing a song and skipping to the next. </p>
 */
 
public class MusicPlayer {	 

	/** Uses a MediaPlayer object from the android.media package as a base for playback functions */
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	/** A TrackList object is used to supply the MusicPlayer with the song information it needs */
	private TrackList trackList;
	

	
	/** Constructor for MusicPlayer and assigning the TrackList it receives to a TrackList object
	 @return
	 @param trackListToPlay A TrackList determined by the target pace and a database query for appropriate songs for that target pace
	 */
	public MusicPlayer(TrackList trackListToPlay){
		
		trackList = trackListToPlay; // this needs to be the trackList that the Player is using.
	}
	
	
	/** Plays a random song from the current trackList by manipulating what the current song is and playing it
	 @return
	 @param
	 */
	public void play() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("play");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();		
	}
	
	
	/** Skips to the next song in the Tracklist ArrayList and plays that song
	 @return
	 @param
	 */
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("skip");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();
 	}
 	
	/** Reverts to the previous song in the TrackList ArrayList and plays that song
	 @return
	 @param
	 */
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
	
		trackList.setSong("previous");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();	
 	}
	
}
 
