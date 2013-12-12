package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;

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

	
	/** A string of the filepath of the current track to be played by the MusicPlayer */
	private String songPath;
	
	/** The index of the current song within the ArrayList */
	private int songIndex;
	
	/** An ArrayList which will contain the information of the appropriate songs for the MusicPlayer to play*/
	private ArrayList<String> paceTrackList = new ArrayList<String>(); // ArrayList containing song details and the path file for the song.	
	
	/** Static object of the Tracklist used in order to create a Singleton */	
	private static TrackList  _trackList = null;
	
	/** Amount of the current songs in the TrackList*/
	private int trackListSize;
	
	/** Whether the current TrackList has any songs in it*/
	public static boolean empty;
	
	/** The Artist and Song of the Current Song */
	private String trackInfo;
	
	
	
	
	/** Singleton constructor for the TrackList */
	private TrackList(){
		
		// The TrackList is populated with songs of an appropriate tempo based on the current Target Running Pace 
		updateTrackList((float) TargetPace.getTargetPace());
			 	
		// Initially pointing to the first song in the array.
		songIndex = 0;
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
	
	
		public void setSong(String function){
			
		// Song index is being incremented, unless it is at the end at which point it is set to 0 in order to cycle.
		if (function == "skip") {
			
			Log.d("TrackList - skip", "This TrackList index is being skipped");
			
				if (getSongIndex() == paceTrackList.size()-1)
					songIndex = 0;
				else
					songIndex = getSongIndex() + 1;
			
		}
			
		
		// Song index is being decremented, unless it is 0 at which point it is pointed to the last element in the array list.	
		if (function == "previous") {
			
			Log.d("TrackList - previous", "This TrackList index is being decremented");
			
			if (getSongIndex() == 0)
				songIndex = paceTrackList.size()-1;
			else
				songIndex = getSongIndex() -1;
			
		}
		
				
		// Song Index is being reset to 0, this is a necessary function to avoid errors for when the array list is being updated to be 
		// smaller than it had been originally, as otherwise it would be possible to achieve out of bounds errors.
		if (function == "reset"){
			
			Log.d("TrackList - reset", "Is now the current TrackList index after being reset");
			
			songIndex = 0;
			
		}
		
		
		
		}
	 	
 	
		/** Sends the index value for the chosen song from the ArrayList
		 * 
		 * @return songIndex (int) The current song from within the TrackList object.
		 */
		public int getSongIndex(){
					
			return songIndex;
	 	}
	 	
		
		
		/** Sends the file path by using the song index from the ArrayList.
		 *
		 * @return songPath (String)The file path for the current song.
		 */
		public String getSongPath() {
					
			songPath = paceTrackList.get(getSongIndex());
			return songPath;
					
			}	

		
		
		
		/** Updates the TrackList based on what the current (updated) Target Pace is, by querying the database of possible 
		 * appropriate songs based on their tempo in relation the target pace.
		 * @param tarPace Current Target Pace as set by the user or using the default preference.
		 */
		public void updateTrackList(float tarPace) {
				
			DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());

			paceTrackList = db.getAppropriateSongs(tarPace);
			
		//	TrackList.getInstance().setSong("reset"); atm this causes recursive error.
			
			Log.d("TrackList - updateTrackList", "TrackList updated");

			/* Array list printing for debugging puproses
			for (int i = 0; i < paceTrackList.size(); i++){
				
				System.out.println(paceTrackList.get(i));
				
			}
			
			*/
							
		}
		
		
		
		/** Used for getting the current amount of song paths held within the TrackList
		 * 
		 * @return trackListSize (int) - Size of the TrackList
		 */
		public int getTrackListSize(){
			
			trackListSize = paceTrackList.size();
			
			return trackListSize;
			
		}
		
		
		
		/** Checks whether the current TrackList contains any song paths
		 * @return empty (boolean) - If true, the TrackList contains no elements.
		 */
		public boolean isEmpty() {
			
			if (paceTrackList.isEmpty()) {
						
						Log.d("TrackList - isEmpty", "TrackList was empty");
						Toast.makeText(ContextProvider.getContext(), "No Songs at that Target Pace", Toast.LENGTH_SHORT).show();
						empty = true;		
					}
					
					else {
						
						empty = false;
						
					}

				return empty;
				}
		
		
		
		
		
		
		public void setTrackInfo(String path){

			DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());
			trackInfo = db.getTrackInfo(path);
		}
		
		
		public String getTrackInfo(){
			
			return trackInfo;
		}
		
		
		
		
}


