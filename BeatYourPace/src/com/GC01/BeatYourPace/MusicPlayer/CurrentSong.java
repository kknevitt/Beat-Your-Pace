<<<<<<< HEAD
package com.GC01.BeatYourPace.MusicPlayer;

import com.GC01.BeatYourPace.Database.DatabaseAdapter1;
import com.GC01.BeatYourPace.Main.ContextProvider;

/** 
 * @author Kristian Knevitt
 * @version 1.0, Updated 12/12/2013
 */

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
	
	public void setSongInfo(String info){

	DatabaseAdapter1 db = new DatabaseAdapter1(ContextProvider.getContext());
	this.songInfo = db.getTrackInfo(info);
	
	}
	
	
	/** Sends the file path  of the current song by using the track index from the TrackList.
	 *
	 * @return songPath (String)The file path for the current song.
	 */
	public String getSongPath() {
				
		return this.songPath;
				
		}

	@Override
	public void setSongPath(String path) {
		
	this.songPath = path;
		
	}	

}
=======
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
>>>>>>> 8ff051f2ad689ef4643f6f096f75075d01e7b679
