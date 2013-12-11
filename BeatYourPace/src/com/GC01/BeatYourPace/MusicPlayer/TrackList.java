package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import com.GC01.BeatYourPace.Database.DatabaseAdapter;
import com.GC01.BeatYourPace.Main.ContextProvider;
import com.GC01.BeatYourPace.PaceCalculator.TargetPace;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 18/11/2013
 */

/** 
 * <p> This class is used to create the TrackList object </p>
 * <p> The TrackList object is used to determine a list of songs which are appropriate for the user
 * at their desired target pace, it does this by querying the database for songs of an appropriate BPM for the
 * inputPace which it received as its parameter. The TrackList object also holds the logic for manipulating 
 * the ArrayList in order to carry out the functions requested by the MusicPlayer </p>
 */



public class TrackList {
	Context context;
	
	/** A string of the filepath of the current track to be played by the MusicPlayer */
	private String songPath;
	
	/** The index of the current song within the ArrayList */
	private int songNo;
	
	/** An ArrayList which will contain the information of the appropriate songs for the MusicPlayer to play*/
	private ArrayList<String> paceTrackList = new ArrayList<String>(); // ArrayList containing song details and the path file for the song.	
	
		
	private static TrackList  _trackList = null;
	
	private int trackListSize;
	
	public static boolean empty;
	
	
	private TrackList(){
		
		// Calling the method for populating the ArrayList.
		updateTrackList((float) TargetPace.getTargetPace());
			 	
		// Default songPath needed.
		songNo = 0;
		}
	
	public static TrackList getInstance() {
		
		if (_trackList == null) {
			
			
			
			_trackList = new TrackList();
			
		}
		
		return _trackList;
		
	}
			
	
		/** Manipulates what the current Song index is depending on what function the MusicPlayer has
		 * requested.
		 *
		 * @param function The playback function that the MusicPlayer has requested. e.g. Play
		 */
	
	
		public void setSong(String function){
			
		// if the function is play set a random song from the ArrayList to play - dependent on the size of the Array
	 	// It is cast as an int so that it can be used as an index, and also means it wont go out of bounds of the Array List.
			
		// if the function is skip, sets the songNo to the next one in the array unless it as at the end.
		if (function == "skip") {
			System.out.println("song index before skip is" + getSongIndex());
			if (getSongIndex() == paceTrackList.size()-1){
				songNo = 0;
			}
			else
			songNo = getSongIndex() + 1;
			
		}
		
		
		System.out.println("song index after skip is" + getSongIndex());
			
		// if the function is previous it reduces the songNo by 1 unless it as at the beginning.	
		if (function == "previous") {
			
			System.out.println("song index before previous is" + getSongIndex());
			if (getSongIndex() == 0){
				songNo = paceTrackList.size()-1;
			}
			else
			songNo = getSongIndex() -1;
			
			System.out.println("song after previous before play is" + getSongIndex());
			
		}
		
		if (function == "reset"){
			
			songNo = 0;
		}
		System.out.println("song index after setSong is" + getSongIndex());
		
		
		}
	 	
		
		
	 	
		/** Sends the index value for the chosen song from the ArrayList
		 * 
		 * @return songNo (int) The current song from within the TrackList object.
		 */
		public int getSongIndex(){
					
			return songNo;
	 	}
	 	
		
		/** Sends the file path by using the song index from the ArrayList.
		 *
		 * @return songPath (String)The file path for the current song.
		 */
		public String getSongPath() {
			
					
			songPath = paceTrackList.get(getSongIndex());
			return songPath;
					
			}	

		
		// method for populating the TrackList
		public void updateTrackList(float tarPace) {
				
		
			// insert code for repopulating trackList
			DatabaseAdapter db = new DatabaseAdapter(ContextProvider.getContext());

			paceTrackList = db.getAppropriateSongs(tarPace);
			
		//	TrackList.getInstance().setSong("reset"); atm this causes recursive error.
			
			System.out.println("TrackList updated");
			System.out.println("Size is " + paceTrackList.size());

			for (int i = 0; i < paceTrackList.size(); i++){
				
				System.out.println(paceTrackList.get(i));
				
				
			}
		}
		
		public int getTrackListSize(){
			
			trackListSize = paceTrackList.size();
			
			return trackListSize;
			
			
		}
		
		public boolean isEmpty() {
			
			if (TrackList.getInstance().getTrackListSize() == 0){
						
						System.out.println("TrackList was empty");
						Toast.makeText(ContextProvider.getContext(), "No Songs at that Target Pace", Toast.LENGTH_LONG).show();
						
						empty = true;
						
					}
					
					else {
						
						empty = false;
						
					}

				return empty;
				}
			
				

		
}


