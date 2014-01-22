package com.GC01.BeatYourPace.MusicPlayer;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 12/12/2013
 */

/** Blueprint for a song containing base variables */
public abstract class Song {
	
	
	/** A string of the filepath to be played by the MusicPlayer */
	protected String songPath;
	
	/** The Artist and Title of the Current Song */
	protected String songInfo;
	
	public Song(){
		
		songPath = "No path set"; 
		songInfo = "No track info set";
		
	}
	
	/** Queries the database for Information on the song dependent on the path sent
	 * 
	 * @param path (String) - Directory path for that song
	 */
	public abstract void setSongInfo(String info);
	
	public abstract void setSongPath(String path);
	
	
	/** Returns the track information on a song
	 * 
	 * @return trackInfo (String) - A concatenation of the Song's artist and title
	 */
	
	public String getSongInfo(){
		
		return songInfo;
	}
	
}
