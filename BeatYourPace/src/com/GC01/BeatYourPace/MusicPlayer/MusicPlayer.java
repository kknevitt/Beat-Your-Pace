package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.media.MediaPlayer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


//music player version1



public class MusicPlayer {	 
	
	//MusicRetriever List = new MusicRetriever(); // do we need an object from Music Retriever
	
	Context context;
	MusicTrackList tracklist = new MusicTrackList();
	MediaPlayer mediaPlayer = new MediaPlayer();
	
	public MusicPlayer(){
	
	}
	
	public void play() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		tracklist.setCurrentTrack(1);
		mediaPlayer.setDataSource(tracklist.getCurrentTrack());
		mediaPlayer.start();		
	}
	
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		tracklist.setCurrentTrack(2);
		  mediaPlayer.setDataSource(tracklist.getCurrentTrack());
		  mediaPlayer.start();	
	}
	
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
	
		tracklist.setCurrentTrack(3);
		  mediaPlayer.setDataSource(tracklist.getCurrentTrack());
		  mediaPlayer.start();	
	}

}
