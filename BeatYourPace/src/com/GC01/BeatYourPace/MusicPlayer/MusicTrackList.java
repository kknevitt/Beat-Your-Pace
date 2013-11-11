package com.GC01.BeatYourPace.MusicPlayer;

public class MusicTrackList {
<<<<<<< HEAD

static int currentTrack = 0;

	
	// Method for changing the current track depending on which button is pressed from the UI.
	@SuppressWarnings("static-access")
	public static void setCurrentTrack(int function) {
		
		MusicRetriever music = new MusicRetriever();
		currentTrack = music.tracks[0]; // Default value for current track
		
		if (function == 1) // Play
			currentTrack = music.tracks[0];
		if (function == 2) // Skip
			currentTrack++;
		if (function == 3) // Previous
			currentTrack--;	
	}
	
	
	public static int getCurrentTrack() {
		return currentTrack;
		
	}
	
	
	
	
=======
	
	String currentTrack;	
	
	
	public MusicTrackList(){
	
		
	}

	
	// Method for changing the current track depending on which button is pressed from the UI.
	
	public void setCurrentTrack(int function) {
		
		MusicRetriever music = new MusicRetriever(); //(ArrayListfromDB);
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
>>>>>>> branch 'master' of https://github.com/kknevitt/Beat-Your-Pace.git
}
