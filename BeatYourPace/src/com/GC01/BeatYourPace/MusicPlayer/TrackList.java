package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;

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
	
	/** A string of the filepath of the current track to be played by the MusicPlayer */
	private String songPath;
	
	/** The index of the current song within the ArrayList */
	private int songNo;
	
	/** An ArrayList which will contain the information of the appropriate songs for the MusicPlayer to play*/
	private ArrayList<String> paceTrackList = new ArrayList(); // ArrayList containing song details and the path file for the song.	
	

	
	
	
	/** Constructor for the Tracklist -
	 * Queries the database to populate the ArrayList of details for appropriate songs, the database
	 * determines which songs are appropriate by referencing the current Target Running CurrentPace
	 * 
	 * @param inputPace The current Target Running CurrentPace of the user
	 */
	
	public TrackList(double defaultInputPace){
		
		// Calling the method for populating the ArrayList.
		
		updateTrackList(defaultInputPace);
	
		String first = "/sdcard/one.wav";
		paceTrackList.add(first);
		String second = "/sdcard/two.wav";
		paceTrackList.add(second);
		String third = "/sdcard/three.wav";
		paceTrackList.add(third);
		String fourth = "/sdcard/four.wav";
		paceTrackList.add(fourth);
		String fifth = "/sdcard/five.wav";
		paceTrackList.add(fifth);
	 	
		// Default songPath needed.
		songNo = 0;
		}
			
	
		/** Manipulates what the current Song index is depending on what function the MusicPlayer has
		 * requested.
		 *
		 * @param function The playback function that the MusicPlayer has requested. e.g. Play
		 */
	
	
		public void setSong(String function){
			
		// if the function is play set a random song from the ArrayList to play - dependent on the size of the Array
	 	// It is cast as an int so that it can be used as an index, and also means it wont go out of bounds of the Array List.
		System.out.println("song index before setSong is" + getSongIndex());
			
		if (function == "play") {
	
		
		}
		System.out.println("song index after play is" + getSongIndex());
		

		
		
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
		public void updateTrackList(double tarPace) {
			
			
			
			// insert code for repopulating trackList
			
			
			}
}


