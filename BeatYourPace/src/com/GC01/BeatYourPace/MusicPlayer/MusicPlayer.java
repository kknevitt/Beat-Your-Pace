package com.GC01.BeatYourPace.MusicPlayer;

import java.io.IOException;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.GC01.BeatYourPace.Main.ContextProvider;
/** 
 * @author Kristian Knevitt
 * @version 3.0, Updated 22/01/2014
 */


/** 
 * <p> The MusicPlayer object carries out the functions requested by the Music Controller in order to manipulate TrackList and play the CurrentSong </p>
 */
 
// Music Player uses the android media player by association, methods provide the specific implementation of audio playback for this app
public class MusicPlayer implements OnCompletionListener, OnErrorListener, OnPreparedListener {	 


	/** Singleton Instance of the MusicPlayer. */
	private static MusicPlayer _instance = null;
	
	protected TrackList trackList = TrackList.getInstance();
	private boolean playing;
	private int position;
	public static MediaPlayer mediaPlayer = new MediaPlayer();
	private String trackInfo;

	
	/** Private constructor for MusicPlayer which ensure it has an onCompletionListener when it is created in order to know
	 * when to automatically play the next song.
	 */
	
	private MusicPlayer(){

		mediaPlayer.setOnCompletionListener(this);
		position = 0;
		
	}
	
	// Singleton construction method
	public static MusicPlayer getInstance()
	{
		if(_instance == null)
		{
			_instance = new MusicPlayer();
		}
		return _instance;
	}
	
	
	
	/** Play the current song without manipulating the position within the TrackList */

	protected void play(){
		
		try {
			playCurrentSong();

		} catch (IOException e) {
			Log.d("Music Player", "Unable to Play Current Song");
			e.printStackTrace();
		}
	}
		           
	      

	
	
	/** Skips to the next song in the Tracklist ArrayList and plays that song */
	
	protected void skip() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setTrackIndex("skip");
		playCurrentSong();
		
 	}
 	
	
	/** Reverts to the previous song in the TrackList ArrayList and plays that song	 */
	protected void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		
		trackList.setTrackIndex("previous");
		playCurrentSong();	
 	}
	
	
	/** Instructs the mediaPlayer to play the determined song, this method is reused across the MusicPlayer */
	private void playCurrentSong() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		
		// Ensures that the Music Player will stop the current song before attempting to play the next to ensure it will not be
		// playing over the top of itself
		try {
		if (this.currentlyPlaying()){
			stopPlayback();
			}
		}
		catch(Exception e){
			Log.d("Music Player", "Unable to stop playback before starting playback.");
		}
		
		
		/* The main sequence for playing a song, which ensures that it is in the correct stage for each event, ie 
		 * being unitialised before attempting to set which song to play, and preparing the mediaPlayer.
		 */
		
		try {
		mediaPlayer.reset();
		mediaPlayer.setDataSource(trackList.getCurrentSong());
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.prepareAsync();
	//	mediaPlayer.start();
		
		}
		catch(Exception e){
			Log.e("Music Player", "Unable to assign proper state");
		}
		
		// Sends the current Track Information to be broadcast to the Activities to be displayed for the user.
		
		
		trackList.setCurrentTrackInfo(trackList.getCurrentSong());
		
		sendTrackInfo("(" + (trackList.getTrackIndex() +1) + "/" + trackList.getTrackListSize() + ")" + " " + trackList.getCurrentSongInfo());

		}

	// Will only attempt to start playback once the mediaplayer is in the correct state.
	public void onPrepared(MediaPlayer player){
		
		mediaPlayer.start();
	}
	
	
	/** Pauses Current Playback */
	protected void pausePlayback(){
		
		mediaPlayer.pause();
	}
	
	/** Stop current playback and setting the mediaPlayer to an unitialised state */
	protected void stopPlayback(){
		
	//	mediaPlayer.seekTo(0);
		mediaPlayer.stop();
	}
	
	
	/** Uses a LocalBroadCastManager to send a broadcast for the activities to receive and in turn display the current
	 * track information including the artist and song name.
	 * @param trackInfo 
	 */
	protected void sendTrackInfo(String trackInfo) {
		
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
	
	/** Resumes the track - only use if there is audio playing */
	protected void resumeTrack(){
		
		mediaPlayer.start();
	}

	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		// extra is the specific error, -1010 is the constant value of MEDIA_ERROR_UNSUPPORTED
		if (extra == -1010) {
				try {
					skip();
					Toast.makeText(ContextProvider.getContext(), "Format of " + trackList.getCurrentSongInfo() + "is not supported, skipping.", Toast.LENGTH_SHORT).show();
					return true;
					
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		return false;	
		}

	/** Checks if the MusicPlayer is playing by querying the mediaPlayer */
	public boolean currentlyPlaying(){
		
		if (mediaPlayer.isPlaying()) {
			playing = true;
			}
			
			else {
				playing = false;
				}
		return playing;

	}
		
	/** Gets the current position through the CUrrentSong
	 * 
	 * @return position (int) Milliseconds through a song
	 */
	protected int getPosition(){
		
		position = mediaPlayer.getCurrentPosition();
		return position;
	}
	
	

}
