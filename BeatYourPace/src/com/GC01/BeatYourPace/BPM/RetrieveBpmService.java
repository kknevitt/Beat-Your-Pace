package com.GC01.BeatYourPace.BPM;

/**
 * Service to call the EchoNest API to see if there is a bpm for the track
 * If there is no bpm in EchoNest then use a default of 0
 */


import java.io.IOException;
import java.util.List;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RetrieveBpmService extends IntentService {

	// Used to write to the system log entries from this class
	public static final String LOG_TAG = "RetrieveBpmService";

	public RetrieveBpmService() {
		super("RetrieveBpmService");

	}

	protected void onHandleIntent(Intent intent) {

	}
	
	/** Get method to query EchoNest library 
	 * @param  artist  The artist name
	 * @param  title   The title of the track
	 * @return bpm  An integer that returns the bpm of a track
	 * @throws EchoNestException
	 * @throws IOException
	 */
	public int getTempo(String artist, String title) throws EchoNestException {
		final String API_KEY = ("METSQHGQBHSAAH077");
		
		//instance of EchoNest library class
		EchoNestAPI en = new EchoNestAPI(API_KEY);  
		
		//Hold the parameters for a song
		SongParams p = new SongParams();
		p.setArtist(artist);
		p.setTitle(title);
		p.includeAudioSummary();
		
		//For the song go and get tempo, convert to integer
		List<Song> songs = en.searchSongs(p);
		if (songs.size() > 0) {
			double tempo = songs.get(0).getTempo();
			int bpm = (int)Math.round(tempo);
				Log.d(LOG_TAG, "BPM data added");
			return Integer.valueOf(bpm);
		} else {
			//This should ideally be null rather than 0
			Log.d(LOG_TAG, "BPM data added");
			return 0;
		}
	}
}

