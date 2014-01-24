package com.GC01.BeatYourPace.BPM;

/**
 * Service to call the EchoNest API to see if there is a bpm for the track
 * If there is no bpm in EchoNest then use a default of 0
 * @author Laura Barbosa and Sarah Nicholson
 * @version 12/12/2013
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
		
		EchoNestAPI en = new EchoNestAPI(API_KEY);  
		
		SongParams p = new SongParams();
		p.setArtist(artist);
		p.setTitle(title);
		p.includeAudioSummary();
		
		List<Song> songs = en.searchSongs(p);
		if (songs.size() > 0) {
			double tempo = songs.get(0).getTempo();
			int bpm = (int)Math.round(tempo);
			Log.d(LOG_TAG,"Bpm for track added");
			return Integer.valueOf(bpm);
		} else {
			Log.d(LOG_TAG,"0 bpm");
			return 0;
		}
	}
}

