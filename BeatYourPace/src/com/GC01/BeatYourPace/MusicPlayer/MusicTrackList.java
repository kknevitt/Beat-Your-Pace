package com.GC01.BeatYourPace.MusicPlayer;

public class MusicTrackList {
	
	String currentTrack;	
	
	
	public MusicTrackList(){
	
		
	}

	
	// Method for changing the current track depending on which button is pressed from the UI.
	
	public void setCurrentTrack(int function) {
		
		MusicRetriever music = new MusicRetriever(null); //(ArrayListfromDB);
		currentTrack = music.getSongPath(); // Default value for current track
		
		if (function == 1) // Play
			currentTrack = music.getSongPath();
		
		
		else if (function == 2) { // Skip
			music.setSong();
			currentTrack = music.getSongPath();
		}
		
		else if (function == 3); {
			// Previous	
		}
	}

	
	
	public String getCurrentTrack() {
		return currentTrack;
		
	}

}
