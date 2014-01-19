package com.GC01.BeatYourPace.MusicPlayer;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 12/12/2013
 */

public class CurrentSong extends Song {
	
	private static TrackList trackList = TrackList.getInstance();
	
	private static CurrentSong _instance = null;

	
	private CurrentSong() {
		
		super();
		
	}
	
	/** Method for creating a Singleton instance of the Current Song if one does not already exist */
	public static CurrentSong getInstance() {
		if(_instance == null)
		{
			_instance = new CurrentSong();
		}
		return _instance;
	}

	
	/** Sends the file path  of the current song by using the track index from the TrackList.
	 *
	 * @return songPath (String)The file path for the current song.
	 */
	public String getSongPath() {
				
		songPath = trackList.getTrackIndex(trackList.getTrackIndex());
		return songPath;
				
		}	

}
