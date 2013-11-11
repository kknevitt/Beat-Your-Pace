package com.GC01.BeatYourPace.MusicPlayer;

import android.media.MediaPlayer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;



public class MusicPlayer {
	
	//MusicRetriever List = new MusicRetriever(); // do we need an object from Music Retriever
	
	private static final MediaPlayer mediaPlayer = new MediaPlayer();
	static Context context;
	static MusicTrackList tracklist = new MusicTrackList();
	
	public static void play() {
		mediaPlayer.setDataSource(MusicTrackList.currentTrack);
		  mediaPlayer.start();		
	}
	
	public static void skip() {
		MusicTrackList.setCurrentTrack(2);
		  MediaPlayer.create(context, tracklist.getCurrentTrack());
		  mediaPlayer.start();	
	}
	
	public static void previous() {
		MusicTrackList.setCurrentTrack(3);
		  MediaPlayer.create(context, tracklist.getCurrentTrack());
		  mediaPlayer.start();	
	}

}
