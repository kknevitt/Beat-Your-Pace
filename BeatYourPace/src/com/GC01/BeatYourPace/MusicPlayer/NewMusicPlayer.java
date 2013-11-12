 package com.GC01.BeatYourPace.MusicPlayer;
 
import java.io.IOException;
import android.media.MediaPlayer;
import android.content.Context;


 
public class NewMusicPlayer {	 

	public Context context;
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private NewTrackList trackList; // default TrackList made to show methods working
	
	// creating a music player by taking in the tracklist object as it will need it.
	
	public NewMusicPlayer(NewTrackList trackListToPlay){
		
		trackList = trackListToPlay; // this needs to be the trackList that the Player is using.
	}
	
 	
	public void play() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("play");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();		
	}
	
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("skip");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();
 	}
 	
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
	
		trackList.setSong("previous");
		mediaPlayer.setDataSource(trackList.getSongPath());
		mediaPlayer.start();	
 	}
	
}
 
