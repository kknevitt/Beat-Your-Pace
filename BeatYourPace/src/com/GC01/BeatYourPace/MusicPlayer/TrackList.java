package com.GC01.BeatYourPace.MusicPlayer;

import java.util.ArrayList;

import com.GC01.BeatYourPace.Database.DataModel;
import com.GC01.BeatYourPace.Database.DatabaseHelper;

public class TrackList {
	
	private String songPath;
	private int songNo;
	private ArrayList<DataModel> paceTrackList = new ArrayList(); // ArrayList containing song details and the path file for the song.	
	

	public TrackList(String inputPace){
		
		double outPutPace = Double.parseDouble(inputPace);
		//	Creating a temporary Track List based on the desired Target Pace
		
	//	paceTrackList = DatabaseHelper.getAppropriateSongs(outPutPace); This is meant to 
	 	
		// Default songPath needed.
		songNo = 0;
		}
			
		// Chooses a random song from the list of appropriate songs.
		public void setSong(String function){
			
		// if the function is play set a random song from the ArrayList to play - dependent on the size of the Array
	 	// It is cast as an int so that it can be used as an index, and also means it wont go out of bounds of the Array List.
	
		if (function == "play")
			songNo = (int) (Math.random() * paceTrackList.size());
		
		
		// if the function is skip, sets the songNo to the next one in the array unless it as at the end.
		if (function == "skip")
			if (songNo == paceTrackList.size()-1){
				songNo = 0;
			}
			else
			songNo = songNo + 1;
		
			
		// if the function is previous it reduces the songNo by 1 unless it as at the beginning.	
		if (function == "previous")
			if (songNo == 0){
				songNo = paceTrackList.size()-1;
			}
			else
			songNo = songNo -1;
		
		}
	 	
	 	
		// Sends the index value for the chosen song from the ArrayList
		public int getSongIndex(){
					
			return songNo;
	 	}
	 	
	 	
		
		
		// Sends the file path by using the song index from the ArrayList.
		public String getSongPath() {
			
			// This will use a method to be implemented later in order to get the path for the song which
			// is at that SongIndex from within the ArrayList.
			
		//	songPath = paceTrackList.(getSongIndex().getFilePath()); 
			
			return songPath;
					
			}	
}


