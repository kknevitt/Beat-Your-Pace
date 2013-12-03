package com.GC01.BeatYourPace.BPM;

//NOT working yet, attempt to learn how to create and use a service

import java.io.File;
import java.io.IOException;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;
import android.app.IntentService;
import android.content.Intent;



public class RetrieveBpmService extends IntentService {

	public RetrieveBpmService() {
		super("RetrieveBpmService");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		
	}
		/**creating get method to query EchoNest library 
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
		//to be replaced by a 'get music' method
	    //File file = new File("C:\\Users\\Solange\\Desktop\\eclipse java\\BPM\\res\\raw\\04.mp3");
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
