package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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
	
	/** The index of the current song within the ArrayList */
	private int trackIndex;
	
	/** An ArrayList which will contain the information of the appropriate songs for the MusicPlayer to play*/
	private ArrayList<String> currentTrackList = new ArrayList<String>(); // ArrayList containing song details and the path file for the song.	
	
	/** Static object of the Tracklist used in order to create a Singleton */	
	private static TrackList  _trackList = null;
	
	/** Amount of the current songs in the TrackList*/
	private int trackListSize;
	
	/** Whether the current TrackList has any songs in it*/
	public static boolean empty;
	

	
	
	
	
	/** Singleton constructor for the TrackList */
	private TrackList(){
		
		// The TrackList is populated with songs of an appropriate tempo based on the current Target Running Pace 
		updateTrackList((float) TargetPace.getTargetPace());
			 	
		// Initially pointing to the first song in the array.
		trackIndex = 0;
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
	
	
		public void setTrackIndex(String function){
			
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

		}
		
		
		
		public String getTrackIndex(int index){
			
			return currentTrackList.get(index);
		}
		
	 	
 	
		/** Sends the index value for the chosen song from the ArrayList
		 * 
		 * @return songIndex (int) The current song from within the TrackList object.
		 */
		
		
		public int getTrackIndex(){
					
			return trackIndex;
	 	}
	 	
		
		
		/** Updates the TrackList based on what the current (updated) Target Pace is, by querying the database of possible 
		 * appropriate songs based on their tempo in relation the target pace.
		 * @param tarPace Current Target Pace as set by the user or using the default preference.
		 */
		public void updateTrackList(float tarPace) {
				
			try {
			DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());

			currentTrackList = db.getAppropriateSongs(tarPace);
			
			Log.d("TrackList", "TrackList updated");
			}
			
			catch(Exception e){
				
				Log.d("TrackList", "TrackList update has failed");
				
			}
			
			
		//	TrackList.getInstance().setSong("reset"); atm this causes recursive error.
			
			

			/* Array list printing for debugging puproses
			for (int i = 0; i < paceTrackList.size(); i++){
				
				System.out.println(currentTrackList.get(i));
				
			}
			
			*/
							
		}
		
		
		
		/** Used for getting the current amount of song paths held within the TrackList
		 * 
		 * @return trackListSize (int) - Size of the TrackList
		 */
		public int getTrackListSize(){
			
			trackListSize = currentTrackList.size();
			
			return trackListSize;
			
		}
		
		
		
		/** Checks whether the current TrackList contains any song paths
		 * @return empty (boolean) - If true, the TrackList contains no elements.
		 */
		public boolean isEmpty() {
			
			if (currentTrackList.isEmpty()) {
						
						Log.d("TrackList", "TrackList was empty");
						Toast.makeText(ContextProvider.getContext(), "No Songs at that Target Pace", Toast.LENGTH_SHORT).show();
						empty = true;
						  Intent intent = new Intent("Track Info Event");
						  
						  // Puts an extra data on the intent which carries the Track Info for the activity to display.
						  intent.putExtra("Track Info Action", "Music Artist and Track");
						  LocalBroadcastManager.getInstance(ContextProvider.getContext()).sendBroadcast(intent);
							}
							
					
					
					else {
						
						empty = false;
						
					}

				return empty;
				}
		

		
		
		
		
}


