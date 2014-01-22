package com.GC01.BeatYourPace.MusicPlayer;

import com.GC01.BeatYourPace.Database.DatabaseAdapter1;
import com.GC01.BeatYourPace.Main.ContextProvider;

/** 
 * @author Kristian Knevitt
 * @version 2.0, Updated 12/01/2014
 */

	/** Singleton Object of whatever song the music player is ready to play */
	public class CurrentSong extends Song {
	
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
	
	// implementing the method from abstract to specific app purposes
	/** Set the song info using the file path
	 * @param info (String) - Artist and Title of the song.
	 */
	public void setSongInfo(String info){
		
		DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());
		this.songInfo = db.getTrackInfo(info);
	
	}
	
	/** Set the file-path of the current Song 
	 * @param path (String) file-path
	 */
	@Override
	public void setSongPath(String path) {
		
		this.songPath = path;
		
	}	
	
	/** Getter method for the song file-path
	 *
	 * @return songPath (String)The file path for the current song.
	 */
	public String getSongPath() {
				
		return this.songPath;
				
		}
	
	

}