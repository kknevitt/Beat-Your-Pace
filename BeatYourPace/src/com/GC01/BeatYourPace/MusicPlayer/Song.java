package com.GC01.BeatYourPace.MusicPlayer;

import com.GC01.BeatYourPace.Database.DatabaseAdapter1;
import com.GC01.BeatYourPace.Main.ContextProvider;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 12/12/2013
 */

/** Blueprint for a song containing base variables */
public class Song {
	
	
	/** A string of the filepath to be played by the MusicPlayer */
	protected String songPath;
	
	/** The Artist and Title of the Current Song */
	protected String trackInfo;
	
	public Song(){
		
		songPath = "No path set";
		trackInfo = "No track info set";
		
	}
	
	/** Queries the database for Information on the song dependent on the path sent
	 * 
	 * @param path (String) - Directory path for that song
	 */
	public void setTrackInfo(String path){

		DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());
		trackInfo = db.getTrackInfo(path);
	}
	
	
	/** Returns the track information on a song
	 * 
	 * @return trackInfo (String) - A concatenation of the Song's artist and title
	 */
	
	public String getTrackInfo(){
		
		return trackInfo;
	}
	
}
