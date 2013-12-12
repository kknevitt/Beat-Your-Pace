package com.GC01.BeatYourPace.MusicPlayer;
 
import java.io.IOException;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.GC01.BeatYourPace.Main.ContextProvider;
import com.google.analytics.tracking.android.Tracker;
/** 
 * @author Kristian Knevitt
 * @version 2.0, Updated 12/12/2013
 */


/** 
 * <p> The MusicPlayer object carries out the functions requested by the Music Controller using the information 
 * in the TrackList, meaning it does not compute the logic of functions it only carries out the functions requested
 * such as playing a song. </p>
 */
 
public class MusicPlayer extends MediaPlayer implements OnCompletionListener, OnErrorListener {	 

	/** Uses a MediaPlayer object from the android.media package as a base for playback functions */

	/** Singleton Instance of the MusicPlayer. */
	private static MusicPlayer _instance = null;
	
	private TrackList trackList = TrackList.getInstance();
	
	Tracker myTracker; 
	
	protected String trackInfo;
	
	
	
	

	
	/** Private constructor for MusicPlayer which ensure it has an onCompletionListener when it is created in order to know
	 * when to automatically play the next song.
	 */
	
	private MusicPlayer(){
		
		super();
		this.setOnCompletionListener(this);
		
	}
	
	/** Method for creating a Singleton instance of the MusicPlayer */
	public static MusicPlayer getInstance()
	{
		if(_instance == null)
		{
			_instance = new MusicPlayer();
		}
		return _instance;
	}
	
	
	
	/** Play the current song without manipulating the position within the TrackList */

	public void play(){
		
		try {
			playCurrentSong();

		} catch (IOException e) {
			Log.d("Music Player", "Unable to Play Current Song");
			e.printStackTrace();
		}
			
		/**Google Analytics tracking code **/
		/*	EasyTracker easyTracker = EasyTracker.getInstance(this);
			myTracker.send(MapBuilder
					.createException(new StandardExceptionParser(this, null)
					.getDescription(Thread.currentThread().getName(),
							e),
							false).build() 
							); */
		}
		           
	      

	
	
	/** Skips to the next song in the Tracklist ArrayList and plays that song */
	
	public void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("skip");
		playCurrentSong();
		
 	}
 	
	
	/** Reverts to the previous song in the TrackList ArrayList and plays that song	 */
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setSong("previous");
		playCurrentSong();	
 	}
	
	/** Instructs the mediaPlayer to play the determined song, this method is reused across the MusicPlayer */
	private void playCurrentSong() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		
		// Ensures that the Music Player will stop the current song before attempting to play the next to ensure it will not be
		// playing over the top of itself
		try {
		if (this.isPlaying()){
			this.stop();
			}
		}
		catch(Exception e){
			Log.d("Music Player", "Unable to stop the current song from playing");
		}
		
		
		/* The main sequence for playing a song, which ensures that it is in the correct stage for each event, ie 
		 * being unitialised before attempting to set which song to play, and preparing before attempting to play
		 * said song. Catches an an exception if unable to do so.
		 */
		
		try {
		this.reset();
		this.setDataSource(trackList.getSongPath());
		this.prepare();
		this.start();
		}
		catch(Exception e){
			Log.e("Music Player", "Unable to assign proper states and play song");
		}
		
		// Sends the current Track Information to be broadcast to the Activities to be displayed for the user.
		
		trackList.setTrackInfo(trackList.getSongPath());
		trackInfo = trackList.getTrackInfo();
		
		sendTrackInfo();
	
		}
	
	
	/** Uses a LocalBroadCastManager to send a broadcast for the activities to receive and in turn display the current
	 * track information including the artist and song name.
	 */
	public void sendTrackInfo() {
		
	// Creating a new Intent to send the broadcast to the Activity
	  Intent intent = new Intent("Track Info Event");
	  
	  // Puts an extra data on the intent which carries the Track Info for the activity to display.
	  intent.putExtra("Track Info Action", trackInfo);
	  LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
	  Log.d("Music Player", "Track Sent of" + trackInfo);
		}

	
	
	/** Automatically plays the next song when the last one has finished. */
	@Override
	public void onCompletion(MediaPlayer mp) {

		// Checks to see that the TrackList is not empty, in case a change in Target Pace has updated the TrackList so that when the song
		// finishes it will result in an error of not finding any paths.
		if (!trackList.isEmpty()) {
				try {
					skip();
				} catch (IOException e) {
					Log.d("MusicPlayer", "Unable to Skip to the next song after Completion");
					e.printStackTrace();
				}
		}
			
	}
	


	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		// TODO Auto-generated method stub
		return false;
	}
	
}


