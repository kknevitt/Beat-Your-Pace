package com.GC01.BeatYourPace.MusicPlayer;

<<<<<<< HEAD

public class MusicRetriever {
	
	public static final int[] tracks = new int[2];
	
	
	public MusicRetriever(){
		tracks[0] = R.raw.song1;
		tracks[1] = R.raw.song;
	}
=======
import java.util.ArrayList;

import android.R;

// This is meant too get information from the database retriever which will point at the right song to be played
// as it will have the information of track id and its BPM and PreferredPace.

public class MusicRetriever {
	
	//public static final int[] tracks = new int[2];
	String songPath;
	int songNo;
	ArrayList<String> appropriateSongs = new ArrayList();	
	
	
	public MusicRetriever(ArrayList<String> dBappropriateSongs){
		
		// copying the references from the 
		appropriateSongs = (ArrayList<String>)dBappropriateSongs.clone();
		
		// Default songPath needed.
		songNo = 0;
		
		//tracks[0] = R.raw.song1;
		//tracks[1] = R.raw.song;
	}
		
	// Chooses a random song from the list of appropriate songs.
	public void setSong(){
		
		// setting a random song from the ArrayList to play - dependent on the size of the Array
		// It is cast as an int so that it can be used as an index, and also means it wont go out of bounds of the Array List.
		
		songNo = (int) (Math.random() * appropriateSongs.size());
	
	}
	
	
	// Sends the index value for the chosen song from the ArrayList
	public int getSongIndex(){
				
		return songNo;
		
	}
	
	
	// Sends the path of the songNo.	
	public String getSongPath() {
		
		songPath = appropriateSongs.get(songNo);
		
		return songPath;
				
		}
			
	}
	
	
	
	
>>>>>>> branch 'master' of https://github.com/kknevitt/Beat-Your-Pace.git
	
	
	
	/*static int getCardId(Context context) {
	        ContentResolver res = context.getContentResolver();
	        Cursor c = res.query(Uri.parse("content://media/external/fs_id"), null, null, null, null);
	        int id = -1;
	        if (c != null) {
	            c.moveToFirst();
	            id = c.getInt(0);
	            c.close();
	        }
	        return id;
	    }*/	
	   
}
