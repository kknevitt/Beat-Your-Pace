package com.GC01.BeatYourPace.BPM;

/**
 * Service to call the EchoNest API to see if there is a bpm for the track
 * If there is no bpm in echonest then use a default of 160
 * 
 */

import java.io.File;
import java.io.IOException;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;

import android.app.IntentService;
import android.content.Intent;

public class RetrieveBpmService extends IntentService {

	// Used to write to the system log from this class.
    public static final String LOG_TAG = "RetrieveBpmService";
    
	public RetrieveBpmService() {
		super("RetrieveBpmService");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		 // Gets a URL to read from the incoming Intent's "data" value
        String localUrlString = intent.getDataString();
        
        try {
			getBPM(null);
		} catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
		/** Get method to query EchoNest library 
		 * @param  file   The path to the track that the BPM is needed for
		 * @return tempo  A double that returns the bpm of a track
		 * @throws EchoNestException
		 * @throws IOException
		 */
		public int getBPM(File file1) throws EchoNestException, IOException {

		//adding EchoNest API developer key
		final String API_KEY = ("LKMQIIIK24AL58W6D"); 
			
		//instance of library class
		EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
			
		//open file and uploads track to get BPM analysed
	    File file = file1;
		Track track = echoNest.uploadTrack(file, true);
	    
	    //setting a max time for analysis that will retrieve a track's BPM
	    track.waitForAnalysis(30000);
	    TrackAnalysis analysis = track.getAnalysis();
	    
	    //retrieve the BPM
	    double tempo = analysis.getTempo(); 
	    
	    int bpm = (int) tempo;
	    
	    return bpm;

		}
	}
