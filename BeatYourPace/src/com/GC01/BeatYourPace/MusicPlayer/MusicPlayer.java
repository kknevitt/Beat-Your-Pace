package com.GC01.BeatYourPace.MusicPlayer;
 
import java.io.IOException;

<<<<<<< HEAD
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;
import com.google.analytics.tracking.android.Tracker;
=======
import com.GC01.BeatYourPace.Main.TrainingModeActivity;
>>>>>>> 87adb0e6b7921d47b1ac99122dc460e0a1bdc23e

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;


/** 
 * <p> This class is used to create the MusicPlayer object </p>
 * <p> The MusicPlayer object is created using a TrackList object and is used to fulfil
 * the functions of a media player, such as playing a song and skipping to the next. </p>
 */
 
public class MusicPlayer implements OnCompletionListener, OnErrorListener {	 

	/** Uses a MediaPlayer object from the android.media package as a base for playback functions */
	private static MediaPlayer mediaPlayer = new MediaPlayer();
	
	private static MusicPlayer _instance = null;
	
	private String currentSongPath;
	private int currentIndex;
	
	private static Context context;
	Tracker myTracker; 

	
	/** Constructor for MusicPlayer and assigning the TrackList it receives to a TrackList object
	 @return
	 @param trackListToPlay A TrackList determined by the target pace and a database query for appropriate songs for that target pace
	 */
	
	
	private MusicPlayer(){

		mediaPlayer.setOnCompletionListener(this);
		
	}
	
	public static MusicPlayer getInstance()
	{
		if(_instance == null)
		{
			_instance = new MusicPlayer();
		}
		return _instance;
	}
	
	
	/** Plays a random song from the current trackList by manipulating what the current song is and playing it
	 @return
	 @param
	 */
	public void play()  {
		
	
		try {
			playCurrentSong();
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
			
			/**Google Analytics tracking code **/
		/*	EasyTracker easyTracker = EasyTracker.getInstance(this);
			myTracker.send(MapBuilder
					.createException(new StandardExceptionParser(this, null)
					.getDescription(Thread.currentThread().getName(),
							e),
							false).build() 
							); */
		}
		           
	      
	}
	
	
	/** Skips to the next song in the Tracklist ArrayList and plays that song
	 @return
	 @param
	 */
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		
		TrackList.getInstance().setSong("skip");
		playCurrentSong();
		
 	}
 	
	/** Reverts to the previous song in the TrackList ArrayList and plays that song
	 @return
	 @param
	 */
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		TrackList.getInstance().setSong("previous");
		playCurrentSong();	
 	}
	
	public void pause() {
		if (mediaPlayer.isPlaying()) {
		mediaPlayer.pause();
		}
		else
			mediaPlayer.start();
	}
	
	public void stop() {
		
		mediaPlayer.stop();
	}
	
	
	
	// Attempting to reduce reuse of code as all 3 buttons use this function.
	private void playCurrentSong() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		
		currentSongPath = TrackList.getInstance().getSongPath();
		currentIndex = TrackList.getInstance().getSongIndex();
		System.out.println(currentSongPath + currentIndex);
		
		try {
		if (mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
		}
		catch(Exception e){
			System.out.println("Error in trying to stop the media player while playing");
		}
		
		mediaPlayer.reset();
		mediaPlayer.setDataSource(currentSongPath);
		mediaPlayer.prepare();
		mediaPlayer.start();
		
		}


	@Override
	public void onCompletion(MediaPlayer mp) {

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
	
	public boolean isPlaying() {
		
		if (mediaPlayer.isPlaying()) 
			return true;
		else
			return false;
	}


	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	/*

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not there yet");
	}
	
	public void onCreate() {
		
		Toast.makeText(this, "MusicPlayer service started", Toast.LENGTH_LONG).show();
	}
	
	public void onDestroy() {
		
		Toast.makeText(this, "MusicPlayer has ended", Toast.LENGTH_LONG).show();
		}
		
		*/
	
	}


