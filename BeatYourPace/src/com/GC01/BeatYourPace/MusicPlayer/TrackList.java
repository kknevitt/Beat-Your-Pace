package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.GC01.BeatYourPace.Database.DatabaseAdapter1;
import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;

/** 
 * @author Kristian Knevitt
 * @version 2.0, Updated 12/12/2013
 */

/** 
 * <p> The TrackList object is used to determine a list of songs which are appropriate for the user
 * at their desired target pace, it does this by querying the database for songs of an appropriate BPM for the
 * inputPace which it received as its parameter. The TrackList object also holds the logic for manipulating 
 * the ArrayList in order to carry out the functions requested by the MusicPlayer </p>
 */



public class TrackList {
	

	private int trackIndex;
	private ArrayList<String> currentTrackList = new ArrayList<String>();		
	private static TrackList  _trackList = null;
	private int trackListSize;
	private static boolean empty;
	private static CurrentSong currentSong = CurrentSong.getInstance();
	
	// Private constructor in order to make singleton instance.
	private TrackList(){
		
		
		// The TrackList is populated with songs of an appropriate tempo based on the current Target Running Pace 
		updateTrackList((float) TargetPace.getTargetPace());
			 	
		// Initially pointing to the first song in the array.
		trackListSize = 0;
		}
	
	
	
	
	/** A singleton instance of the TrackList*/ 
	public static TrackList getInstance() {
		
		if (_trackList == null) {

			_trackList = new TrackList();
		
		}
		
		return _trackList;	
		
	}
			
	
		/** Manipulates what the current Song index is depending on what function the MusicPlayer has
		 * requested.
		 *
		 * @param function The playback function that the MusicPlayer has requested. e.g. Skip, Previous and Reset.
		 */
	
	
		protected void setTrackIndex(String function){
			
		// Song index is being incremented, unless it is at the end at which point it is set to 0 in order to cycle.
		if (function == "skip") {
			
			Log.d("TrackList - skip", "This TrackList index is being skipped");
			
				if (getTrackIndex() == currentTrackList.size()-1)
					trackIndex = 0;
				else
					trackIndex = getTrackIndex() + 1;
			
		}
			
		
		// Song index is being decremented, unless it is 0 at which point it is pointed to the last element in the array list.	
		if (function == "previous") {
			
			Log.d("TrackList - previous", "This TrackList index is being decremented");
			
			if (getTrackIndex() == 0)
				trackIndex = currentTrackList.size()-1;
			else
				trackIndex = getTrackIndex() -1;
			
		}
		
				
		// Song Index is being reset to 0, this is a necessary function to avoid errors for when the array list is being updated to be 
		// smaller than it had been originally, as otherwise it would be possible to achieve out of bounds errors.
		if (function == "reset"){
			
			Log.d("TrackList", "TrackList index has been reset");
			
			trackIndex = 0;
			
			}
		
		
		// Updates the current song to be the modified value
		currentSong.setSongPath(currentTrackList.get(getTrackIndex()));

		}
		
		// Additional method to query the TrackList value for a given index
		protected String getTrackListValue(int index){
			
			return currentTrackList.get(index);
		}
		
		
		protected int getTrackIndex(){
					
			return trackIndex;
	 	}
	 	
		
		
		/** Updates the TrackList based on what the current (updated) Target Pace is, by querying the database of possible 
		 * appropriate songs based on their tempo in relation the target pace.
		 * @param tarPace Current Target Pace as set by the user or using the default preference.
		 */
		protected void updateTrackList(float tarPace) {
				
			try {
			DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());

			currentTrackList = db.getAppropriateSongs(tarPace);
				
			Collections.shuffle(currentTrackList);
			
			Log.d("TrackList", "TrackList updated");
			}
			
			catch(Exception e){
				
				Log.d("TrackList", "TrackList update has failed");
				
			}
			
							
		}
		
		
		
		/** Used for getting the current amount of song paths held within the TrackList
		 * 
		 * @return trackListSize (int) - Size of the TrackList
		 */
		protected int getTrackListSize(){
			
			trackListSize = currentTrackList.size();
			
			return trackListSize;
			
		}
		
		
		
		/** Checks whether the current TrackList contains any song paths - Sends a broadcast to update UI if it is.
		 * @return empty (boolean) - If true, the TrackList contains no elements.
		 */
		public boolean isEmpty() {
			
			if (currentTrackList.isEmpty()) {
						
						Log.d("TrackList", "TrackList was empty");
							empty = true;
							
						  Intent intent = new Intent("Track Info Event");
						  
						  // Puts an extra data on the intent which carries the Track Info for the activity to display.
						  intent.putExtra("Track Info Action", "Try another Target Pace!");
						  LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
							}
						
					else {
						
						empty = false;
						
					}

				return empty;
				}
		
		
		/** @return (String) Returns the file-path of the CurrentSong  */

		public String getCurrentSong() {
			
			return currentSong.getSongPath();
			
		}
		
		
		/** Set file-path of CurrentSong
		 * 
		 * @param songpath (String) filepath
		 */
		public void setCurrentTrackInfo(String songpath) {
			
			currentSong.setSongInfo(songpath);
			
		}
		
		/** Returns Artist and Title of CurrentSong
		 * 
		 * @return (String) Artist and Title
		 */
		protected String getCurrentSongInfo(){
			
			return currentSong.getSongInfo();
		}
		
}


